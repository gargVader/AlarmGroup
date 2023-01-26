package com.example.alarmgroups.presentation.alarm_details

import java.time.LocalTime

data class AlarmDetailsScreenState(
    val time: LocalTime? = null,
    val label: String = "",
)