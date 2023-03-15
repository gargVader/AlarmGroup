package com.example.alarmgroups.alarm.pendingIntent.alarm_service_pending_intent

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import com.example.alarmgroups.alarm.AlarmConstants
import com.example.alarmgroups.alarm.AlarmDismissType
import com.example.alarmgroups.alarm.receivers.AlarmReceiver


fun createAlarmDismissPendingIntent(
    applicationContext: Context,
    pendingIntentId: Long,
    alarmDismissType: AlarmDismissType
): PendingIntent {
    val alarmDismissIntent =
        createAlarmDismissIntent(applicationContext, pendingIntentId, alarmDismissType)
    return PendingIntent.getBroadcast(
        applicationContext,
        pendingIntentId.toInt(),
        alarmDismissIntent,
        FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
    )
}

fun createAlarmDismissIntent(
    applicationContext: Context,
    notificationId: Long,
    alarmDismissType: AlarmDismissType
): Intent {
    return Intent(AlarmConstants.ACTION_ALARM_DISMISSED).apply {
        setClass(applicationContext, AlarmReceiver::class.java)
        putExtra(AlarmConstants.EXTRA_NOTIFICATION_ID, notificationId)
        putExtra(AlarmConstants.EXTRA_ALARM_DISMISS_TYPE, alarmDismissType.name)
    }
}