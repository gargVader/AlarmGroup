package com.example.alarmgroups.alarm

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.alarmgroups.alarm.receivers.AlarmReceiver
import com.example.alarmgroups.domain.Alarm
import java.time.ZoneId
import javax.inject.Inject

class AlarmHelperImpl @Inject constructor(
    private val app: Application,
    private val alarmManager: AlarmManager,
) : AlarmHelper {

    override fun scheduleAlarm(alarm: Alarm) {
        setNonRepeatingAlarm(alarm)
    }

    private fun setNonRepeatingAlarm(alarm: Alarm) {
        val pendingIntent = createPendingIntent(alarm)
        Log.d("Girish", "setNonRepeatingAlarm: ")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    alarm.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
                    pendingIntent
                )
            }
        }
    }

    private fun createPendingIntent(alarm: Alarm): PendingIntent {
        val intent = Intent(app, AlarmReceiver::class.java).apply {
            putExtra(AlarmReceiver.EXTRA_LABEL, alarm.label)
        }
        return PendingIntent.getBroadcast(
            app,
            alarm.id!!.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

}