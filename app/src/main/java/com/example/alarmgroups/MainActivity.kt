package com.example.alarmgroups

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.alarmgroups.presentation.home.HomeScreen
import com.example.alarmgroups.ui.theme.AlarmGroupsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlarmGroupsTheme {
                HomeScreen()
            }
        }
    }
}

/*
TODO:
    - AlarmReceiver
    - Maintain separate modules for different features
 */