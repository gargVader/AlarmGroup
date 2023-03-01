package com.example.alarmgroups.presentation.groups

import com.example.alarmgroups.domain.model.GroupWithAlarms

data class GroupsScreenState(
    val groupWithAlarmsList: List<GroupWithAlarms> = emptyList(),
    val showNewGroupDialog: Boolean = false,
    val newGroupName : String = "",
)