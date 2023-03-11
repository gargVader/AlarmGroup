package com.example.alarmgroups.domain.repository

import com.example.alarmgroups.domain.model.Alarm
import com.example.alarmgroups.domain.model.GroupWithAlarms
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {

    fun getAllAlarms(): Flow<List<Alarm>>

    fun getAllAlarmsWithoutGroup() : Flow<List<Alarm>>

    suspend fun insertAlarm(alarm: Alarm): Long

    suspend fun getAlarm(id: Long): Alarm?

    suspend fun deleteAlarm(id: Long)

    suspend fun deleteAllAlarms()

    suspend fun updateAlarmActive(id: Long, isActive: Boolean)

    suspend fun updateAlarm(alarm: Alarm)

    suspend fun updateAlarmWithGroupId(alarmId: Long, groupId: Long)

    suspend fun getAlarmGroup(alarmId : Long) : GroupWithAlarms?

}