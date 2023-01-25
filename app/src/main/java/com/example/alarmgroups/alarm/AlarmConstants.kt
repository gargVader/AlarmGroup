package com.example.alarmgroups.alarm

import com.example.alarmgroups.BuildConfig

class AlarmConstants {
    companion object {
        // Intent actions
        const val ACTION_ALARM_FIRED = BuildConfig.APPLICATION_ID + ".ACTION_FIRED"
        const val ACTION_ALARM_DISMISSED = BuildConfig.APPLICATION_ID + ".ACTION_DISMISSED"

        // Extras for intent
        const val EXTRA_NOTIFICATION_ID = "ALARM_ID"
        const val EXTRA_LABEL = "ALARM_LABEL"
        const val EXTRA_IS_ONE_TIME_ALARM = "IS_ONE_TIME_ALARM"
    }
}