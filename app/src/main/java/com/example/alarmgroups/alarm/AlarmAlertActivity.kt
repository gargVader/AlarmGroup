package com.example.alarmgroups.alarm

import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.compose.material.Surface
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.example.alarmgroups.presentation.alarm_alert.AlarmAlertScreen
import com.example.alarmgroups.presentation.alarm_alert.AlarmAlertViewModel
import com.example.alarmgroups.ui.theme.AlarmGroupsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@AndroidEntryPoint
class AlarmAlertActivity : ComponentActivity() {

    private val viewModel: AlarmAlertViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Girish", "onCreate: AlarmAlertActivity")
        turnScreenOnAndKeyguardOff()

        val label = intent.getStringExtra(AlarmConstants.EXTRA_LABEL) ?: ""
        val notificationId: Long = intent.getLongExtra(AlarmConstants.EXTRA_NOTIFICATION_ID, -1)

        savedInstanceState?.putLong(AlarmConstants.EXTRA_NOTIFICATION_ID, notificationId)

        val nowLocalTime = LocalTime.now()

        lifecycleScope.launch {
            viewModel.uiState.collect {
                if (it.dismissClick){
                    finish()
                }
            }
        }


        setContent {
            AlarmGroupsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AlarmAlertScreen(nowLocalTime, label, viewModel)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        turnScreenOffAndKeyguardOn()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun turnScreenOnAndKeyguardOff() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        }
        // Deprecated flags are required on some devices, even with API>=27
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )


        with(getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager) {
            requestDismissKeyguard(this@AlarmAlertActivity, null)
        }
    }

    private fun turnScreenOffAndKeyguardOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(false)
            setTurnScreenOn(false)
        } else {
            window.clearFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
            )
        }
    }


}