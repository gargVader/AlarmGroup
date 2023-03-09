package com.example.alarmgroups.alarm.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity

/*
Broadcast Receiver to perform actions on AlarmAlertActivity.

This is used so that user can close the AlarmAlertActivity by clicking notification action.
Notification Action -> Alarm Receiver ALARM_DISMISS -> AlarmAlertActivityReceiver
 */
class AlarmAlertActivityReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return
        (context as ComponentActivity).finish()
    }
}