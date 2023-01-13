package com.example.alarmgroups.di

import android.app.AlarmManager
import android.app.Application
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
}