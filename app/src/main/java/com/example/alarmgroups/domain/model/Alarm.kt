package com.example.alarmgroups.domain.model

import android.util.Log
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

data class Alarm(
    val id: Long? = null,
    val time: LocalTime,
    val days: List<Int>? = null,
    val label: String?,
    val isActive: Boolean = true,
    val groupId: Long? = null,
) {

    /*
    Returns the LocalDateTime when the alarm should be first triggered.
     */
    private fun getAlarmFirstTrigger(
        dayOfWeek: Int? = null, //  dayOfWeek is not null only for repeating alarms
    ): LocalDateTime {
        var alarmDateTime = LocalDateTime.of(LocalDate.now(), time)

        val nowDateTime = LocalDateTime.now()
        val nowDayOfWeek = nowDateTime.dayOfWeek.value
        val nowTime = nowDateTime.toLocalTime()

        val isTimeBefore = time < nowTime

        if (dayOfWeek != null) {

            val isRequiredDayBefore = dayOfWeek < nowDayOfWeek
            val isRequiredDaySame = dayOfWeek == nowDayOfWeek

            if (isRequiredDayBefore or (isRequiredDaySame and isTimeBefore)) {
                // forward it by 7 days
                val daysToAdd = 7 - (nowDayOfWeek - dayOfWeek)
                alarmDateTime = alarmDateTime.plusDays(daysToAdd.toLong())
            } else {
                // set day properly
                val daysToAdd = dayOfWeek - nowDayOfWeek
                alarmDateTime = alarmDateTime.plusDays(daysToAdd.toLong())
            }
        } else {
            if (isTimeBefore) {
                alarmDateTime = alarmDateTime.plusDays(1)
            }
        }
        return alarmDateTime
    }

    fun getAlarmFirstTriggerMillis(
        dayOfWeek: Int? = null,
    ): Long {
        Log.d("Girish", "getAlarmFirstTriggerMillis: ${getAlarmFirstTrigger(dayOfWeek)}")
        return getAlarmFirstTrigger(dayOfWeek).atZone(ZoneId.systemDefault())
            .toEpochSecond() * 1000
    }
}