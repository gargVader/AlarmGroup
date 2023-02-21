package com.example.alarmgroups.di

import android.app.Application
import androidx.room.Room
import com.example.alarmgroups.data.data_source.AlarmDao
import com.example.alarmgroups.data.data_source.AlarmDatabase
import com.example.alarmgroups.data.data_source.GroupDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAlarmDatabase(app: Application): AlarmDatabase {
        return Room.databaseBuilder(
            app,
            AlarmDatabase::class.java, "alarmdb"
        ).build()
    }

    @Provides
    fun provideAlarmDao(db: AlarmDatabase): AlarmDao {
        return db.alarmDao
    }

    @Provides
    fun provideGroupDao(db: AlarmDatabase): GroupDao {
        return db.groupDao
    }

}