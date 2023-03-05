package com.example.alarmgroups.presentation.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarmgroups.alarm.AlarmHelper
import com.example.alarmgroups.domain.model.Alarm
import com.example.alarmgroups.domain.model.GroupWithAlarms
import com.example.alarmgroups.domain.repository.AlarmRepository
import com.example.alarmgroups.domain.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val alarmHelper: AlarmHelper,
    private val alarmRepo: AlarmRepository,
    private val groupRepo: GroupRepository
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    init {
        getAllAlarmData()
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
                    // clears selectedList before the start of every multiSelection session
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

    private fun getAllAlarmData() {
        viewModelScope.launch {
            alarmRepo.getAllAlarmsWithoutGroup()
                .combine(groupRepo.getAllGroupWithAlarms()) { alarmsWithoutGroup: List<Alarm>, groupWithAlarms: List<GroupWithAlarms> ->
                    groupWithAlarms + alarmsWithoutGroup
                }.collect {
                    state = state.copy(alarmDataList = it)
                }
        }
    }

    fun scheduleAlarm(alarm: Alarm) {
        alarmHelper.scheduleAlarm(alarm)
        viewModelScope.launch {
            alarmRepo.updateAlarmActive(alarm.id!!, true)
        }
    }

    fun unscheduleAlarm(alarm: Alarm) {
        alarmHelper.unscheduleAlarm(alarm)
        viewModelScope.launch {
            alarmRepo.updateAlarmActive(alarm.id!!, false)
        }
    }

    fun deleteAlarm(alarm: Alarm) {
        Log.d("Girish", "deleteAlarm: $alarm")
        alarmHelper.unscheduleAlarm(alarm)
        viewModelScope.launch {
            alarmRepo.deleteAlarm(alarm.id!!)
        }
    }

    fun scheduleGroup(groupWithAlarms: GroupWithAlarms) {
        groupWithAlarms.alarms.filter { it.isActive }.forEach { alarm ->
            alarmHelper.scheduleAlarm(alarm)
        }
        viewModelScope.launch {
            groupRepo.updateGroupIsActive(groupWithAlarms.group.id!!, true)
        }
    }

    fun unscheduleGroup(groupWithAlarms: GroupWithAlarms) {
        groupWithAlarms.alarms.filter { it.isActive }.forEach { alarm ->
            alarmHelper.unscheduleAlarm(alarm)
        }
        viewModelScope.launch {
            groupRepo.updateGroupIsActive(groupWithAlarms.group.id!!, false)
        }
    }

    /*
    fun deleteAllAlarms() {
        // unschedule all alarms
        state.alarmDataList.forEach {
            alarmHelper.unscheduleAlarm(it)
        }
        viewModelScope.launch {
            alarmRepo.deleteAllAlarms()
        }
    }
     */
}