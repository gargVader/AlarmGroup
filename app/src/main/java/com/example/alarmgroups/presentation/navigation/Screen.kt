package com.example.alarmgroups.presentation.navigation

const val NAV_GRAPH_ROOT = "root_graph"

sealed class Screen(val route : String) {
    object HomeScreen : Screen("home")
    object AlarmDetailsScreen : Screen("alarm_details")
}