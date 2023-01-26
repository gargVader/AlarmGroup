package com.example.alarmgroups.alarm.pendingIntent.alarm_manager_pending_intent

import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import com.example.alarmgroups.alarm.AlarmConstants
import com.example.alarmgroups.alarm.receivers.AlarmReceiver
import com.example.alarmgroups.domain.model.Alarm

fun createAlarmReceiverPendingIntentForSchedule(app: Application, alarm: Alarm): PendingIntent {
    val alarmReceiverIntent = createAlarmReceiverIntent(app, alarm)
    return PendingIntent.getBroadcast(
        app,
        alarm.id!!.toInt(),
        alarmReceiverIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
}

private fun createAlarmReceiverIntent(app: Application, alarm: Alarm): Intent {
    return Intent(AlarmConstants.ACTION_ALARM_FIRED).apply {
        setClass(app, AlarmReceiver::class.java)
        // 1. put extras
        putExtra(AlarmConstants.EXTRA_NOTIFICATION_ID, alarm.id)
        putExtra(AlarmConstants.EXTRA_LABEL, alarm.label)
        putExtra(AlarmConstants.EXTRA_IS_ONE_TIME_ALARM, true)
    }
}