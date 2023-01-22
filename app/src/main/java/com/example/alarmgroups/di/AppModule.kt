package com.example.alarmgroups.di

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.example.alarmgroups.data.data_source.AlarmDatabase
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
    fun provideNotificationManager(app : Application) : NotificationManager{
        return app.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @Provides
    @Singleton
    fun provideAlarmDatabase(app: Application) : AlarmDatabase {
        return Room.databaseBuilder(
            app,
            AlarmDatabase::class.java, "alarmdb"
        ).build()
    }
}