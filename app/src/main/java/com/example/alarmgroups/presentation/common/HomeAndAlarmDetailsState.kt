package com.example.alarmgroups.presentation.common

import com.example.alarmgroups.domain.model.Alarm

data class HomeAndAlarmDetailsState(
    val alarmList: List<Alarm> = emptyList()
)