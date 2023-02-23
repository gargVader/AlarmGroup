package com.example.alarmgroups.presentation.home

import com.example.alarmgroups.domain.model.Alarm

data class HomeState(
    val seconds: String = "",
    val alarmList: List<Alarm> = emptyList(),
    val isMultiSelectionMode: Boolean = false,
) {
    val selectedAlarmList: List<Alarm>
        get() = alarmList.filter {
            it.isSelected
        }
}