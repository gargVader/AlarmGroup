package com.example.alarmgroups.presentation.alarm_details

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarmgroups.alarm.AlarmHelper
import com.example.alarmgroups.domain.model.Alarm
import com.example.alarmgroups.domain.repository.AlarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmDetailsViewModel @Inject constructor(
    private val alarmHelper: AlarmHelper,
    private val repo: AlarmRepository
) : ViewModel() {

    var state by mutableStateOf(AlarmDetailsState())
        private set

    fun onEvent(event: AlarmDetailsScreenEvents) {
        when (event) {
            is AlarmDetailsScreenEvents.OnLabelChange -> {
                state = state.copy(label = event.label)
            }

            is AlarmDetailsScreenEvents.OnTimeChange -> {
                Log.d("Girish", "AlarmDetailsScreenEvents.OnTimeChange: time=${event.time}")
                state = state.copy(time = event.time)
            }

            is AlarmDetailsScreenEvents.OnSaveClick -> {
                createNewAlarm(
                    Alarm(time = state.time!!, label = state.label)
                )
            }
        }
    }

    private fun createNewAlarm(alarm: Alarm) {
        viewModelScope.launch {
            // Insert in db
            val rowId = repo.insertAlarm(alarm)
            // Then use rowId to schedule alarm
            alarmHelper.scheduleAlarm(alarm.copy(id = rowId))
        }
    }

}