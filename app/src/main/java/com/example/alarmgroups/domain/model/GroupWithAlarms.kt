package com.example.alarmgroups.domain.model

data class GroupWithAlarms(
    val group: Group,
    val alarms: List<Alarm>
)