package com.example.alarmgroups.data

import androidx.room.*

@Dao
interface AlarmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarmEntity: AlarmEntity)

    @Query("SELECT * FROM alarmentity")
    suspend fun getAllAlarms(): List<AlarmEntity>

    @Query(
        """
        SELECT *
        FROM alarmentity
        WHERE id LIKE :id
    """
    )
    suspend fun getAlarm(id: Int): AlarmEntity?

    @Update
    suspend fun updateAlarm(alarmEntity: AlarmEntity)

    @Query("DELETE FROM alarmentity WHERE id LIKE :id")
    suspend fun deleteAlarm(id: Int)

    @Query("DELETE FROM alarmentity")
    suspend fun deleteAllAlarms()
}