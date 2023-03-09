package com.example.alarmgroups.presentation.home

data class HomeState(
    val seconds: String = "",
    val alarmDataList: List<Any> = emptyList(), // Can be Alarm or GroupWithAlarms
    val selectedAlarmList: List<Long> = emptyList(),
    val isMultiSelectionMode: Boolean = false,
)