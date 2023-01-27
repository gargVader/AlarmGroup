package com.example.alarmgroups.presentation.alarm_details

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.example.alarmgroups.domain.model.Alarm
import com.example.alarmgroups.presentation.common.HomeViewModel
import com.example.alarmgroups.ui.theme.grayLight
import java.time.LocalTime

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AlarmDetailsScreen(
    context: Context = LocalContext.current,
    homeViewModel: HomeViewModel = hiltViewModel(
        viewModelStoreOwner = (context as ComponentActivity)
    ),
    alarmDetailsViewModel: AlarmDetailsViewModel = hiltViewModel(),
    navController: NavHostController,
    alarmId: Long? = null
) {

    /*
    TODO:
        - Wheeler.startTime depends on alarmDetailsState.time
        - During init alarmDetailsState.time = current time
        - But Wheeler.startTime is used only once, so even if we update alarmDetailsState.time, wheeler won't change
        - Also with current implementation (L 53 : 66), because of recomposition, OnTimeChange is called both
            at OnSnappedTime() and L61. So, the wheeler's snapped time is overriden
        - Solution:
            Pass time as navigation arguments
     */
    val alarmDetailsState = alarmDetailsViewModel.state
    val commonState = homeViewModel.commonState
    val alarm: Alarm? = alarmId?.let {
        // If alarmId is available
        val alarm = commonState.alarmList.find {
            (it.id != null) and (it.id == alarmId)
        }?.apply {
            // If alarmId has been found in alarmList
            // Update alarmDetailsState
            alarmDetailsViewModel.onEvent(AlarmDetailsScreenEvents.OnTimeChange(time))
            label?.let {
                alarmDetailsViewModel.onEvent(AlarmDetailsScreenEvents.OnLabelChange(label))
            }
        }
        return@let alarm
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    alarmDetailsViewModel.onEvent(AlarmDetailsScreenEvents.OnCloseClick)
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
                    Text(text = alarmId?.let { "Edit Alarm" } ?: "Add Alarm")
                }

                IconButton(onClick = {
                    alarmDetailsViewModel.onEvent(AlarmDetailsScreenEvents.OnSaveClick)
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
                    startTime = alarmDetailsState.time,
                    timeFormat = TimeFormat.AM_PM,
                    textColor = grayLight,
                    size = DpSize(width = 256.dp, height = 256.dp),
                    rowCount = 5,
                    textStyle = TextStyle(fontSize = 36.sp)
                ) { snappedTime ->
                    alarmDetailsViewModel.onEvent(AlarmDetailsScreenEvents.OnTimeChange(snappedTime))
                }
            }

            OutlinedTextField(
                value = alarmDetailsState.label,
                onValueChange = {
                    alarmDetailsViewModel.onEvent(AlarmDetailsScreenEvents.OnLabelChange(it))
                },
                label = { Text(text = "label") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                leadingIcon = {
                    Icon(painterResource(id = R.drawable.label), contentDescription = null)
                }
            )
        }
    }

}
