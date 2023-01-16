package com.example.alarmgroups

import com.example.alarmgroups.domain.Alarm

interface AlarmService {
    fun scheduleAlarm(alarm: Alarm)
}