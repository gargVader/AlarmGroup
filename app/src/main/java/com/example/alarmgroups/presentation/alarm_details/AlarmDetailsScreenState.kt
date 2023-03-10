package com.example.alarmgroups.presentation.alarm_details

import java.time.LocalTime

/*
This state holds data for AlarmDetailsScreen. The screen shows all the details of an Alarm.kt object.
Because of this, its members are similar to Alarm. Could have replaced all members with just Alarm.
But that would have created an added complexity and deviation from existing app pattern. Would have to
then create a new Alarm object upon every state update.
 */
data class AlarmDetailsScreenState(
    val time: LocalTime = LocalTime.now(),
    val days: List<Int>? = null,
    val label: String = "",
    val groupId: Long? = null,
    val isEditMode: Boolean = false,
)