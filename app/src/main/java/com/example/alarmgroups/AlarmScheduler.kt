package com.example.alarmgroups

import com.example.alarmgroups.domain.Alarm

interface AlarmScheduler {
    fun scheduleAlarm(alarm: Alarm)
}