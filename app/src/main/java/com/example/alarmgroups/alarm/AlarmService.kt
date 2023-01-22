package com.example.alarmgroups.alarm

import android.app.Notification
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.Service
import android.content.Intent
import android.os.*
import android.os.Process.THREAD_PRIORITY_BACKGROUND
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.alarmgroups.R
import com.example.alarmgroups.alarm.receivers.AlarmReceiver
import dagger.hilt.android.AndroidEntryPoint

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

    // Handler that receives messages from the thread
    private inner class ServiceHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(message: Message) {
            super.handleMessage(message)

            val label = message.data.getString(AlarmConstants.EXTRA_LABEL) ?: ""
            val notificationId = message.data.getLong(AlarmConstants.EXTRA_NOTIFICATION_ID)

            // Start Notification
            startForeground(
                notificationId.toInt(),
                createNotification(label, notificationId.toInt())
            )
            startVibratingAndPlayingSound()
        }
    }

    override fun onCreate() {
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

    private fun createNotification(label: String, notificationId: Int): Notification {
        val alarmAlertPendingIntent =
            createAlarmAlertPendingIntent(pendingIntentId = notificationId)
        val alarmDismissPendingIntent =
            createAlarmDismissPendingIntent(pendingIntentId = notificationId)
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

    private fun createAlarmAlertPendingIntent(pendingIntentId: Int): PendingIntent {
        val alarmAlertIntent = createAlarmAlertIntent()
        return PendingIntent.getActivity(
            applicationContext,
            pendingIntentId,
            alarmAlertIntent,
            FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
        )
    }

    private fun createAlarmAlertIntent(): Intent {
        return Intent(applicationContext, AlarmAlertActivity::class.java)
    }

    private fun createAlarmDismissPendingIntent(pendingIntentId: Int): PendingIntent {
        val alarmDismissIntent = createAlarmDismissIntent()
        return PendingIntent.getBroadcast(
            applicationContext,
            pendingIntentId,
            alarmDismissIntent,
            FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
        )
    }

    private fun createAlarmDismissIntent(): Intent {
        return Intent(AlarmConstants.ACTION_ALARM_DISMISSED).apply {
            setClass(applicationContext, AlarmReceiver::class.java)
        }
    }

    private fun startVibratingAndPlayingSound() {

    }

    private fun stopVibratingAndPlayingSound() {

    }

    companion object {
        val ALARM_NOTIFICATION_CHANNEL_ID = "channelId"
        val ALARM_NOTIFICATION_CHANNEL_NAME = "channelName"
        val ALARM_NOTIFICATION_CHANNEL_DESCRIPTION = "channelDescription"
    }

}