package com.example.alarmgroups.domain

import java.time.LocalDateTime

data class Alarm(
    val id: Int? = null,
    val time: LocalDateTime,
    val label: String?,
    val isActive: Boolean = true
)