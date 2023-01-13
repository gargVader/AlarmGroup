package com.example.alarmgroups.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
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
    }
}