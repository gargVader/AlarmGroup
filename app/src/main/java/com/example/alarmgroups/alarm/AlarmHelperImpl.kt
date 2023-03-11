package com.example.alarmgroups.alarm

import android.app.AlarmManager
import android.app.Application
import android.content.Intent
import android.util.Log
import com.example.alarmgroups.alarm.pendingIntent.alarm_manager_pending_intent.createAlarmReceiverPendingIntentForSchedule
import com.example.alarmgroups.alarm.pendingIntent.alarm_manager_pending_intent.createAlarmReceiverPendingIntentForUnSchedule
import com.example.alarmgroups.alarm.services.AlarmService
import com.example.alarmgroups.domain.model.Alarm
import javax.inject.Inject

class AlarmHelperImpl @Inject constructor(
    private val app: Application,
    private val alarmManager: AlarmManager,
) : AlarmHelper {

    override fun scheduleAlarm(alarm: Alarm, skipFirstAlarm: Boolean) {
        alarm.days?.let {
            it.forEach { dayOfWeek ->
                setRepeatingAlarm(dayOfWeek, alarm, skipFirstAlarm)
            }
        } ?: setNonRepeatingAlarm(alarm)

    }

    override fun unscheduleAlarm(alarm: Alarm) {
        // Remove any alarms with a matching Intent
        alarm.days?.let {
            it.forEach { dayOfWeek ->
                alarmManager.cancel(
                    createAlarmReceiverPendingIntentForUnSchedule(
                        app,
                        alarm,
                        dayOfWeek
                    )
                )
            }
        } ?: alarmManager.cancel(createAlarmReceiverPendingIntentForUnSchedule(app, alarm))
    }

    override fun stopAlarm() {
        app.stopService(Intent(app.applicationContext, AlarmService::class.java))
    }

    private fun setRepeatingAlarm(dayOfWeek: Int, alarm: Alarm, skipFirstAlarm: Boolean) {
        Log.d("Girish", "setRepeatingAlarm: dayOfWeek=$dayOfWeek, $alarm")
        val alarmReceiverPendingIntent =
            createAlarmReceiverPendingIntentForSchedule(app, alarm, dayOfWeek)
        val firstAlarmTriggerMillis = alarm.getAlarmFirstTriggerMillis(dayOfWeek) +
                (AlarmConstants.WEEK_INTERVAL_MILLIS.takeIf { skipFirstAlarm } ?: 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            firstAlarmTriggerMillis,
            AlarmConstants.WEEK_INTERVAL_MILLIS,
            alarmReceiverPendingIntent
        )

    }

    private fun setNonRepeatingAlarm(alarm: Alarm) {
        val alarmReceiverPendingIntent = createAlarmReceiverPendingIntentForSchedule(app, alarm)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarm.getAlarmFirstTriggerMillis(),
            alarmReceiverPendingIntent
        )
    }

}