package com.example.alarmgroups.data.data_source

import androidx.room.*
import com.example.alarmgroups.data.model.AlarmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

    @Query("SELECT * FROM alarmentity")
    fun getAllAlarms(): Flow<List<AlarmEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarmEntity: AlarmEntity): Long

    @Query(
        """
        SELECT *
        FROM alarmentity
        WHERE id LIKE :id
    """
    )
    suspend fun getAlarm(id: Long): AlarmEntity?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAlarm(alarmEntity: AlarmEntity)

    @Query("""
        UPDATE alarmentity
        SET isActive = :isActive
        WHERE id = :id
    """)
    suspend fun updateAlarmActive(id: Long, isActive : Boolean)

    @Query("DELETE FROM alarmentity WHERE id LIKE :id")
    suspend fun deleteAlarm(id: Long)

    @Query("DELETE FROM alarmentity")
    suspend fun deleteAllAlarms()
}