package com.example.alarmgroups

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return
        val message: String? = intent?.getStringExtra(EXTRA_LABEL)
        Log.d("Girish", "onReceive: $message")
        startMusicService(context)
    }

    private fun startMusicService(context: Context) {

    }

    companion object {
        val EXTRA_LABEL = "ALARM_LABEL"
    }

}