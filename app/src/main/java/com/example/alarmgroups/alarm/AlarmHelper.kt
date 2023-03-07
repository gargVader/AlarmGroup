package com.example.alarmgroups.alarm

import com.example.alarmgroups.domain.model.Alarm

interface AlarmHelper {
    fun scheduleAlarm(alarm: Alarm, skipFirstAlarm: Boolean = false)
    fun unscheduleAlarm(alarm: Alarm)
    fun stopAlarm()
}