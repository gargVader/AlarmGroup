package com.example.alarmgroups.alarm

import android.app.AlarmManager
import android.app.Application
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.alarmgroups.alarm.pendingIntent.alarm_manager_pending_intent.createAlarmReceiverPendingIntentForSchedule
import com.example.alarmgroups.alarm.pendingIntent.alarm_manager_pending_intent.createAlarmReceiverPendingIntentForUnSchedule
import com.example.alarmgroups.domain.model.Alarm
import javax.inject.Inject

class AlarmHelperImpl @Inject constructor(
    private val app: Application,
    private val alarmManager: AlarmManager,
) : AlarmHelper {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun scheduleAlarm(alarm: Alarm) {
        alarm.days?.let {
            it.forEach { dayOfWeek ->
                setRepeatingAlarm(dayOfWeek, alarm)
            }
        } ?: setNonRepeatingAlarm(alarm)

    }

    override fun unscheduleAlarm(alarm: Alarm) {
        // Remove any alarms with a matching Intent
        alarm.days?.let {
            it.forEach { dayOfWeek ->
                alarmManager.cancel(createAlarmReceiverPendingIntentForUnSchedule(app, alarm, dayOfWeek))
            }
        } ?: alarmManager.cancel(createAlarmReceiverPendingIntentForUnSchedule(app, alarm))
    }

    override fun stopAlarm() {
        app.stopService(Intent(app.applicationContext, AlarmService::class.java))
    }

    private fun setRepeatingAlarm(dayOfWeek: Int, alarm: Alarm) {
        Log.d("Girish", "setRepeatingAlarm: dayOfWeek=$dayOfWeek, $alarm")
        val alarmReceiverPendingIntent =
            createAlarmReceiverPendingIntentForSchedule(app, alarm, dayOfWeek)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            alarm.getAlarmFirstTriggerMillis(dayOfWeek = dayOfWeek),
            AlarmConstants.WEEK_INTERVAL_MILLIS,
            alarmReceiverPendingIntent
        )

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setNonRepeatingAlarm(alarm: Alarm) {
        val alarmReceiverPendingIntent = createAlarmReceiverPendingIntentForSchedule(app, alarm)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarm.getAlarmFirstTriggerMillis(),
            alarmReceiverPendingIntent
        )
    }

}