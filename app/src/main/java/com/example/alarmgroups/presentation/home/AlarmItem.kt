package com.example.alarmgroups.presentation.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alarmgroups.alarm.AlarmConstants
import com.example.alarmgroups.domain.model.Alarm
import com.example.alarmgroups.ui.theme.*

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlarmItem(
    alarm: Alarm,
    onDeleteClick: (id: Long) -> Unit,
    onToggleClick: (isActive: Boolean) -> Unit,
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .clip(RoundedCornerShape(32.dp))
            .clickable(enabled = true) {
                onCardClick()
            },
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
                    fontWeight = if (alarm.isActive) FontWeight.SemiBold else FontWeight.Normal,
                    color = if (alarm.isActive) grayLight else grayDark
                )

                if (!alarm.days.isNullOrEmpty()) {
                    Text(
                        text = getRepeatDaysDisplay(alarm.days),
                        color = if (alarm.isActive) grayLight else grayDark
                    )
                }
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

fun getRepeatDaysDisplay(days: List<Int>): String {
    var daysDisplay: String = ""
    days.forEachIndexed { idx, day ->
        daysDisplay += if (idx == (days.size - 1))
            AlarmConstants.DAYS[day]
        else
            "${AlarmConstants.DAYS[day]}, "
    }
    return daysDisplay
}