package com.example.alarmgroups.presentation.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandCircleDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alarmgroups.domain.model.Alarm
import com.example.alarmgroups.domain.model.GroupWithAlarms
import com.example.alarmgroups.presentation.common.CustomSwitchButton
import com.example.alarmgroups.ui.theme.*

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GroupItem(
    modifier: Modifier = Modifier,
    groupWithAlarms: GroupWithAlarms,
    onToggleClick: (isActive: Boolean) -> Unit,
    onAlarmClick: (alarm: Alarm) -> Unit,
    onAlarmToggleClick: (alarm: Alarm, isActive: Boolean) -> Unit,
) {

    val group = groupWithAlarms.group
    val alarmList = groupWithAlarms.alarms
    var isExpanded by remember { mutableStateOf(false) }

    val rotateX = animateFloatAsState(
        targetValue = if (!isExpanded) 0f else 180f,
        animationSpec = tween(
            durationMillis = 300
        )
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .clip(RoundedCornerShape(32.dp))
            .combinedClickable(
                onClick = { },
                onLongClick = { }
            ),
        shape = RoundedCornerShape(32.dp),
        backgroundColor = black2
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 18.dp,
                    vertical = 16.dp
                ),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp, end = 8.dp),
                    text = group.label,
                    color = if (group.isActive) grayLight else grayDark,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = if (group.isActive) FontWeight.SemiBold else FontWeight.Normal,
                )

                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        Icons.Filled.ExpandCircleDown,
                        contentDescription = null,
                        modifier = Modifier.rotate(rotateX.value)
                    )
                }
            }
            if (!isExpanded) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column {
                        alarmList.forEach {
                            Text(
                                text = "${it.HrString}:${it.MinString}",
                                color = if (group.isActive) {
                                    if (it.isActive) grayLight else grayDark
                                } else {
                                    grayDark
                                }

                            )
                        }
                    }
                    CustomSwitchButton(
                        checked = group.isActive,
                        onCheckedChange = {
                            onToggleClick(it)
                        },
                    )
                }
            }
            GroupItemExpandable(
                isVisible = isExpanded,
                alarmList = alarmList,
                onAlarmClick = onAlarmClick,
                onAlarmToggleClick = onAlarmToggleClick,
            )


        }

    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewGroupItem() {

}