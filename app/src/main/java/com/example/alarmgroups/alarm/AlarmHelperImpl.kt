package com.example.alarmgroups.alarm

import android.app.AlarmManager
import android.app.Application
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.alarmgroups.alarm.pendingIntent.alarm_manager_pending_intent.createAlarmReceiverPendingIntentForSchedule
import com.example.alarmgroups.alarm.pendingIntent.alarm_manager_pending_intent.createAlarmReceiverPendingIntentForUnSchedule
import com.example.alarmgroups.domain.model.Alarm
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class AlarmHelperImpl @Inject constructor(
    private val app: Application,
    private val alarmManager: AlarmManager,
) : AlarmHelper {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun scheduleAlarm(alarm: Alarm) {
        setNonRepeatingAlarm(alarm)
    }

    override fun unscheduleAlarm(id: Long) {
        // Remove any alarms with a matching Intent
        alarmManager.cancel(createAlarmReceiverPendingIntentForUnSchedule(app, id))
    }

    override fun stopAlarm() {
        app.stopService(Intent(app.applicationContext, AlarmService::class.java))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setNonRepeatingAlarm(alarm: Alarm) {
        val alarmReceiverPendingIntent = createAlarmReceiverPendingIntentForSchedule(app, alarm)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            getDateAdjustedAlarmTime(alarm).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            alarmReceiverPendingIntent
        )
    }

    private fun getDateAdjustedAlarmTime(alarm: Alarm): LocalDateTime {
        val adjustedAlarmTime = LocalDateTime.of(LocalDate.now(), alarm.time.toLocalTime())

        return if (adjustedAlarmTime.isBefore(LocalDateTime.now())) {
            adjustedAlarmTime.plusDays(1)
        } else {
            adjustedAlarmTime
        }
    }

}