package com.example.alarmgroups.domain.repository

import com.example.alarmgroups.domain.Alarm
import com.example.alarmgroups.util.Resource
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {

    suspend fun getAllAlarms(): Flow<Resource<List<Alarm>>>

    suspend fun insertAlarm(alarm: Alarm)

    suspend fun getAlarm(id: Int): Flow<Resource<Alarm>>

    suspend fun deleteAlarm(id: Int)

    suspend fun deleteAllAlarms()

}