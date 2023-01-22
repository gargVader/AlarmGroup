package com.example.alarmgroups.domain.model

import java.time.LocalDateTime

data class Alarm(
    val id: Long? = null,
    val time: LocalDateTime,
    val label: String?,
    val isActive: Boolean = true
)