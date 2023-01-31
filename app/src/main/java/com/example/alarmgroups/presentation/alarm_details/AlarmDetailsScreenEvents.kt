package com.example.alarmgroups.presentation.alarm_details

import java.time.LocalTime

sealed interface AlarmDetailsScreenEvents {
    data class OnLabelChange(val label: String) : AlarmDetailsScreenEvents
    data class OnTimeChange(val time: LocalTime) : AlarmDetailsScreenEvents
    object OnCloseClick : AlarmDetailsScreenEvents
    object OnSaveClick : AlarmDetailsScreenEvents
    object OnLabelTextDeleteClick : AlarmDetailsScreenEvents
    data class OnDayToggleClick(val day : Int) : AlarmDetailsScreenEvents
}