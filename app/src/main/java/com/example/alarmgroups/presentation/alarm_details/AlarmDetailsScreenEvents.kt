package com.example.alarmgroups.presentation.alarm_details

import com.example.alarmgroups.presentation.common.AppEvents
import java.time.LocalTime

sealed interface AlarmDetailsScreenEvents : AppEvents{
    data class OnLabelChange(val label: String) : AlarmDetailsScreenEvents
    data class OnTimeChange(val time: LocalTime) : AlarmDetailsScreenEvents
    object OnCloseClick : AlarmDetailsScreenEvents
    object OnSaveClick : AlarmDetailsScreenEvents
}