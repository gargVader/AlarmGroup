package com.example.alarmgroups.alarm.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
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
                context.stopService(alarmServiceIntent)
                sendBroadcastToCloseAlarmAlertActivity(context)
            }
        }

    }

    private fun createAlarmServiceIntent(context: Context, intent: Intent): Intent {
        val notificationId: Long = intent.getLongExtra(AlarmConstants.EXTRA_NOTIFICATION_ID, -1)
        val label: String? = intent.getStringExtra(AlarmConstants.EXTRA_LABEL)
        val isOneTimeAlarm: Boolean =
            intent.getBooleanExtra(AlarmConstants.EXTRA_IS_ONE_TIME_ALARM, false)

        Log.d("Girish", "AlarmReceiver: ${intent.action} -> $notificationId $label")

        return Intent(context, AlarmService::class.java).apply {
//            putExtras(intent.extras!!)
            putExtra(AlarmConstants.EXTRA_NOTIFICATION_ID, notificationId)
            putExtra(AlarmConstants.EXTRA_LABEL, label)
            putExtra(AlarmConstants.EXTRA_IS_ONE_TIME_ALARM, isOneTimeAlarm)
        }
    }

    private fun sendBroadcastToCloseAlarmAlertActivity(context: Context) {
        val alarmAlertActivityCloseIntent =
            Intent(AlarmConstants.ACTION_ALARM_ALERT_ACTIVITY_CLOSE)
        context.sendBroadcast(alarmAlertActivityCloseIntent)
    }
}