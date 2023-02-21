package com.example.alarmgroups.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = GroupEntity::class,
            parentColumns = ["id"],
            childColumns = ["groupId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("groupId")]
)
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val timeHour: Int,
    val timeMin: Int,
    val days: List<Int>?,
    val label: String?,
    val isActive: Boolean = true,
    val groupId: Long? = null
)