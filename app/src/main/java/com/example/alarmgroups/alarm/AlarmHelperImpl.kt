package com.example.alarmgroups.alarm

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.alarmgroups.alarm.receivers.AlarmReceiver
import com.example.alarmgroups.domain.model.Alarm
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

    override fun unscheduleAlarm(alarm: Alarm) {
        alarmManager.cancel(createAlarmReceiverPendingIntent(alarm))
    }

    override fun stopAlarm() {
        app.stopService(Intent(app.applicationContext, AlarmService::class.java))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setNonRepeatingAlarm(alarm: Alarm) {
        val alarmReceiverPendingIntent = createAlarmReceiverPendingIntent(alarm)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarm.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            alarmReceiverPendingIntent
        )
    }

    private fun createAlarmReceiverPendingIntent(alarm: Alarm): PendingIntent {
        val alarmReceiverIntent = createAlarmReceiverIntent(alarm)
        return PendingIntent.getBroadcast(
            app,
            alarm.id!!.toInt(),
            alarmReceiverIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createAlarmReceiverIntent(alarm: Alarm): Intent {
        return Intent(AlarmConstants.ACTION_ALARM_FIRED).apply {
            setClass(app, AlarmReceiver::class.java)
            // 1. put extras
            putExtra(AlarmConstants.EXTRA_NOTIFICATION_ID, alarm.id)
            putExtra(AlarmConstants.EXTRA_LABEL, alarm.label)
            putExtra(AlarmConstants.EXTRA_IS_ONE_TIME_ALARM, true)
        }
    }

}