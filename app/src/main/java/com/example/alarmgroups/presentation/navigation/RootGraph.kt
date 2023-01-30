package com.example.alarmgroups.presentation.navigation

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.alarmgroups.presentation.alarm_details.AlarmDetailsScreen
import com.example.alarmgroups.presentation.home.HomeScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RootGraph(navController: NavHostController) {

    AnimatedNavHost(
        navController = navController,
        route = NAV_GRAPH_ROOT,
        startDestination = Screen.HomeScreen.route
    ) {

        // Define all routes
        composable(
            route = Screen.HomeScreen.route,
        ) {
            HomeScreen(navController = navController)
        }

        composable(
            route = Screen.AlarmDetailsScreen.route,
            arguments = listOf(
                navArgument(
                    name = ALARM_DETAILS_ALARM_ID
                ) {
                    type = NavType.LongType
                    defaultValue = -1
                },
                navArgument(
                    name = ALARM_DETAILS_ALARM_HR
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(
                    name = ALARM_DETAILS_ALARM_MIN
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            ),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(200)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(200)
                )
            }
        ) { backStackEntry ->
            val alarmId = backStackEntry.arguments?.getLong(ALARM_DETAILS_ALARM_ID)
            val alarmHr = backStackEntry.arguments?.getInt(ALARM_DETAILS_ALARM_HR)
            val alarmMin = backStackEntry.arguments?.getInt(ALARM_DETAILS_ALARM_MIN)

            Log.d("Girish", "RootGraph: backStackEntry-> id=$alarmId hr=$alarmHr min=$alarmMin")

            AlarmDetailsScreen(
                navController = navController,
            )
        }

    }

}