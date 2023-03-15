package com.example.alarmgroups.alarm.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.alarmgroups.alarm.AlarmConstants
import com.example.alarmgroups.alarm.AlarmDismissType
import com.example.alarmgroups.alarm.services.AlarmService
import java.util.*
import kotlin.concurrent.timerTask

// Receives PendingIntent from AlarmManager and AlarmAlertViewModel
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return
        intent ?: return
        val alarmServiceIntent = createAlarmServiceIntent(context, intent)
        when (intent.action) {
            AlarmConstants.ACTION_ALARM_FIRED -> {
                context.startForegroundService(alarmServiceIntent)
                // Set alarm to end automatically if not responded
                Timer().schedule(timerTask {
                    alarmServiceIntent.putExtra(
                        AlarmConstants.EXTRA_ALARM_DISMISS_TYPE, AlarmDismissType.DISMISS_AUTO.name
                    )
                    context.startService(alarmServiceIntent)
                    sendBroadcastToCloseAlarmAlertActivity(context)
                }, AlarmConstants.ALARM_DURATION_MILLIS)
            }

            AlarmConstants.ACTION_ALARM_DISMISSED -> {
                // stopService doesn't send the intent. So, alarm dismiss is handled in AlarmService
                context.startService(alarmServiceIntent)
                sendBroadcastToCloseAlarmAlertActivity(context)
            }
        }

    }

    private fun createAlarmServiceIntent(context: Context, intent: Intent): Intent {
        return Intent(intent.action, null, context, AlarmService::class.java).apply {
            putExtras(intent.extras!!)
        }
    }

    private fun sendBroadcastToCloseAlarmAlertActivity(context: Context) {
        val alarmAlertActivityCloseIntent =
            Intent(AlarmConstants.ACTION_ALARM_ALERT_ACTIVITY_CLOSE)
        context.sendBroadcast(alarmAlertActivityCloseIntent)
    }
}