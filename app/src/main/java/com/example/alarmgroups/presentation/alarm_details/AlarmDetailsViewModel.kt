package com.example.alarmgroups.presentation.alarm_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarmgroups.alarm.AlarmConstants
import com.example.alarmgroups.alarm.AlarmHelper
import com.example.alarmgroups.domain.model.Alarm
import com.example.alarmgroups.domain.repository.AlarmRepository
import com.example.alarmgroups.presentation.navigation.ALARM_DETAILS_ALARM_HR
import com.example.alarmgroups.presentation.navigation.ALARM_DETAILS_ALARM_ID
import com.example.alarmgroups.presentation.navigation.ALARM_DETAILS_ALARM_MIN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class AlarmDetailsViewModel @Inject constructor(
    private val alarmHelper: AlarmHelper,
    private val repo: AlarmRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val alarmHr: Int = savedStateHandle.get(ALARM_DETAILS_ALARM_HR) ?: -1
    private val alarmMin: Int = savedStateHandle.get(ALARM_DETAILS_ALARM_MIN) ?: -1
    private val alarmId: Long = savedStateHandle.get(ALARM_DETAILS_ALARM_ID) ?: -1

    var state by mutableStateOf(
        if (isValidHrAndMin(alarmHr, alarmMin))
            AlarmDetailsScreenState(LocalTime.of(alarmHr, alarmMin))
        else AlarmDetailsScreenState()
    )
        private set

    init {
        getAlarmFromRepo()
    }

    private fun getAlarmFromRepo() {
        if (!isValidAlarmId(alarmId)) return
        viewModelScope.launch {
            val alarm = repo.getAlarm(id = alarmId)
            alarm?.let {
                // alarm found. So we are in edit mode
                state = state.copy(isEditMode = true)
                if (!it.label.isNullOrBlank()) {
                    state = state.copy(label = it.label)
                }
                state = state.copy(days = it.days)
            }
        }
    }

    fun onEvent(event: AlarmDetailsScreenEvents) {
        when (event) {
            is AlarmDetailsScreenEvents.OnLabelChange -> {
                state = state.copy(label = event.label)
            }

            is AlarmDetailsScreenEvents.OnTimeChange -> {
                state = state.copy(time = event.time)
            }

            is AlarmDetailsScreenEvents.OnSaveClick -> {
                if (isAlarmEditMode(alarmId)) {
                    updateAlarm(
                        Alarm(
                            id = alarmId,
                            time = state.time,
                            label = state.label,
                            days = state.days
                        )
                    )
                } else {
                    createNewAlarm(
                        Alarm(
                            time = state.time,
                            label = state.label,
                            days = state.days
                        )
                    )
                }
            }

            is AlarmDetailsScreenEvents.OnDayToggleClick -> {
                val isDaySelected: Boolean = state.days?.contains(event.day) ?: false
                state = if (isDaySelected) {
                    state.copy(days = state.days?.minusElement(event.day)?.sorted())
                } else {
                    state.copy(days = state.days?.let {
                        it.plusElement(event.day).sorted()
                    } ?: listOf(event.day))
                }
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

    private fun updateAlarm(alarm: Alarm) {
        viewModelScope.launch {
            // Unschedule old alarm
            alarmHelper.unscheduleAlarm(alarm)
            // Update db
            repo.updateAlarm(alarm)
            // Schedule updated alarm time
            alarmHelper.scheduleAlarm(alarm)
        }
    }

    fun getRepeatDaysDisplay(): String {
        var daysDisplay: String = ""
        state.days?.forEachIndexed { idx, day ->
            daysDisplay += if (idx == (state.days!!.size - 1))
                AlarmConstants.DAYS[day]
            else
                "${AlarmConstants.DAYS[day]}, "
        }
        return daysDisplay
    }

    private fun isValidHrAndMin(alarmHr: Int, alarmMin: Int): Boolean {
        return (alarmHr != -1) and (alarmMin != -1)
    }

    private fun isValidAlarmId(alarmId: Long): Boolean {
        return alarmId != -1L
    }

    private fun isAlarmEditMode(alarmId: Long): Boolean = isValidAlarmId(alarmId)

}