package com.example.alarmgroups

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.alarmgroups.alarm.services.AlarmService
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var notificationManager: NotificationManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        setupNotificationChannel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupNotificationChannel() {
        val notificationChannel =
            NotificationChannel(
                AlarmService.ALARM_NOTIFICATION_CHANNEL_ID,
                AlarmService.ALARM_NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
        notificationChannel.description = AlarmService.ALARM_NOTIFICATION_CHANNEL_DESCRIPTION
        notificationManager.createNotificationChannel(notificationChannel)
    }
}