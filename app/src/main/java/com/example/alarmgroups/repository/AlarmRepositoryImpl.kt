package com.example.alarmgroups.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.alarmgroups.data.AlarmDatabase
import com.example.alarmgroups.domain.Alarm
import com.example.alarmgroups.domain.repository.AlarmRepository
import com.example.alarmgroups.mapper.toAlarm
import com.example.alarmgroups.mapper.toAlarmEntity
import com.example.alarmgroups.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    db: AlarmDatabase
) : AlarmRepository {

    val dao = db.dao

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getAllAlarms(): Flow<Resource<List<Alarm>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val alarmList = dao.getAllAlarms().map { it.toAlarm() }
            emit(Resource.Success(data = alarmList))
            emit(Resource.Loading(isLoading = false))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun insertAlarm(alarm: Alarm): Flow<Resource<Long>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val rowId = dao.insertAlarm(alarm.toAlarmEntity())
            emit(Resource.Success(rowId))
            emit(Resource.Loading(isLoading = false))
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getAlarm(id: Int): Flow<Resource<Alarm>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val alarm = dao.getAlarm(id)
            if (alarm != null) emit(Resource.Success(data = alarm.toAlarm()))
            else emit(Resource.Error(message = "Not found"))
            emit(Resource.Loading(isLoading = false))
        }
    }

    override suspend fun deleteAlarm(id: Int) {
        dao.deleteAlarm(id)
    }

    override suspend fun deleteAllAlarms() {
        dao.deleteAllAlarms()
    }

}