package com.example.alarmgroups.presentation.home

import com.example.alarmgroups.domain.model.Alarm

data class HomeScreenState(
    val seconds: String = "",
    @Deprecated("Please don't use as it will not be updated")
    val alarmList: List<Alarm> = emptyList(),
)