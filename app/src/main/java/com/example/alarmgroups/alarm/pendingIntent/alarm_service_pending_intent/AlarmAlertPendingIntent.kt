package com.example.alarmgroups.alarm.pendingIntent.alarm_service_pending_intent

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import com.example.alarmgroups.alarm.AlarmAlertActivity


fun createAlarmAlertPendingIntent(
    applicationContext: Context,
    pendingIntentId: Long
): PendingIntent {
    val alarmAlertIntent = createAlarmAlertIntent(applicationContext)
    return PendingIntent.getActivity(
        applicationContext,
        pendingIntentId.toInt(),
        alarmAlertIntent,
        FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
    )
}

private fun createAlarmAlertIntent(applicationContext: Context): Intent {
    return Intent(applicationContext, AlarmAlertActivity::class.java)
}