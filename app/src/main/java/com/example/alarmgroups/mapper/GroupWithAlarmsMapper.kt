package com.example.alarmgroups.mapper

import com.example.alarmgroups.data.model.relations.GroupWithAlarmsRelation
import com.example.alarmgroups.domain.model.GroupWithAlarms

fun GroupWithAlarmsRelation.toGroupWithAlarms(): GroupWithAlarms {
    return GroupWithAlarms(
        group = group.toGroup(),
        alarms = alarms.map { it.toAlarm() }
    )
}

fun GroupWithAlarms.toGroupWithAlarmsRelation(): GroupWithAlarmsRelation {
    return GroupWithAlarmsRelation(
        group = group.toGroupEntity(),
        alarms = alarms.map { it.toAlarmEntity() }
    )
}