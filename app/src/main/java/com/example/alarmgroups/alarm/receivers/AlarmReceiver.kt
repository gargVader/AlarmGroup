package com.example.alarmgroups.alarm.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.alarmgroups.alarm.AlarmService

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return
        val message: String? = intent?.getStringExtra(EXTRA_LABEL)
        Log.d("Girish", "AlarmReceiver onReceive: $message")
        val alarmServiceIntent = Intent(context, AlarmService::class.java).apply {
            putExtra(EXTRA_LABEL, message)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(alarmServiceIntent)
        }else{
            context.startService(alarmServiceIntent)
        }
    }

    companion object {
        val EXTRA_LABEL = "ALARM_LABEL"
    }

}