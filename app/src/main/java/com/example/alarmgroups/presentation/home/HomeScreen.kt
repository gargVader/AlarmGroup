package com.example.alarmgroups.presentation.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {

    val state = viewModel.state

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


        if (state.alarmList.isEmpty()) {
            Text(text = "No alarms set")
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.alarmList.size) { i ->
                    val alarm = state.alarmList[i]
                    AlarmItem(alarm = alarm)
                }
            }
        }

    }
}