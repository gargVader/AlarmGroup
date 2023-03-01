package com.example.alarmgroups.presentation.home

import com.example.alarmgroups.domain.model.Alarm

data class HomeState(
    val seconds: String = "",
    val alarmList: List<Alarm> = emptyList(),
    val selectedAlarmList: List<Long> = emptyList(),
    val isMultiSelectionMode: Boolean = false,
)