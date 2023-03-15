package com.example.alarmgroups.alarm

import com.example.alarmgroups.BuildConfig

class AlarmConstants {
    companion object {
        // Intent actions
        const val ACTION_ALARM_FIRED = BuildConfig.APPLICATION_ID + ".ACTION_FIRED"
        const val ACTION_ALARM_DISMISSED = BuildConfig.APPLICATION_ID + ".ACTION_DISMISSED"
        const val ACTION_ALARM_ALERT_ACTIVITY_CLOSE =
            BuildConfig.APPLICATION_ID + ".ACTION_ALARM_ALERT_ACTIVITY_CLOSE"

        // Extras for intent
        const val EXTRA_NOTIFICATION_ID = "ALARM_ID"
        const val EXTRA_LABEL = "ALARM_LABEL"
        const val EXTRA_IS_ONE_TIME_ALARM = "IS_ONE_TIME_ALARM"

        const val EXTRA_ALARM_DISMISS_TYPE = "ALARM_DISMISS_TYPE"
        const val EXTRA_IS_DISMISS = "IS_DISMISS"
        const val EXTRA_IS_MANUAL_DISMISS_ALL = "IS_MANUAL_DISMISS_ALL"
        const val EXTRA_IS_AUTO_DISMISS = "IS_AUTO_DISMISS"

        const val WEEK_INTERVAL_MILLIS: Long = 7 * 24 * 60 * 60 * 1000
        const val ALARM_DURATION_MILLIS: Long = 30 * 1000

        val DAYS = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    }
}

/**
 * DISMISS_THIS : Dismiss this alarm only. Manually from AlarmAlertScreen.
 * DISMISS_ALL : Dismiss rest of the alarms in the group. Manually from AlarmAlertScreen.
 * DISMISS_AUTO : Dismiss this alarm only. Automatically after ALARM_ALERT_DURATION
 */
enum class AlarmDismissType {
    DISMISS_AUTO,
    DISMISS_ALL,
    DISMISS_THIS,
}
