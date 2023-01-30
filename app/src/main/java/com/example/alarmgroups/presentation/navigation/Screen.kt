package com.example.alarmgroups.presentation.navigation

const val NAV_GRAPH_ROOT = "root_graph"

const val ALARM_DETAILS_ALARM_ID = "alarm_id"
const val ALARM_DETAILS_ALARM_HR = "alarm_hour"
const val ALARM_DETAILS_ALARM_MIN = "alarm_min"

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home")
    object AlarmDetailsScreen :
        Screen(
            "alarm_details" +
                    "?$ALARM_DETAILS_ALARM_ID={$ALARM_DETAILS_ALARM_ID}" +
                    "?$ALARM_DETAILS_ALARM_HR={$ALARM_DETAILS_ALARM_HR}" +
                    "?$ALARM_DETAILS_ALARM_MIN={$ALARM_DETAILS_ALARM_MIN}"
        ) {
        fun passNavArgs(
            alarmId: Long,
            alarmHr: Int,
            alarmMin: Int
        ): String {
            return "alarm_details" +
                    "?$ALARM_DETAILS_ALARM_ID=$alarmId" +
                    "?$ALARM_DETAILS_ALARM_HR=$alarmHr" +
                    "?$ALARM_DETAILS_ALARM_MIN=$alarmMin"
        }
    }
}