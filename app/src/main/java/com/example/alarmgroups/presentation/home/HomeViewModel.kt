package com.example.alarmgroups.presentation.home

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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val alarmHelper: AlarmHelper,
    private val repo: AlarmRepository
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    init {
        Log.d("Girish", "HomeViewModel: ")
        getAllAlarms()
    }

    fun onEvent(event: HomeScreenEvents) {
        when (event) {
            // Used for debugging
            is HomeScreenEvents.OnTimeChanged -> {
                state = state.copy(
                    seconds = event.time
                )
            }
            is HomeScreenEvents.OnDeleteAlarm -> {
                // remove from selectedAlarmList if present
                state = state.copy(
                    selectedAlarmList = state.selectedAlarmList.toMutableList().apply {
                        remove(event.alarm.id)
                    }
                )
                deleteAlarm(event.alarm)
            }
            is HomeScreenEvents.OnMultiSelectionMode -> {
                state = if (event.enabled) {
                    state.copy(isMultiSelectionMode = true)
                } else {
                    state.copy(
                        isMultiSelectionMode = false, selectedAlarmList = emptyList()
                    )
                }
            }
            is HomeScreenEvents.OnAlarmSelect -> {
                state = state.copy(
                    selectedAlarmList = state.selectedAlarmList.toMutableList().apply {
                        add(event.id)
                    }
                )
            }
            is HomeScreenEvents.OnAlarmUnSelect -> {
                val newSelectedAlarmList = state.selectedAlarmList.toMutableList().apply {
                    remove(event.id)
                }
                state = state.copy(
                    selectedAlarmList = newSelectedAlarmList,
                    isMultiSelectionMode = newSelectedAlarmList.isNotEmpty()
                )
            }
        }
    }

    private fun getAllAlarms() {
        viewModelScope.launch {
            repo.getAllAlarms().collect { alarms ->
                Log.d("Girish", "getAllAlarms: ViewModel $alarms")
                state = state.copy(alarmList = alarms)
            }
        }
    }

    // test func
    @RequiresApi(Build.VERSION_CODES.O)
    fun scheduleAlarmInSeconds(seconds: Int) {
        createNewAlarm(
            Alarm(
                time = LocalTime.now().plusSeconds(seconds.toLong()),
                label = "test alarm",
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

    fun unscheduleAlarm(alarm: Alarm) {
        Log.d("Girish", "unscheduleAlarm: ${alarm.time.hour}:${alarm.time.minute}")
        alarmHelper.unscheduleAlarm(alarm)
        viewModelScope.launch {
            repo.updateAlarmActive(alarm.id!!, false)
        }
    }

    fun deleteAlarm(alarm: Alarm) {
        Log.d("Girish", "deleteAlarm: $alarm")
        alarmHelper.unscheduleAlarm(alarm)
        viewModelScope.launch {
            repo.deleteAlarm(alarm.id!!)
        }
    }

    fun deleteAllAlarms() {
        // unschedule all alarms
        state.alarmList.forEach {
            alarmHelper.unscheduleAlarm(it)
        }
        viewModelScope.launch {
            repo.deleteAllAlarms()
        }
    }
}