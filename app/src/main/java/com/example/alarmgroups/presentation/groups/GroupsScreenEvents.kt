package com.example.alarmgroups.presentation.groups

sealed interface GroupsScreenEvents {
    data class ShowNewGroupDialog(val show: Boolean) : GroupsScreenEvents
    data class OnNewGroupNameValueChange(val value: String) : GroupsScreenEvents
    data class OnNewGroupCreate(val label: String) : GroupsScreenEvents
}