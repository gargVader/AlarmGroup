package com.example.alarmgroups.alarm.services

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.*
import android.os.Process.THREAD_PRIORITY_BACKGROUND
import androidx.core.app.NotificationCompat
import com.example.alarmgroups.R
import com.example.alarmgroups.alarm.AlarmConstants
import com.example.alarmgroups.alarm.AlarmDismissType
import com.example.alarmgroups.alarm.AlarmHelper
import com.example.alarmgroups.alarm.pendingIntent.alarm_service_pending_intent.createAlarmAlertPendingIntent
import com.example.alarmgroups.alarm.pendingIntent.alarm_service_pending_intent.createAlarmDismissPendingIntent
import com.example.alarmgroups.domain.model.Alarm
import com.example.alarmgroups.domain.repository.AlarmRepository
import com.example.alarmgroups.domain.repository.GroupRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

/**
 * AlarmService is created because the notification shown to user is an ongoing one
 * ref: https://developer.android.com/develop/ui/views/notifications/time-sensitive#display-notification-user
 *
 * notificationId is used both as an Id for the notification and pendingIntent
 */
@AndroidEntryPoint
class AlarmService : Service() {

    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null

    @Inject
    lateinit var alarmRepo: AlarmRepository

    @Inject
    lateinit var groupRepo: GroupRepository

    lateinit var mediaPlayer: MediaPlayer

    @Inject
    lateinit var alarmHelper: AlarmHelper

    @Inject
    lateinit var vibrator: Vibrator

    // Handler that receives messages from the thread
    private inner class ServiceHandler(looper: Looper) : Handler(looper) {

        override fun handleMessage(message: Message) {
            super.handleMessage(message)

            val label = message.data.getString(AlarmConstants.EXTRA_LABEL) ?: ""
            val notificationId = message.data.getLong(AlarmConstants.EXTRA_NOTIFICATION_ID)
            val isOneTimeAlarm: Boolean =
                message.data.getBoolean(AlarmConstants.EXTRA_IS_ONE_TIME_ALARM)
            val alarmDismissType: AlarmDismissType? =
                message.data.getString(AlarmConstants.EXTRA_ALARM_DISMISS_TYPE)?.let {
                    AlarmDismissType.valueOf(it)
                }

            when (alarmDismissType) {

                AlarmDismissType.DISMISS_AUTO -> {
                    stopSelf()
                }

                AlarmDismissType.DISMISS_THIS -> {
                    stopSelf()
                }

                AlarmDismissType.DISMISS_ALL -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        val groupWithAlarms = alarmRepo.getAlarmGroup(notificationId)
                        if (groupWithAlarms != null) {
                            updateAlarmsInGroup(groupWithAlarms.alarms, notificationId)
                        }
                    }
                    stopSelf()
                }

                else -> {
                    // 1. Start Notification
                    startForeground(
                        notificationId.toInt(),
                        createNotification(label, notificationId)
                    )
                    // 2. Start vibration
                    startVibration()
                    // 3. Start sound
                    startSound()

                }
            }

            if (isOneTimeAlarm) {
                turnOffAlarm(alarmId = notificationId)
            }
        }
    }

    /*

     */
    fun updateAlarmsInGroup(alarms: List<Alarm>, currentAlarmId: Long) {
        var currentAlarmIdx = Int.MAX_VALUE
        alarms.filter {
            // delay only those alarms which are active and are repeating
            val isRepeating = it.days?.isNotEmpty() ?: false
            it.isActive and isRepeating
        }.forEachIndexed { index, alarm ->
            if (alarm.id == currentAlarmId) currentAlarmIdx = index
            // All the alarms appearing after currentAlarmIdx need to be operated on
            if (index > currentAlarmIdx) {
                // 1. unschedule
                alarmHelper.unscheduleAlarm(alarm)
                // 2. schedule them by skipping first alarm, i.e, delay the first alarm trigger time by 1 week
                alarmHelper.scheduleAlarm(alarm, skipFirstAlarm = true)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()

        setupMediaPlayer()

        // Create a HandlerThread for the service to run on
        HandlerThread("AlarmServiceThread", THREAD_PRIORITY_BACKGROUND).apply {
            start()
            serviceLooper = looper
            serviceHandler = ServiceHandler(looper)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        serviceHandler?.obtainMessage()?.also { message ->
            message.arg1 = startId
            message.data = intent?.extras
            serviceHandler?.sendMessage(message)
        }
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    /*
    This is called when the service is stopped by calling
    app.stopService(Intent(app.applicationContext, AlarmService::class.java))
    */
    override fun onDestroy() {
        stopVibration()
        stopSound()
        // remove notification
        stopForeground(STOP_FOREGROUND_REMOVE)
    }

    private fun createNotification(label: String, notificationId: Long): Notification {
        val time = LocalTime.now()
        val text =
            "${time.hour.toString().padStart(2, '0')}:${time.minute.toString().padStart(2, '0')}"
        val alarmAlertPendingIntent =
            createAlarmAlertPendingIntent(applicationContext, label, notificationId)
        val alarmDismissThisPendingIntent = createAlarmDismissPendingIntent(
            applicationContext = applicationContext,
            pendingIntentId = notificationId,
            alarmDismissType = AlarmDismissType.DISMISS_THIS
        )
        val alarmDismissAllPendingIntent = createAlarmDismissPendingIntent(
            applicationContext = applicationContext,
            pendingIntentId = notificationId,
            alarmDismissType = AlarmDismissType.DISMISS_ALL
        )
        return NotificationCompat.Builder(applicationContext, ALARM_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
            .setContentTitle(text)
            .setContentText(label)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setFullScreenIntent(alarmAlertPendingIntent, true)
            .addAction(0, "Dismiss this", alarmDismissThisPendingIntent)
            .addAction(0, "Dismiss all", alarmDismissAllPendingIntent)
            .build()
    }

    private fun turnOffAlarm(alarmId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            alarmRepo.updateAlarmActive(alarmId, false)
        }
    }

    private fun startVibration() {
        val pattern: LongArray = longArrayOf(0, 1000, 500)
        val effect = VibrationEffect.createWaveform(pattern, 0)
        vibrator.vibrate(effect)
    }

    private fun stopVibration() {
        vibrator.cancel()
    }

    private fun setupMediaPlayer() {
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        mediaPlayer = MediaPlayer.create(this, soundUri)
        mediaPlayer.isLooping = true
    }

    private fun startSound() {
        mediaPlayer.start()
    }

    private fun stopSound() {
        mediaPlayer.stop()
    }

    companion object {
        const val ALARM_NOTIFICATION_CHANNEL_ID = "Alarm_Bees_Channel_Id"
        const val ALARM_NOTIFICATION_CHANNEL_NAME = "Alarm Bees"
        const val ALARM_NOTIFICATION_CHANNEL_DESCRIPTION = "To show notification for alarms"
    }

}