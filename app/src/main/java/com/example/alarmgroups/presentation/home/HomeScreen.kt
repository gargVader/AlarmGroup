package com.example.alarmgroups.presentation.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.alarmgroups.presentation.common.HomeViewModel
import com.example.alarmgroups.presentation.navigation.Screen
import com.example.alarmgroups.ui.theme.orangeLight

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    context: Context = LocalContext.current,
    viewModel: HomeViewModel = hiltViewModel(
        viewModelStoreOwner = (context as ComponentActivity)
    ),
    navController: NavHostController
) {

    val homeState = viewModel.homeState
    val commonState = viewModel.commonState

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .zIndex(2f)
                .padding(bottom = 34.dp)
        ) {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AlarmDetailsScreen.route)
                },
                modifier = Modifier.size(96.dp),
                backgroundColor = orangeLight
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }

        Column {

            OutlinedTextField(
                value = viewModel.homeState.seconds,
                onValueChange = {
                    viewModel.onEvent(
                        HomeScreenEvents.OnTimeChanged(it)
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Button(onClick = {
                viewModel.scheduleAlarmInSeconds(homeState.seconds.toInt())
            }) {
                Text(text = "Set Alarm")
            }


            Button(onClick = {
                viewModel.deleteAllAlarms()
            }) {
                Text(text = "Delete All from db")
            }
            if (commonState.alarmList.isEmpty()) {
                Text(text = "No alarms set")
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(commonState.alarmList.size) { i ->
                        val alarm = commonState.alarmList[i]
                        AlarmItem(
                            alarm = alarm,
                            onToggleClick = { isActive ->
                                if (isActive) {
                                    viewModel.scheduleAlarm(alarm)
                                } else {
                                    viewModel.unscheduleAlarm(alarm)
                                }
                            },
                            onDeleteClick = {
                                viewModel.deleteAlarm(commonState.alarmList[i].id!!)
                            },
                            onCardClick = {
                                navController.navigate(
                                    Screen.AlarmDetailsScreen.passAlarmId(alarm.id!!)
                                )
                            }
                        )
                    }
                }
            }

        }
    }
}