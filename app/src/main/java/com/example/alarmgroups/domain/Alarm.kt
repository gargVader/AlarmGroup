package com.example.alarmgroups.domain

import java.time.LocalDateTime

data class Alarm(
    val time: LocalDateTime,
    val label: String?,
)