package com.example.alarmgroups.presentation.home

import com.example.alarmgroups.domain.model.AlarmData

data class HomeState(
    val seconds: String = "",
    val alarmDataList: List<Any> = emptyList(), // Can be Alarm or GroupWithAlarms
    val selectedAlarmList: List<Long> = emptyList(),
    val isMultiSelectionMode: Boolean = false,
)