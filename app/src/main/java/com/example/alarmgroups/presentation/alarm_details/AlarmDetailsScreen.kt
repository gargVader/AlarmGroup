package com.example.alarmgroups.presentation.alarm_details

import android.annotation.SuppressLint
import android.util.Log
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
import com.commandiron.wheel_picker_compose.WheelTimePicker
import com.commandiron.wheel_picker_compose.core.TimeFormat
import com.example.alarmgroups.R
import com.example.alarmgroups.ui.theme.grayDark
import com.example.alarmgroups.ui.theme.grayLight

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AlarmDetailsScreen(
    viewModel: AlarmDetailsViewModel = hiltViewModel()
) {

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /*TODO*/ }) {
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
                    Text(text = "Add Alarm")
                    Text(text = "Alarm in", color = grayDark)
                }

                IconButton(onClick = { /*TODO*/ }) {
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
                    timeFormat = TimeFormat.AM_PM,
                    textColor = grayLight,
                    size = DpSize(width = 256.dp, height = 256.dp),
                    rowCount = 5,
                    textStyle = TextStyle(fontSize = 36.sp)
                ) { snappedTime ->
                    Log.d("Girish", "$snappedTime")
                }
            }

            OutlinedTextField(
                value = "",
                onValueChange = {
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
