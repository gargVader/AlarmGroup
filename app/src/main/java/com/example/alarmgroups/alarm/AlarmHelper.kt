package com.example.alarmgroups.alarm

import com.example.alarmgroups.domain.Alarm

interface AlarmHelper {
    fun scheduleAlarm(alarm: Alarm)
}