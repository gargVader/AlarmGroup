package com.example.alarmgroups.presentation.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.alarmgroups.ui.theme.orangeLight

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AddAlarmFloatingActionButton(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .zIndex(2f)
            .padding(bottom = 34.dp)
    ) {
        AnimatedVisibility(
            isVisible,
            enter = scaleIn(animationSpec = spring(stiffness = Spring.StiffnessLow)),
            exit = scaleOut(animationSpec = spring(stiffness = Spring.StiffnessLow))
        ) {
            FloatingActionButton(
                onClick = onClick,
                modifier = Modifier.size(96.dp),
                backgroundColor = orangeLight
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }
    }

}