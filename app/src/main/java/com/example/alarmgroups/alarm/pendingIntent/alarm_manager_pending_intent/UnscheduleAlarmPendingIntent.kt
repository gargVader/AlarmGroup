package com.example.alarmgroups.alarm.pendingIntent.alarm_manager_pending_intent

import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import com.example.alarmgroups.alarm.AlarmConstants
import com.example.alarmgroups.alarm.receivers.AlarmReceiver
import com.example.alarmgroups.domain.model.Alarm


fun createAlarmReceiverPendingIntentForUnSchedule(
    app: Application,
    alarm: Alarm,
    dayOfWeek: Int? = null
): PendingIntent {
    val alarmReceiverIntent = createAlarmReceiverIntent(app, alarm)
    return PendingIntent.getBroadcast(
        app,
        generateAlarmIntentId(alarm.id!!.toInt(), dayOfWeek),
        alarmReceiverIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
}

private fun createAlarmReceiverIntent(app: Application, alarm: Alarm): Intent {
    val isOneTimeAlarm: Boolean = alarm.days.isNullOrEmpty()
    return Intent(AlarmConstants.ACTION_ALARM_FIRED).apply {
        setClass(app, AlarmReceiver::class.java)
        // 1. put extras
        putExtra(AlarmConstants.EXTRA_NOTIFICATION_ID, alarm.id)
        putExtra(AlarmConstants.EXTRA_LABEL, alarm.label)
        putExtra(AlarmConstants.EXTRA_IS_ONE_TIME_ALARM, isOneTimeAlarm)
    }
}