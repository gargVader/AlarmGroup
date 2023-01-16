package com.example.alarmgroups.receivers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.alarmgroups.AlarmAlertActivity
import com.example.alarmgroups.R
import kotlin.random.Random.Default.nextInt

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return
        val message: String? = intent?.getStringExtra(EXTRA_LABEL)
        Log.d("Girish", "onReceive: $message")
        showFullScreenNotification(context, message ?: "")
        startMusicService(context)
    }

    private fun startMusicService(context: Context) {

    }

    private fun showFullScreenNotification(context: Context, label: String) {
        val fullScreenIntent = Intent(context, AlarmAlertActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, nextInt(20), fullScreenIntent, 0)
        val channelId = "channelId"
        val channelName = "channelName"
        val channelDescription = "channelDescription"
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(label)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setFullScreenIntent(pendingIntent, true)

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
        notificationChannel.description = channelDescription
        notificationManager.createNotificationChannel(notificationChannel)

        notificationManager.notify(nextInt(), builder.build())
    }

    companion object {
        val EXTRA_LABEL = "ALARM_LABEL"
    }

}