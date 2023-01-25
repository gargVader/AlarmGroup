package com.example.alarmgroups.domain.repository

import com.example.alarmgroups.domain.model.Alarm
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {

    fun getAllAlarms(): Flow<List<Alarm>>

    suspend fun insertAlarm(alarm: Alarm): Long

    suspend fun getAlarm(id: Long): Alarm?

    suspend fun deleteAlarm(id: Long)

    suspend fun deleteAllAlarms()

    suspend fun updateAlarmActive(id: Long, isActive : Boolean)

}