package com.example.alarmgroups.domain.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

data class Alarm(
    val id: Long? = null,
    val time: LocalTime,
    val days: List<Int>? = null,
    val label: String?,
    val isActive: Boolean = true
) {

    /*
    Returns the LocalDateTime when the alarm should be first triggered.
     */
    private fun getAlarmFirstTrigger(
        dayOfWeek: Int? = null, //  dayOfWeek is not null only for repeating alarms
    ): LocalDateTime {
        if (dayOfWeek != null) {
            var alarmDate = LocalDate.now()
            // Required dayOfWeek is infront
            if (alarmDate.dayOfWeek.value < dayOfWeek) {
                val daysToAdd = dayOfWeek - alarmDate.dayOfWeek.value
                alarmDate = alarmDate.plusDays(daysToAdd.toLong())
            } else {
                // Required dayOfWeek is behind
                val daysToAdd = 7 - (alarmDate.dayOfWeek.value - dayOfWeek)
                alarmDate = alarmDate.plusDays(daysToAdd.toLong())
            }
            return LocalDateTime.of(alarmDate, time)
        } else {
            var alarmDateTime = LocalDateTime.of(LocalDate.now(), time)
            val nowDateTime = LocalDateTime.now()
            if (alarmDateTime.isBefore(nowDateTime)) {
                alarmDateTime = alarmDateTime.plusDays(1)
            }
            return alarmDateTime
        }
    }

    fun getAlarmFirstTriggerMillis(
        dayOfWeek: Int? = null,
    ): Long {
        return getAlarmFirstTrigger(dayOfWeek).atZone(ZoneId.systemDefault())
            .toEpochSecond() * 1000
    }
}