package com.example.alarmgroups.alarm

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.*
import android.os.Process.THREAD_PRIORITY_BACKGROUND
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.alarmgroups.R
import com.example.alarmgroups.alarm.pendingIntent.alarm_service_pending_intent.createAlarmAlertPendingIntent
import com.example.alarmgroups.alarm.pendingIntent.alarm_service_pending_intent.createAlarmDismissPendingIntent
import com.example.alarmgroups.domain.repository.AlarmRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
    lateinit var repo: AlarmRepository

    // Handler that receives messages from the thread
    private inner class ServiceHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(message: Message) {
            super.handleMessage(message)

            val label = message.data.getString(AlarmConstants.EXTRA_LABEL) ?: ""
            val notificationId = message.data.getLong(AlarmConstants.EXTRA_NOTIFICATION_ID)
            val isOneTimeAlarm: Boolean =
                message.data.getBoolean(AlarmConstants.EXTRA_IS_ONE_TIME_ALARM)

            // Start Notification
            startForeground(
                notificationId.toInt(),
                createNotification(label, notificationId)
            )
            startVibratingAndPlayingSound()
            if (isOneTimeAlarm) {
                turnOffAlarm(alarmId = notificationId)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
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
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onDestroy() {
        stopVibratingAndPlayingSound()
        // remove notification
        stopForeground(STOP_FOREGROUND_REMOVE)
    }

    private fun createNotification(label: String, notificationId: Long): Notification {
        val alarmAlertPendingIntent =
            createAlarmAlertPendingIntent(applicationContext, label,  notificationId)
        val alarmDismissPendingIntent =
            createAlarmDismissPendingIntent(applicationContext, pendingIntentId = notificationId)
        return NotificationCompat.Builder(applicationContext, ALARM_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(label)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setFullScreenIntent(
                alarmAlertPendingIntent, true
            )
            .addAction(
                R.drawable.ic_baseline_close_24,
                "Turn off",
                alarmDismissPendingIntent
            )
            .build()
    }

    private fun turnOffAlarm(alarmId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            repo.updateAlarmActive(alarmId, false)
        }
    }

    private fun startVibratingAndPlayingSound() {

    }

    private fun stopVibratingAndPlayingSound() {

    }

    companion object {
        const val ALARM_NOTIFICATION_CHANNEL_ID = "channelId"
        const val ALARM_NOTIFICATION_CHANNEL_NAME = "channelName"
        const val ALARM_NOTIFICATION_CHANNEL_DESCRIPTION = "channelDescription"
    }

}