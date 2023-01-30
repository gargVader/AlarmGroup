package com.example.alarmgroups.alarm.pendingIntent.alarm_manager_pending_intent

import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import com.example.alarmgroups.alarm.AlarmConstants
import com.example.alarmgroups.alarm.receivers.AlarmReceiver


fun createAlarmReceiverPendingIntentForUnSchedule(
    app: Application,
    pendingIntentId: Long
): PendingIntent {
    val alarmReceiverIntent = createAlarmReceiverIntent(app)
    return PendingIntent.getBroadcast(
        app,
        pendingIntentId.toInt(),
        alarmReceiverIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
}

private fun createAlarmReceiverIntent(app: Application): Intent {
    return Intent(AlarmConstants.ACTION_ALARM_FIRED).apply {
        setClass(app, AlarmReceiver::class.java)
        // no need to set extras here
    }
}