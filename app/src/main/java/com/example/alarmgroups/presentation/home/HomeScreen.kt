package com.example.alarmgroups.presentation.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.alarmgroups.R
import com.example.alarmgroups.presentation.navigation.Screen
import com.example.alarmgroups.presentation.utils.SwipeActions
import com.example.alarmgroups.presentation.utils.SwipeActionsConfig
import com.example.alarmgroups.ui.theme.grayDark
import com.example.alarmgroups.ui.theme.orangeLight

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    context: Context = LocalContext.current,
    viewModel: HomeViewModel = hiltViewModel(
        viewModelStoreOwner = (context as ComponentActivity)
    ),
    navController: NavHostController
) {

    val state = viewModel.state

    BackHandler(enabled = state.isMultiSelectionMode) {
        viewModel.onEvent(HomeScreenEvents.OnMultiSelectionMode(false))
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .zIndex(2f)
                .padding(bottom = 34.dp)
        ) {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AlarmDetailsScreen.route)
                },
                modifier = Modifier.size(96.dp),
                backgroundColor = orangeLight
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }

        Column(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp)
                .fillMaxSize()
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
                if (state.isMultiSelectionMode) {
                    TextButton(onClick = {
                        navController.navigate(
                            Screen.GroupsScreen.route
                        )
                    }) {
                        Icon(
                            painterResource(id = R.drawable.baseline_add_box_24),
                            contentDescription = null
                        )
                        Text(text = "Add to group")
                    }
                }
            }

            if (state.alarmList.isEmpty()) {
                Column(
                    modifier = Modifier
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
            } else {

                LazyColumn(modifier = Modifier.fillMaxSize()) {

                    items(
                        items = state.alarmList,
                        key = { alarm ->
                            // Return a stable + unique key for the item
                            alarm.id!!
                        }
                    ) { alarm ->
                        SwipeActions(
                            modifier = Modifier
                                .animateItemPlacement(),
                            showTutorial = true,
                            endActionsConfig = SwipeActionsConfig(
                                threshold = 0.4f,
                                background = Color(0xffFF4444),
                                iconTint = Color.Black,
                                icon = Icons.Default.Delete,
                                stayDismissed = true,
                                onDismiss = {
                                    viewModel.onEvent(HomeScreenEvents.OnDeleteAlarm(alarm))
                                }
                            ),
                        ) { swipeActionState ->
                            AlarmItem(
                                alarm = alarm,
                                onToggleClick = { isActive ->
                                    if (isActive) {
                                        viewModel.scheduleAlarm(alarm)
                                    } else {
                                        viewModel.unscheduleAlarm(alarm)
                                    }
                                },
                                onClick = {
                                    if (state.isMultiSelectionMode) {
                                        // if already selected then unselect
                                        if (state.selectedAlarmList.contains(alarm.id)) {
                                            viewModel.onEvent(HomeScreenEvents.OnAlarmUnSelect(alarm.id!!))
                                        } else {
                                            viewModel.onEvent(HomeScreenEvents.OnAlarmSelect(alarm.id!!))
                                        }
                                    } else {
                                        // navigate to item
                                        navController.navigate(
                                            Screen.AlarmDetailsScreen.passNavArgs(
                                                alarmId = alarm.id!!,
                                                alarmHr = alarm.time.hour,
                                                alarmMin = alarm.time.minute
                                            )
                                        )
                                    }
                                },
                                onLongClick = {
                                    viewModel.onEvent(HomeScreenEvents.OnMultiSelectionMode(true))
                                    viewModel.onEvent(HomeScreenEvents.OnAlarmSelect(alarm.id!!))
                                },
                                isMultiSelectionMode = state.isMultiSelectionMode,
                                isSelected = state.selectedAlarmList.contains(alarm.id)
                            )
                        }
                    }
                }
            }

        }
    }
}