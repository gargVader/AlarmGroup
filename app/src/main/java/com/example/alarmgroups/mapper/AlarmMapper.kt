package com.example.alarmgroups.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.alarmgroups.data.AlarmEntity
import com.example.alarmgroups.domain.Alarm
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
fun Alarm.toAlarmEntity(): AlarmEntity {
    return AlarmEntity(
        id = id,
        time = time.atZone(ZoneId.systemDefault()).toEpochSecond(),
        label = label,
        isActive = isActive,
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun AlarmEntity.toAlarm(): Alarm {
    return Alarm(
        id = id,
        time = LocalDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneId.systemDefault()),
        label = label,
        isActive = isActive,
    )
}