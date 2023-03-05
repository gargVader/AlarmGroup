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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.alarmgroups.domain.model.Alarm
import com.example.alarmgroups.domain.model.GroupWithAlarms
import com.example.alarmgroups.presentation.common.AddAlarmFloatingActionButton
import com.example.alarmgroups.presentation.common.EmptyAlarmDataList
import com.example.alarmgroups.presentation.common.HomeScreenTopBar
import com.example.alarmgroups.presentation.navigation.Screen
import com.example.alarmgroups.presentation.utils.SwipeActions
import com.example.alarmgroups.presentation.utils.SwipeActionsConfig

@OptIn(
    ExperimentalMaterialApi::class, ExperimentalFoundationApi::class,
)
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

        AddAlarmFloatingActionButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            isVisible = !state.isMultiSelectionMode
        ) {
            navController.navigate(Screen.AlarmDetailsScreen.route)
        }

        Column(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp)
                .fillMaxSize()
        ) {

            HomeScreenTopBar(isMultiSelectionMode = state.isMultiSelectionMode) {
                navController.navigate(
                    Screen.GroupsScreen.route
                )
            }

            if (state.alarmDataList.isEmpty()) {
                EmptyAlarmDataList()
            } else {

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(
                        items = state.alarmDataList,
                        key = { alarmData ->
                            // Return a stable + unique key for the item
                            when (alarmData) {
                                is Alarm -> alarmData.id!!
                                is GroupWithAlarms -> alarmData.group.id!!
                                else -> {}
                            }
                        }
                    ) { alarmData ->

                        when (alarmData) {
                            is GroupWithAlarms -> {

                            }
                            is Alarm -> {
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
                                            viewModel.onEvent(
                                                HomeScreenEvents.OnDeleteAlarm(
                                                    alarmData
                                                )
                                            )
                                        }
                                    ),
                                ) { swipeActionState ->
                                    AlarmItem(
                                        alarm = alarmData,
                                        onToggleClick = { isActive ->
                                            if (isActive) {
                                                viewModel.scheduleAlarm(alarmData)
                                            } else {
                                                viewModel.unscheduleAlarm(alarmData)
                                            }
                                        },
                                        onClick = {
                                            if (state.isMultiSelectionMode) {
                                                // if already selected then unselect
                                                if (state.selectedAlarmList.contains(alarmData.id)) {
                                                    viewModel.onEvent(
                                                        HomeScreenEvents.OnAlarmUnSelect(
                                                            alarmData.id!!
                                                        )
                                                    )
                                                } else {
                                                    viewModel.onEvent(
                                                        HomeScreenEvents.OnAlarmSelect(
                                                            alarmData.id!!
                                                        )
                                                    )
                                                }
                                            } else {
                                                // navigate to item
                                                navController.navigate(
                                                    Screen.AlarmDetailsScreen.passNavArgs(
                                                        alarmId = alarmData.id!!,
                                                        alarmHr = alarmData.time.hour,
                                                        alarmMin = alarmData.time.minute
                                                    )
                                                )
                                            }
                                        },
                                        onLongClick = {
                                            viewModel.onEvent(
                                                HomeScreenEvents.OnMultiSelectionMode(
                                                    true
                                                )
                                            )
                                            viewModel.onEvent(
                                                HomeScreenEvents.OnAlarmSelect(
                                                    alarmData.id!!
                                                )
                                            )
                                        },
                                        isMultiSelectionMode = state.isMultiSelectionMode,
                                        isSelected = state.selectedAlarmList.contains(alarmData.id)
                                    )
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}