package com.example.alarmgroups.presentation.alarm_details


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import com.example.alarmgroups.ui.theme.*


@Composable
fun DayItem(text: Char, isSelected: Boolean = false, onClick: () -> Unit) {

    Box(
        modifier = Modifier
            .size(36.dp)
            .border(
                BorderStroke(1.dp, if (isSelected) orangeLight else grayDark),
                shape = CircleShape
            )
            .clip(CircleShape)
            .background(color = if (isSelected) orangeLight else black2)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = "$text", color = black1, fontSize = 22.sp)
    }

}