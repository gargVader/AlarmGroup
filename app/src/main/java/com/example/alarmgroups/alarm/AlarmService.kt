package com.example.alarmgroups.alarm

import android.app.Notification
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.Service
import android.content.Intent
import android.os.*
import android.os.Process.THREAD_PRIORITY_BACKGROUND
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.alarmgroups.R
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random.Default.nextInt

/**
 * AlarmService is created because the notification shown to user is an ongoing one
 * ref: https://developer.android.com/develop/ui/views/notifications/time-sensitive#display-notification-user
 */
@AndroidEntryPoint
class AlarmService : Service() {

    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null

    // Handler that receives messages from the thread
    private inner class ServiceHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(msg: Message) {
            Log.d("Girish", "handleMessage: AlarmService")
            super.handleMessage(msg)
            // Open AlarmAlertActivity
            // TODO: primary key as notification id
            startForeground(nextInt(), createNotification())
        }
    }

    fun createNotification(): Notification {
        return NotificationCompat.Builder(applicationContext, ALARM_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Title")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setFullScreenIntent(
                PendingIntent.getActivity(
                    applicationContext,
                    nextInt(),
                    Intent(applicationContext, AlarmAlertActivity::class.java),
                    FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
                ), true
            )
            .build()
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
        Log.d("Girish", "onStartCommand: service started")
        serviceHandler?.obtainMessage()?.also {
            it.arg1 = startId
            serviceHandler?.sendMessage(it)
        }
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    companion object {
        val ALARM_NOTIFICATION_CHANNEL_ID = "channelId"
        val ALARM_NOTIFICATION_CHANNEL_NAME = "channelName"
        val ALARM_NOTIFICATION_CHANNEL_DESCRIPTION = "channelDescription"
    }

}