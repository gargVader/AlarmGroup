package com.example.alarmgroups

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import com.example.alarmgroups.domain.Alarm
import com.example.alarmgroups.receivers.AlarmReceiver
import java.time.ZoneId
import javax.inject.Inject

class AlarmServiceImpl @Inject constructor(
    private val app: Application,
    private val alarmManager: AlarmManager,
) : AlarmService {

    override fun scheduleAlarm(alarm: Alarm) {
        setNonRepeatingAlarm(alarm)
    }

    private fun setNonRepeatingAlarm(alarm: Alarm) {
        val pendingIntent = createPendingIntent(alarm)
        Log.d("Girish", "setNonRepeatingAlarm: ")
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