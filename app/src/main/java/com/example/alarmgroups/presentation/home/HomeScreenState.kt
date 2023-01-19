package com.example.alarmgroups.presentation.home

import com.example.alarmgroups.domain.Alarm

data class HomeScreenState(
    val seconds: String = "",
    val alarmList: List<Alarm> = emptyList(),
    val alarmListIsLoading: Boolean = false
)