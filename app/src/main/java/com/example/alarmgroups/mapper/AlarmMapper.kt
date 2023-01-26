package com.example.alarmgroups.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.alarmgroups.data.model.AlarmEntity
import com.example.alarmgroups.domain.model.Alarm
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
fun Alarm.toAlarmEntity(): AlarmEntity {
    return AlarmEntity(
        id = id,
        timeHour = time.hour,
        timeMin = time.minute,
        days = days,
        label = label,
        isActive = isActive,
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun AlarmEntity.toAlarm(): Alarm {
    return Alarm(
        id = id,
        time = LocalTime.of(timeHour, timeMin),
        days = days,
        label = label,
        isActive = isActive,
    )
}