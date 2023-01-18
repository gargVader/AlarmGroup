package com.example.alarmgroups

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.example.alarmgroups.alarm.AlarmService
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()
        setupNotificationChannel()
    }

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