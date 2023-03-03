package com.example.alarmgroups.presentation.alarm_alert

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.alarmgroups.ui.theme.grayLight
import java.time.LocalTime

@Composable
fun AlarmAlertScreen(
    localTime: LocalTime,
    label: String,
    viewModel: AlarmAlertViewModel = hiltViewModel()
) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${localTime.hour} : ${localTime.minute}",
            color = grayLight,
            fontWeight = FontWeight.SemiBold,
            fontSize = 96.sp
        )

        Text(
            text = label,
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )

        Row {
            Button(onClick = { viewModel.onEvent(AlarmAlertScreenEvents.OnDismissCurrentClick) }) {
                Text(text = "Dismiss Current")
            }
        }


    }
}