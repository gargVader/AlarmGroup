package com.example.alarmgroups.presentation.alarm_details

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.commandiron.wheel_picker_compose.WheelTimePicker
import com.commandiron.wheel_picker_compose.core.TimeFormat
import com.example.alarmgroups.R
import com.example.alarmgroups.alarm.AlarmConstants
import com.example.alarmgroups.presentation.common.TextFieldItem
import com.example.alarmgroups.ui.theme.grayLight

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AlarmDetailsScreen(
    viewModel: AlarmDetailsViewModel = hiltViewModel(),
    navController: NavHostController,
) {

    val state = viewModel.state

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    viewModel.onEvent(AlarmDetailsScreenEvents.OnCloseClick)
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = if (state.isEditMode) "Edit Alarm" else "Add Alarm")
                }

                IconButton(onClick = {
                    viewModel.onEvent(AlarmDetailsScreenEvents.OnSaveClick)
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        },

        ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Box(contentAlignment = Alignment.TopCenter) {
                WheelTimePicker(
                    startTime = state.time,
                    timeFormat = TimeFormat.AM_PM,
                    textColor = grayLight,
                    size = DpSize(width = 256.dp, height = 256.dp),
                    rowCount = 5,
                    textStyle = TextStyle(fontSize = 36.sp)
                ) { snappedTime ->
                    viewModel.onEvent(AlarmDetailsScreenEvents.OnTimeChange(snappedTime))
                }
            }

            Text(
                text = viewModel.getRepeatDaysDisplay(),
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AlarmConstants.DAYS.forEachIndexed { idx, day ->
                    val isDaySelected: Boolean = state.days?.contains(idx) ?: false
                    DayItem(text = day[0], isSelected = isDaySelected) {
                        viewModel.onEvent(AlarmDetailsScreenEvents.OnDayToggleClick(idx))
                    }
                }
            }

            TextFieldItem(
                value = state.label,
                onValueChange = {
                    viewModel.onEvent(AlarmDetailsScreenEvents.OnLabelChange(it))
                },
                label = "Label",
                onDeleteClick = {
                    viewModel.onEvent(AlarmDetailsScreenEvents.OnLabelChange(""))
                },
                leadingIcon = {
                    Icon(
                        painterResource(id = R.drawable.label),
                        contentDescription = null
                    )
                }
            )
        }
    }

}
