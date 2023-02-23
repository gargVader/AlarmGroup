package com.example.alarmgroups.data.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.alarmgroups.data.model.AlarmEntity
import com.example.alarmgroups.data.model.GroupEntity

data class GroupWithAlarmsRelation(
    @Embedded
    val group: GroupEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "groupId"
    )
    val alarms : List<AlarmEntity>
)