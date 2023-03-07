package com.example.alarmgroups.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.alarmgroups.data.model.relations.GroupWithAlarmsRelation
import com.example.alarmgroups.domain.model.Alarm
import com.example.alarmgroups.domain.model.GroupWithAlarms

@RequiresApi(Build.VERSION_CODES.O)
fun GroupWithAlarmsRelation.toGroupWithAlarms(): GroupWithAlarms {
    return GroupWithAlarms(
        group = group.toGroup(),
        alarms = alarms.map { it.toAlarm() }.sortedBy { alarm: Alarm ->
            alarm.time
        }
    )
}

fun GroupWithAlarms.toGroupWithAlarmsRelation(): GroupWithAlarmsRelation {
    return GroupWithAlarmsRelation(
        group = group.toGroupEntity(),
        alarms = alarms.map { it.toAlarmEntity() }
    )
}