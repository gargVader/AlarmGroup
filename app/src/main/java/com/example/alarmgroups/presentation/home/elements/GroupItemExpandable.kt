package com.example.alarmgroups.presentation.home

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import com.example.alarmgroups.domain.model.Alarm
import com.example.alarmgroups.ui.theme.black1
import com.example.alarmgroups.ui.theme.black3

@Composable
fun GroupItemExpandable(isVisible: Boolean, alarmList: List<Alarm>) {
    val EXPANSTION_TRANSITION_DURATION = 500
    val enterTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow,
            )
        ) + fadeIn(
            initialAlpha = 0.3f,
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
        )
    }
    val exitTransition = remember {
        shrinkVertically(
            // Expand from the top.
            shrinkTowards = Alignment.Top,
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
        ) + fadeOut(
            // Fade in with the initial alpha of 0.3f.
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
        )
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = enterTransition,
        exit = exitTransition
    ) {
        Column(
        ) {

            alarmList.forEach {
                AlarmItem(
                    alarm = it,
                    onToggleClick = {},
                    onClick = { /*TODO*/ },
                    onLongClick = { /*TODO*/ },
                    backgroundColor = black3
                )
            }
        }
    }
}
/*
TODO:
 - Currently user can only add alarms to a group and delete an entire group. But cannot remove
 alarms that have already been added to the group.
 */