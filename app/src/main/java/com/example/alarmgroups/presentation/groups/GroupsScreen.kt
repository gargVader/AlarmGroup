package com.example.alarmgroups.presentation.groups

import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.alarmgroups.ui.theme.orangeLight

@Composable
fun GroupsScreen(
    viewModel: GroupsViewModel = hiltViewModel(),
) {

    val state = viewModel.state
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {

        Card(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .clickable {

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

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(
                items = state.groupWithAlarmsList,
            ) {
                GroupWithAlarmsItem(groupWithAlarms = it)
            }
        }
    }

}