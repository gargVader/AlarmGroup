package com.example.alarmgroups.presentation.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.alarmgroups.domain.Alarm

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlarmItem(alarm: Alarm) {
    Row {
        Column() {
            Text(text = "${alarm.time.hour}:${alarm.time.minute}")
            Text(text = alarm.label ?: "")
        }
        
    }

}