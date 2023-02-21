package com.example.alarmgroups.di

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.os.Build
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.RequiresApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @RequiresApi(Build.VERSION_CODES.M)
    @Provides
    @Singleton
    fun provideAlarmManager(app: Application): AlarmManager {
        return app.getSystemService(AlarmManager::class.java)
    }

    @Provides
    @Singleton
    fun provideNotificationManager(app: Application): NotificationManager {
        return app.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @Provides
    @Singleton
    fun provideVibrator(app: Application): Vibrator =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            (app.getSystemService(Service.VIBRATOR_MANAGER_SERVICE) as VibratorManager)
                .defaultVibrator
        } else {
            app.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
        }
}