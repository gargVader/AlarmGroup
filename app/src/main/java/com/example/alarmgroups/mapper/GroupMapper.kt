package com.example.alarmgroups.mapper

import com.example.alarmgroups.data.model.GroupEntity
import com.example.alarmgroups.domain.model.Group

fun GroupEntity.toGroup(): Group {
    return Group(
        id = id,
        label = label,
        isActive = isActive,
    )
}

fun Group.toGroupEntity(): GroupEntity {
    return GroupEntity(
        id = id,
        label = label,
        isActive = isActive,
    )
}