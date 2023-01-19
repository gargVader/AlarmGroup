package com.example.alarmgroups.presentation.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarmgroups.alarm.AlarmHelper
import com.example.alarmgroups.domain.Alarm
import com.example.alarmgroups.domain.repository.AlarmRepository
import com.example.alarmgroups.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val alarmHelper: AlarmHelper,
    private val alarmRepository: AlarmRepository
) : ViewModel() {

    var state by mutableStateOf(HomeScreenState())

    init {
        getAllAlarms()
    }

    private fun getAllAlarms() {
        viewModelScope.launch {
            alarmRepository.getAllAlarms().collect { result ->
                when (result) {
                    is Resource.Loading -> {

                    }

                    is Resource.Error -> {

                    }

                    is Resource.Success -> {
                        if (result.data != null) {
                            state = state.copy(alarmList = result.data)
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: HomeScreenEvents) {
        when (event) {
            is HomeScreenEvents.OnTimeChanged -> {
                state = state.copy(
                    seconds = event.time
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun scheduleAlarmInSeconds(seconds: Int) {
        scheduleAlarm(
            Alarm(
                time = LocalDateTime.now().plusSeconds(seconds.toLong()),
                label = "test alarm"
            )
        )
    }

    private fun scheduleAlarm(alarm: Alarm) {
        alarmHelper.scheduleAlarm(alarm)
    }

}