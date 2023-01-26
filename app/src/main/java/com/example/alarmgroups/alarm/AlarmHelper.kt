package com.example.alarmgroups.alarm

import com.example.alarmgroups.domain.model.Alarm

interface AlarmHelper {
    fun scheduleAlarm(alarm: Alarm)
    fun unscheduleAlarm(id : Long)
    fun stopAlarm()
}