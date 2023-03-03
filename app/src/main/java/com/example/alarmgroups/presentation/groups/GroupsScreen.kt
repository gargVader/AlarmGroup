package com.example.alarmgroups.presentation.groups

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.alarmgroups.presentation.home.HomeScreenEvents
import com.example.alarmgroups.presentation.home.HomeViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun GroupsScreen(
    context: Context = LocalContext.current,
    groupsViewModel: GroupsViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(
        viewModelStoreOwner = (context as ComponentActivity)
    ),
    navController: NavHostController,
) {

    val groupsState = groupsViewModel.state
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    BackHandler(enabled = !groupsState.showNewGroupDialog) {
        goBackToHomeScreen(homeViewModel, navController)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 12.dp, end = 12.dp)
            .background(color = MaterialTheme.colors.background)
    ) {
        if (groupsState.showNewGroupDialog) {
            LaunchedEffect(Unit) {
                delay(100) // Make sure you have delay here
                focusRequester.requestFocus()
                keyboard?.show()
            }

            NewGroupAlertDialog(
                textFieldValue = groupsState.newGroupName,
                focusRequester = focusRequester,
                onDismissRequest = {
                    groupsViewModel.onEvent(
                        GroupsScreenEvents.ShowNewGroupDialog(
                            false
                        )
                    )
                },
                onTextFieldValueChange = {
                    groupsViewModel.onEvent(GroupsScreenEvents.OnNewGroupNameValueChange(it))
                },
                onTextFieldValueDeleteClick = {
                    groupsViewModel.onEvent(GroupsScreenEvents.OnNewGroupNameValueChange(""))
                },
                onOkButtonClick = {
                    groupsViewModel.createNewGroup(
                        selectedAlarmList = homeViewModel.state.selectedAlarmList
                    )
                    groupsViewModel.onEvent(
                        GroupsScreenEvents.ShowNewGroupDialog(false)
                    )
                    // Show toast
                    goBackToHomeScreen(homeViewModel, navController)
                },
            )
        }

        LazyVerticalStaggeredGrid(
            modifier = Modifier.fillMaxSize(),
            columns = StaggeredGridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {

            item {
                NewGroupButton(onClick = {
                    groupsViewModel.onEvent(GroupsScreenEvents.ShowNewGroupDialog(true))
                })
            }

            items(
                items = groupsState.groupWithAlarmsList,
            ) { groupWithAlarms ->
                GroupWithAlarmsItem(groupWithAlarms = groupWithAlarms) { groupId ->
                    groupsViewModel.addAlarmsToExistingGroup(
                        groupId,
                        homeViewModel.state.selectedAlarmList
                    )
                    goBackToHomeScreen(homeViewModel, navController)
                }
            }
        }
    }
}

fun goBackToHomeScreen(homeViewModel: HomeViewModel, navController: NavHostController) {
    homeViewModel.onEvent(HomeScreenEvents.OnMultiSelectionMode(false))
    navController.popBackStack()
}


