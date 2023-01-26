package com.example.alarmgroups.presentation.home

import com.example.alarmgroups.presentation.common.AppEvents

sealed interface HomeScreenEvents : AppEvents {
     data class OnTimeChanged(val time : String) : HomeScreenEvents
}