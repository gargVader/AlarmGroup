package com.example.alarmgroups.presentation.groups

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.alarmgroups.domain.model.GroupWithAlarms

@Composable
fun GroupWithAlarmsItem(
    groupWithAlarms: GroupWithAlarms
) {

    val group = groupWithAlarms.group
    val alarmList = groupWithAlarms.alarms

    Column() {
        Text(text = group.label)

    }
}