package com.example.alarmgroups.alarm.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.alarmgroups.alarm.AlarmHelper
import com.example.alarmgroups.domain.repository.AlarmRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RescheduleAlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repo: AlarmRepository

    @Inject
    lateinit var alarmHelper: AlarmHelper

    override fun onReceive(context: Context?, intent: Intent?) {
        intent ?: return

        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            rescheduleAlarms()
        }
    }

    private fun rescheduleAlarms() {
        CoroutineScope(Dispatchers.IO).launch {
            repo.getAllAlarms().collect{
                it.forEach {
                    if (it.isActive){
                        alarmHelper.scheduleAlarm(it)
                    }
                }
            }
        }
    }
}