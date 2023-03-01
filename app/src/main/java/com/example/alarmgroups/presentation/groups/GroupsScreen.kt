package com.example.alarmgroups.presentation.groups

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.alarmgroups.presentation.common.TextFieldItem
import com.example.alarmgroups.presentation.home.HomeViewModel
import com.example.alarmgroups.ui.theme.black2
import com.example.alarmgroups.ui.theme.orangeLight
import kotlinx.coroutines.delay

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GroupsScreen(
    context: Context = LocalContext.current,
    groupsViewModel: GroupsViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(
        viewModelStoreOwner = (context as ComponentActivity)
    ),
) {

    val groupsState = groupsViewModel.state
    val homeState = homeViewModel.state
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {

        Card(
            modifier = Modifier
                .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .clickable {
                    groupsViewModel.onEvent(GroupsScreenEvents.ShowNewGroupDialog(true))
                },
            shape = RoundedCornerShape(32.dp),
            backgroundColor = orangeLight
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Text(text = "Create New Group")
            }
        }

        if (groupsState.showNewGroupDialog) {

            LaunchedEffect(Unit) {
                delay(100) // Make sure you have delay here
                focusRequester.requestFocus()
                keyboard?.show()
            }

            AlertDialog(
                shape = RoundedCornerShape(16.dp),
                backgroundColor = black2,
                onDismissRequest = {
                    groupsViewModel.onEvent(
                        GroupsScreenEvents.ShowNewGroupDialog(
                            false
                        )
                    )
                },
                title = {

                    TextFieldItem(
                        modifier = Modifier.focusRequester(focusRequester),
                        value = groupsState.newGroupName,
                        onValueChange = {
                            groupsViewModel.onEvent(GroupsScreenEvents.OnNewGroupNameValueChange(it))
                        },
                        label = "Group",
                        onDeleteClick = {
                            groupsViewModel.onEvent(GroupsScreenEvents.OnNewGroupNameValueChange(""))
                        },
                        singleLine = true
                    )
                },
                buttons = {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = {
                            groupsViewModel.onEvent(
                                GroupsScreenEvents.ShowNewGroupDialog(false)
                            )
                        }) {
                            Text(text = "Cancel")
                        }
                        TextButton(onClick = { /*TODO*/ }) {
                            Text(text = "OK")
                        }
                    }
                }
            )
        }



        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(
                items = groupsState.groupWithAlarmsList,
            ) {
                GroupWithAlarmsItem(groupWithAlarms = it)
            }
        }
    }

}