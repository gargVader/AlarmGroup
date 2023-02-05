package com.example.alarmgroups.alarm.pendingIntent.alarm_service_pending_intent

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import com.example.alarmgroups.alarm.AlarmAlertActivity
import com.example.alarmgroups.alarm.AlarmConstants


fun createAlarmAlertPendingIntent(
    applicationContext: Context,
    label: String,
    pendingIntentId: Long
): PendingIntent {
    val alarmAlertIntent = createAlarmAlertIntent(applicationContext, label, pendingIntentId)
    return PendingIntent.getActivity(
        applicationContext,
        pendingIntentId.toInt(),
        alarmAlertIntent,
        FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
    )
}

private fun createAlarmAlertIntent(
    applicationContext: Context,
    label: String,
    notificationId: Long
): Intent {
    return Intent(applicationContext, AlarmAlertActivity::class.java).apply {
        putExtra(AlarmConstants.EXTRA_NOTIFICATION_ID, notificationId)
        putExtra(AlarmConstants.EXTRA_LABEL, label)
    }
}