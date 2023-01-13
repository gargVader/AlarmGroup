package com.example.alarmgroups.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.alarmgroups.AlarmScheduler
import com.example.alarmgroups.domain.Alarm
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val scheduler: AlarmScheduler
) : ViewModel() {

    var state by mutableStateOf(HomeScreenState())

    fun onEvent(event: HomeScreenEvents) {
        when (event) {
            is HomeScreenEvents.OnTimeChanged -> {
                state = state.copy(
                    seconds = event.time
                )
            }
        }
    }

    fun scheduleAlarmInSeconds(seconds: Int) {
        scheduleAlarm(
            Alarm(
                time = LocalDateTime.now().plusSeconds(seconds.toLong()),
                label = "test alarm"
            )
        )
    }

    private fun scheduleAlarm(alarm: Alarm) {
        scheduler.scheduleAlarm(alarm)
    }

}