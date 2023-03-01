package com.example.alarmgroups.presentation.home

import com.example.alarmgroups.domain.model.Alarm


sealed interface HomeScreenEvents {

    // Used for debugging
    data class OnTimeChanged(val time: String) : HomeScreenEvents
    data class OnDeleteAlarm(val alarm: Alarm) : HomeScreenEvents
    data class OnMultiSelectionMode(val enabled: Boolean) : HomeScreenEvents
    data class OnAlarmSelect(val id: Long) : HomeScreenEvents
    data class OnAlarmUnSelect(val id: Long) : HomeScreenEvents
}