package com.example.alarmgroups

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.content.getSystemService
import com.example.alarmgroups.domain.Alarm
import java.time.ZoneId
import javax.inject.Inject

class AlarmSchedulerImpl @Inject constructor(
    private val app: Application,
    private val alarmManager: AlarmManager,
) : AlarmScheduler {

    override fun scheduleAlarm(alarm: Alarm) {
        setNonRepeatingAlarm(alarm)
    }

    private fun setNonRepeatingAlarm(alarm: Alarm) {
        val pendingIntent = createPendingIntent(alarm)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarm.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            pendingIntent
        )
    }

    private fun createPendingIntent(alarm: Alarm): PendingIntent {
        val intent = Intent(app, AlarmReceiver::class.java).apply {
            putExtra(AlarmReceiver.EXTRA_LABEL, alarm.label)
        }
        return PendingIntent.getBroadcast(
            app,
            alarm.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

}