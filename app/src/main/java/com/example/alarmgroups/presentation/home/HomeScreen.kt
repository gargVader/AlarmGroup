package com.example.alarmgroups.presentation.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.alarmgroups.presentation.navigation.Screen
import com.example.alarmgroups.ui.theme.orangeLight

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val state = viewModel.state

    Scaffold(topBar = { },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AlarmDetailsScreen.route)
                },
                modifier = Modifier.size(84.dp),
                backgroundColor = orangeLight
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }
    ) {
        Column {
            OutlinedTextField(
                value = viewModel.state.seconds,
                onValueChange = {
                    viewModel.onEvent(
                        HomeScreenEvents.OnTimeChanged(it)
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Button(onClick = {
                viewModel.scheduleAlarmInSeconds(viewModel.state.seconds.toInt())
            }) {
                Text(text = "Set Alarm")
            }

            Button(onClick = {
                viewModel.deleteAllAlarms()
            }) {
                Text(text = "Delete All from db")
            }


            if (state.alarmList.isEmpty()) {
                Text(text = "No alarms set")
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.alarmList.size) { i ->
                        val alarm = state.alarmList[i]
                        AlarmItem(
                            alarm = alarm,
                            onUnscheduleClick = {
                                viewModel.unsheduleAlarm(alarm)
                            },
                            onDeleteClick = {
                                viewModel.deleteAlarm(state.alarmList[i])
                            }
                        )
                    }
                }
            }

        }
    }

}

@Composable
fun HomeScreenContent(viewModel: HomeViewModel) {

}