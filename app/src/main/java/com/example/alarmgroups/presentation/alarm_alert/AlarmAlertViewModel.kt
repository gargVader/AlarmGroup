package com.example.alarmgroups.presentation.alarm_alert

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.alarmgroups.alarm.AlarmHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlarmAlertViewModel @Inject constructor(
    private val alarmHelper: AlarmHelper
) : ViewModel() {

    var state by mutableStateOf(AlarmAlertScreenState())

    fun onEvent(event: AlarmAlertScreenEvents) {
        when (event) {
            is AlarmAlertScreenEvents.OnCancelClicked -> {

            }
        }
    }

}