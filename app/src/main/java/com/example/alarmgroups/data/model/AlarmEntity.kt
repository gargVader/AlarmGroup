package com.example.alarmgroups.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val time: Long,
    val label: String?,
    val isActive: Boolean = true
)