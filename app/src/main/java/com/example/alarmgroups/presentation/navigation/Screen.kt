package com.example.alarmgroups.presentation.navigation

const val NAV_GRAPH_ROOT = "root_graph"

const val ALARM_DETAILS_ALARM_ID = "alarmId"

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home")
    object AlarmDetailsScreen :
        Screen("alarm_details?$ALARM_DETAILS_ALARM_ID={$ALARM_DETAILS_ALARM_ID}") {
        fun passAlarmId(
            alarmId: Long
        ): String {
            return "alarm_details?$ALARM_DETAILS_ALARM_ID=$alarmId"
        }
    }
}