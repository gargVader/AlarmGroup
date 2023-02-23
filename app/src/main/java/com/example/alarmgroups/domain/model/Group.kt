package com.example.alarmgroups.domain.model

data class Group(
    val id: Long? = null,
    val label: String = "",
    val isActive: Boolean = true,
)