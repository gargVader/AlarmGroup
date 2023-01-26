package com.example.alarmgroups.presentation.common

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarmgroups.alarm.AlarmHelper
import com.example.alarmgroups.domain.model.Alarm
import com.example.alarmgroups.domain.repository.AlarmRepository
import com.example.alarmgroups.presentation.alarm_details.AlarmDetailsScreenEvents
import com.example.alarmgroups.presentation.alarm_details.AlarmDetailsScreenState
import com.example.alarmgroups.presentation.home.HomeScreenEvents
import com.example.alarmgroups.presentation.home.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class HomeAndAlarmDetailsViewModel @Inject constructor(
    private val alarmHelper: AlarmHelper,
    private val repo: AlarmRepository
) : ViewModel() {

    var homeState by mutableStateOf(HomeScreenState())
        private set

    var alarmDetailsState by mutableStateOf(AlarmDetailsScreenState())
        private set

    var commonState by mutableStateOf(HomeAndAlarmDetailsState())
        private set

    init {
        getAllAlarms()
    }

    fun onEvent(event: AppEvents) {
        when (event) {
            // HomeScreenEvents
            is HomeScreenEvents.OnTimeChanged -> {
                homeState = homeState.copy(
                    seconds = event.time
                )
            }

            // AlarmDetailsScreenEvents
            is AlarmDetailsScreenEvents.OnLabelChange -> {
                alarmDetailsState = alarmDetailsState.copy(label = event.label)
            }

            is AlarmDetailsScreenEvents.OnTimeChange -> {
                alarmDetailsState = alarmDetailsState.copy(time = event.time)
            }

            is AlarmDetailsScreenEvents.OnSaveClick -> {
                createNewAlarm(
                    Alarm(time = alarmDetailsState.time!!, label = alarmDetailsState.label)
                )
            }
        }
    }


    private fun getAllAlarms() {
        viewModelScope.launch {
            repo.getAllAlarms().collect { alarms ->
                Log.d("Girish", "getAllAlarms: ViewModel $alarms")
                commonState = commonState.copy(alarmList = alarms)
            }
        }
    }


    // test func
    @RequiresApi(Build.VERSION_CODES.O)
    fun scheduleAlarmInSeconds(seconds: Int) {
        createNewAlarm(
            Alarm(
                time = LocalTime.now().plusSeconds(seconds.toLong()),
                label = "test alarm"
            )
        )
    }

    private fun createNewAlarm(alarm: Alarm) {
        viewModelScope.launch {
            // Insert in db
            val rowId = repo.insertAlarm(alarm)
            // Then use rowId to schedule alarm
            alarmHelper.scheduleAlarm(alarm.copy(id = rowId))
        }
    }

    fun scheduleAlarm(alarm: Alarm) {
        Log.d("Girish", "scheduleAlarm: ${alarm.time.hour}:${alarm.time.minute}")
        alarmHelper.scheduleAlarm(alarm)
        viewModelScope.launch {
            repo.updateAlarmActive(alarm.id!!, true)
        }
    }

    // Should also be doable just from id
    fun unscheduleAlarm(alarm: Alarm) {
        Log.d("Girish", "unscheduleAlarm: ${alarm.time.hour}:${alarm.time.minute}")
        alarmHelper.unscheduleAlarm(alarm.id!!)
        viewModelScope.launch {
            repo.updateAlarmActive(alarm.id, false)
        }
    }

    // Should also be doable just from id
    fun deleteAlarm(id: Long) {
        alarmHelper.unscheduleAlarm(id)
        viewModelScope.launch {
            repo.deleteAlarm(id)
        }
    }

    fun deleteAllAlarms() {
        // unschedule all alarms

        viewModelScope.launch {
            repo.deleteAllAlarms()
        }
    }


}