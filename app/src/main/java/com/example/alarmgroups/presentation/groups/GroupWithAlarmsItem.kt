package com.example.alarmgroups.presentation.groups

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alarmgroups.domain.model.Alarm
import com.example.alarmgroups.domain.model.Group
import com.example.alarmgroups.domain.model.GroupWithAlarms
import com.example.alarmgroups.ui.theme.black2
import com.example.alarmgroups.ui.theme.grayLight
import java.time.LocalTime

@Composable
fun GroupWithAlarmsItem(
    groupWithAlarms: GroupWithAlarms,
    onClick: (groupId: Long) -> Unit
) {

    val group = groupWithAlarms.group
    val alarmList = groupWithAlarms.alarms


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .clickable { onClick(group.id!!) },
        shape = RoundedCornerShape(32.dp),
        backgroundColor = black2
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = group.label,
                style = MaterialTheme.typography.h5,
                color = grayLight,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(text = "${alarmList.size} alarms", color= grayLight)

            /*
            alarmList.forEach { alarm ->
                val hrString = alarm.time.hour.toString().padStart(2, '0')
                val minString = alarm.time.minute.toString().padStart(2, '0')
                Text(
                    text = "$hrString:$minString",
                    color = if (alarm.isActive) grayDark else grayLight,
                )
            }
             */

        }
    }

}

@Preview
@Composable
fun PreviewGroupWithAlarmsItem() {
    val alarm = Alarm(
        time = LocalTime.now(),
        days = listOf(1, 2, 3),
        label = "Wake up"
    )
    GroupWithAlarmsItem(
        groupWithAlarms = GroupWithAlarms(
            group = Group(label = "Morning alarms"),
            alarms = List(3) {
                alarm
            }
        ),
        onClick = {}
    )
}