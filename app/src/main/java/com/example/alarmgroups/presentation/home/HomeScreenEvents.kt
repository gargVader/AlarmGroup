package com.example.alarmgroups.presentation.home


sealed interface HomeScreenEvents {

    // Used for debugging
    data class OnTimeChanged(val time: String) : HomeScreenEvents
    data class OnMultiSelectionMode(val mode: Boolean) : HomeScreenEvents
    data class OnAlarmSelect(val id: Long) : HomeScreenEvents
    data class OnAlarmUnSelect(val id: Long) : HomeScreenEvents
}