package com.example.alarmgroups.presentation.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alarmgroups.alarm.AlarmConstants
import com.example.alarmgroups.domain.model.Alarm
import com.example.alarmgroups.ui.theme.*
import java.time.LocalTime

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlarmItem(
    modifier: Modifier = Modifier,
    alarm: Alarm,
    onToggleClick: (isActive: Boolean) -> Unit,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    isMultiSelectionMode: Boolean = false, // decides whether or not to show the Switch
    isSelected: Boolean = false,
    backgroundColor : Color= black2
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .clip(RoundedCornerShape(32.dp))
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        shape = RoundedCornerShape(32.dp),
        border = if (isSelected) BorderStroke(width = 2.dp, color = Color.White) else null,
        backgroundColor = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 18.dp,
                    vertical = 16.dp
                ),
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
                    text = "${alarm.HrString}:${alarm.MinString}",
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
            if (isMultiSelectionMode) {
                if (isSelected) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null)
                } else {
                    Icon(Icons.Outlined.Circle, contentDescription = null)
                }
            } else {
                Switch(
                    checked = alarm.isActive,
                    onCheckedChange = {
                        onToggleClick(it)
                    },
                )
            }
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

@Preview
@Composable
fun PreviewAlarmItem() {
    AlarmItem(
        alarm = Alarm(
            time = LocalTime.now(),
            days = listOf(1, 2, 3),
            label = "Wake up"
        ),
        onToggleClick = {},
        onClick = { /*TODO*/ },
        onLongClick = { /*TODO*/ }
    )
}