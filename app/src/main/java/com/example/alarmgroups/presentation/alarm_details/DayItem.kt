package com.example.alarmgroups.presentation.alarm_details


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alarmgroups.ui.theme.black2
import com.example.alarmgroups.ui.theme.grayDark


@Composable
fun DayItem(text: Char) {

    Box(
        modifier = Modifier
            .size(36.dp)
            .border(BorderStroke(1.dp, grayDark), shape = CircleShape)
            .clip(CircleShape)
            .background(color = black2),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "$text", color = grayDark, fontSize = 22.sp)
    }

}