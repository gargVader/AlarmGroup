package com.example.alarmgroups.presentation.alarm_alert

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.alarmgroups.alarm.AlarmConstants
import com.example.alarmgroups.alarm.AlarmHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlarmAlertViewModel @Inject constructor(
    private val alarmHelper: AlarmHelper,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(AlarmAlertScreenState())

    private val notificationId: Long = savedStateHandle[AlarmConstants.EXTRA_NOTIFICATION_ID] ?: -1


    init {
        Log.d("Girish", "AlarmAlertViewModel notificationId: $notificationId")
    }

    fun onEvent(event: AlarmAlertScreenEvents) {
        when (event) {
            is AlarmAlertScreenEvents.OnCancelClicked -> {

            }
        }
    }

}