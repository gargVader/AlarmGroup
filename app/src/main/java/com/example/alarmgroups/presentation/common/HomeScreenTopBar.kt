package com.example.alarmgroups.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreenTopBar(
    isMultiSelectionMode: Boolean,
    onAddToGroupClick: () -> Unit,
) {

    Row(
        modifier = Modifier
            .padding(start = 8.dp)
            .height(48.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Alarm",
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 22.sp,
            ),
        )
        if (isMultiSelectionMode) {
            TextButton(onClick = onAddToGroupClick) {
                Icon(
                    painterResource(id = com.example.alarmgroups.R.drawable.baseline_add_box_24),
                    contentDescription = null
                )
                Text(text = "Add to group")
            }
        }
    }

}