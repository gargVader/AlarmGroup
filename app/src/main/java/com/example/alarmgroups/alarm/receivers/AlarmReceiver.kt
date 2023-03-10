package com.example.alarmgroups.alarm.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.alarmgroups.alarm.AlarmConstants
import com.example.alarmgroups.alarm.services.AlarmService
import java.util.*
import kotlin.concurrent.timerTask

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return
        intent ?: return
        val alarmServiceIntent = createAlarmServiceIntent(context, intent)
        when (intent.action) {
            AlarmConstants.ACTION_ALARM_FIRED -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(alarmServiceIntent)
                } else {
                    context.startService(alarmServiceIntent)
                }
                // Set auto alarm end
                Timer().schedule(timerTask {
                    context.stopService(alarmServiceIntent)
                    sendBroadcastToCloseAlarmAlertActivity(context)
                }, AlarmConstants.ALARM_DURATION_MILLIS)

            }

            AlarmConstants.ACTION_ALARM_DISMISSED -> {
                // stopService doesn't send the intent. So, alarm dismiss is handled in AlarmService
                alarmServiceIntent.putExtra(AlarmConstants.EXTRA_IS_DISMISS, true)
                context.startService(alarmServiceIntent)
                sendBroadcastToCloseAlarmAlertActivity(context)
            }
        }

    }

    private fun createAlarmServiceIntent(context: Context, intent: Intent): Intent {
        return Intent(context, AlarmService::class.java).apply {
            putExtras(intent.extras!!)
        }
    }

    private fun sendBroadcastToCloseAlarmAlertActivity(context: Context) {
        val alarmAlertActivityCloseIntent =
            Intent(AlarmConstants.ACTION_ALARM_ALERT_ACTIVITY_CLOSE)
        context.sendBroadcast(alarmAlertActivityCloseIntent)
    }
}