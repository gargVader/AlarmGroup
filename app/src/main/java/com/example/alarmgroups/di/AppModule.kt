package com.example.alarmgroups.di

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationManager
import android.content.Context
import com.example.alarmgroups.App
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAlarmManager(app: Application): AlarmManager {
        return app.getSystemService(AlarmManager::class.java)
    }

    @Provides
    @Singleton
    fun provideNotificationManager(app : Application) : NotificationManager{
        return app.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
}