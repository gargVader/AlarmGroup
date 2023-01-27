package com.example.alarmgroups.presentation.alarm_details

import java.time.LocalTime

data class AlarmDetailsState(
    val time: LocalTime = LocalTime.now(),
    val label: String = "",
)