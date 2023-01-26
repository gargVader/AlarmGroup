package com.example.alarmgroups.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.alarmgroups.presentation.alarm_details.AlarmDetailsScreen
import com.example.alarmgroups.presentation.home.HomeScreen

@Composable
fun RootGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        route = NAV_GRAPH_ROOT,
        startDestination = Screen.HomeScreen.route
    ){

        // Define all routes

        composable(route = Screen.HomeScreen.route){
            HomeScreen(navController = navController)
        }

        composable(route = Screen.AlarmDetailsScreen.route){
            AlarmDetailsScreen(navController = navController)
        }

    }

}