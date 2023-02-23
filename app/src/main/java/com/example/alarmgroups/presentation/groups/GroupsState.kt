package com.example.alarmgroups.presentation.groups

import com.example.alarmgroups.domain.model.GroupWithAlarms

data class GroupsState(
    val groupWithAlarmsList: List<GroupWithAlarms> = emptyList()
)