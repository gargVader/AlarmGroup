package com.example.alarmgroups.alarm.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity

class AlarmAlertActivityReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("Girish", "onReceive: AlarmAlertActivityReceiver")
        context ?: return
        (context as ComponentActivity).finish()
    }
}