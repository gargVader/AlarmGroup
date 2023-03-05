package com.example.alarmgroups.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.alarmgroups.R
import com.example.alarmgroups.ui.theme.grayDark

@Composable
fun EmptyAlarmDataList(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .offset(y = (-72).dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painterResource(id = R.drawable.baseline_add_alarm_24),
            contentDescription = null,
            tint = grayDark,
            modifier = Modifier
                .padding(bottom = 4.dp)
                .size(96.dp)
        )
        Text(text = "No alarms here", color = grayDark)
    }
}