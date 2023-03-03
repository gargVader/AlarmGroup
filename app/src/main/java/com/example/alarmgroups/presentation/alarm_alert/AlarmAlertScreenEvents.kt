package com.example.alarmgroups.presentation.alarm_alert

sealed interface AlarmAlertScreenEvents {
    object OnDismissCurrentClick : AlarmAlertScreenEvents
    object OnDismissAllClick : AlarmAlertScreenEvents
}