package com.example.alarmgroups.presentation.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import java.time.LocalDateTime
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val alarmHelper: AlarmHelper,
    private val repo: AlarmRepository
) : ViewModel() {

    var state by mutableStateOf(HomeScreenState())
        private set

    init {
        getAllAlarms()
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

    private fun getAllAlarms() {
        viewModelScope.launch {
            repo.getAllAlarms().collect { alarms ->
                Log.d("Girish", "getAllAlarms: ViewModel $alarms")
                state = state.copy(alarmList = alarms)
            }
        }
    }


    // test func
    @RequiresApi(Build.VERSION_CODES.O)
    fun scheduleAlarmInSeconds(seconds: Int) {
        createNewAlarm(
            Alarm(
                time = LocalDateTime.now().plusSeconds(seconds.toLong()),
                label = "test alarm"
            )
        )
    }

    private fun createNewAlarm(alarm: Alarm) {
        viewModelScope.launch {
            // Insert in db
            val rowId = repo.insertAlarm(alarm)
            // Then use rowId to schedule alarm
            alarmHelper.scheduleAlarm(alarm.copy(id = rowId))
        }
    }

    fun deleteAlarm(id: Long) {
        viewModelScope.launch {
            repo.deleteAlarm(id)
        }
    }

}