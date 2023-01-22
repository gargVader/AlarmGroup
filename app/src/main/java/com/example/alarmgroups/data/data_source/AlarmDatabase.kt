package com.example.alarmgroups.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.alarmgroups.data.model.AlarmEntity

@Database(
    entities = [AlarmEntity::class],
    version = 1,
)
abstract class AlarmDatabase : RoomDatabase() {
    abstract val dao: AlarmDao
}