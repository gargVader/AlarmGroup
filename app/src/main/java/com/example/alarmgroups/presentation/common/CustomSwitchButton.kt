package com.example.alarmgroups.presentation.common

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.alarmgroups.ui.theme.orangeDark

@Composable
fun CustomSwitchButton(
    switchPadding: Dp = 5.dp,
    buttonWidth: Dp = 50.dp,
    buttonHeight: Dp = 25.dp,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit),
) {

    val switchSize by remember {
        mutableStateOf(buttonHeight - switchPadding * 2)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    var padding by remember {
        mutableStateOf(0.dp)
    }

    padding = if (checked) buttonWidth - switchSize - switchPadding * 2 else 0.dp

    val animateSize by animateDpAsState(
        targetValue = if (checked) padding else 0.dp,
        tween(
            durationMillis = 300,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    Box(
        modifier = Modifier
            .width(buttonWidth)
            .height(buttonHeight)
            .clip(CircleShape)
            .background(if (checked) orangeDark else Color.LightGray)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onCheckedChange(!checked)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(switchPadding)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(animateSize)
                    .background(Color.Transparent)
            )

            Box(
                modifier = Modifier
                    .size(switchSize)
                    .clip(CircleShape)
                    .background(Color.White)
            )

        }
    }

}