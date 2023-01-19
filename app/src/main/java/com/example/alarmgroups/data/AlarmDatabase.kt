package com.example.alarmgroups.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [AlarmEntity::class],
    version = 1,
)
abstract class AlarmDatabase : RoomDatabase() {
    abstract val dao: AlarmDao
}