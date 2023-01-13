package com.example.alarmgroups.presentation.home

sealed interface HomeScreenEvents {
    data class OnTimeChanged(val time : String) : HomeScreenEvents
}