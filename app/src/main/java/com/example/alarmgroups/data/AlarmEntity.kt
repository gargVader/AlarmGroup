package com.example.alarmgroups.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val time: Long,
    val label: String?,
    val isActive: Boolean = true
)