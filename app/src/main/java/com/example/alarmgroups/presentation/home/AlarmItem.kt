package com.example.alarmgroups.presentation.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.alarmgroups.domain.model.Alarm

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlarmItem(alarm: Alarm, OnDeleteClicked: () -> Unit) {
    Row {
        Column(modifier = Modifier.padding(all = 8.dp)) {
            Text(text = "${alarm.time.hour}:${alarm.time.minute}")
            Text(text = alarm.label ?: "")
            Button(onClick = OnDeleteClicked) {
                Text(text = "Delete")
            }
        }

    }

}