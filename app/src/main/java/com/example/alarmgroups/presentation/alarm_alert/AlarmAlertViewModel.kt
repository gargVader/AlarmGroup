package com.example.alarmgroups.presentation.alarm_alert

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.alarmgroups.alarm.AlarmConstants
import com.example.alarmgroups.alarm.AlarmHelper
import com.example.alarmgroups.alarm.pendingIntent.alarm_service_pending_intent.createAlarmDismissIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AlarmAlertViewModel @Inject constructor(
    private val alarmHelper: AlarmHelper,
    private val app: Application,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(AlarmAlertScreenState())
        private set

    private val _uiState = MutableStateFlow(AlarmAlertScreenState())
    val uiState: StateFlow<AlarmAlertScreenState> = _uiState.asStateFlow()

    private val notificationId: Long = savedStateHandle[AlarmConstants.EXTRA_NOTIFICATION_ID] ?: -1

    fun onEvent(event: AlarmAlertScreenEvents) {
        when (event) {
            is AlarmAlertScreenEvents.OnDismissCurrentClick -> {
                sendAlarmDismissEventToAlarmReceiver()
                _uiState.update {
                    it.copy(
                        dismissThisClick = true
                    )
                }
            }

            is AlarmAlertScreenEvents.OnDismissAllClick -> {
                sendAlarmDismissEventToAlarmReceiver(true)
                _uiState.update {
                    it.copy(
                        dismissAllClick = true
                    )
                }
            }
        }
    }

    private fun sendAlarmDismissEventToAlarmReceiver(isDismissAll: Boolean = false) {

        val alarmDismissIntent =
            createAlarmDismissIntent(
                app,
                notificationId = notificationId,
                isDismissAll = isDismissAll
            )
//         Send this intent to AlarmReceiver
        app.sendBroadcast(alarmDismissIntent)
    }

}