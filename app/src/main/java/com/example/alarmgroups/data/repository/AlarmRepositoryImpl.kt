package com.example.alarmgroups.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.alarmgroups.data.data_source.AlarmDao
import com.example.alarmgroups.data.data_source.AlarmDatabase
import com.example.alarmgroups.domain.model.Alarm
import com.example.alarmgroups.domain.repository.AlarmRepository
import com.example.alarmgroups.mapper.toAlarm
import com.example.alarmgroups.mapper.toAlarmEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val dao: AlarmDao
) : AlarmRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getAllAlarms(): Flow<List<Alarm>> {
        Log.d("Girish", "getAllAlarms: RepoImpl")
        return dao.getAllAlarms()
            .map {
                it.map { alarmEntity -> alarmEntity.toAlarm() }
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun insertAlarm(alarm: Alarm): Long {
        return dao.insertAlarm(alarm.toAlarmEntity())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getAlarm(id: Long): Alarm? {
        return dao.getAlarm(id)?.toAlarm()
    }

    override suspend fun deleteAlarm(id: Long) {
        dao.deleteAlarm(id)
    }

    override suspend fun deleteAllAlarms() {
        dao.deleteAllAlarms()
    }

    override suspend fun updateAlarmActive(id: Long, isActive: Boolean) {
        dao.updateAlarmActive(id, isActive)
    }

    override suspend fun updateAlarm(alarm: Alarm) {
        dao.updateAlarm(alarm.toAlarmEntity())
    }

}