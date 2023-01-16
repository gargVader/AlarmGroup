package com.example.alarmgroups.di

import com.example.alarmgroups.AlarmService
import com.example.alarmgroups.AlarmServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AlarmModule {

    @Binds
    @Singleton
    abstract fun bindAlarmService(
        alarmServiceImpl: AlarmServiceImpl
    ): AlarmService

}