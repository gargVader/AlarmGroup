package com.example.alarmgroups.presentation.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alarmgroups.domain.model.Alarm
import com.example.alarmgroups.ui.theme.black2
import com.example.alarmgroups.ui.theme.grayDark
import com.example.alarmgroups.ui.theme.grayLight

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlarmItem(
    alarm: Alarm,
    onDeleteClick: (id: Long) -> Unit,
    onToggleClick: (isActive: Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, top = 8.dp),
        shape = RoundedCornerShape(32.dp),
        backgroundColor = black2
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                if (!alarm.label.isNullOrBlank()) {
                    Text(
                        text = alarm.label,
                        color = if (alarm.isActive) grayLight else grayDark
                    )
                }
                Text(
                    text = "${alarm.time.hour}:${alarm.time.minute}",
                    fontSize = 46.sp,
                    color = if (alarm.isActive) grayLight else grayDark
                )
            }
            Switch(
                checked = alarm.isActive,
                onCheckedChange = {
                    onToggleClick(it)
                },
            )
        }
    }
}