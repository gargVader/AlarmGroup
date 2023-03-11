package com.example.alarmgroups.data.data_source

import androidx.room.*
import com.example.alarmgroups.data.model.AlarmEntity
import com.example.alarmgroups.data.model.relations.GroupWithAlarmsRelation
import com.example.alarmgroups.domain.model.GroupWithAlarms
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

    @Query("SELECT * FROM alarmentity")
    fun getAllAlarms(): Flow<List<AlarmEntity>>

    @Query("SELECT * FROM alarmentity WHERE groupId IS NULL")
    fun getAllAlarmsWithoutGroup(): Flow<List<AlarmEntity>>

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

    @Query(
        """
        UPDATE alarmentity
        SET isActive = :isActive
        WHERE id = :id
    """
    )
    suspend fun updateAlarmActive(id: Long, isActive: Boolean)

    @Query(
        """
        UPDATE alarmentity
        SET groupId = :groupId
        WHERE id = :alarmId
    """
    )
    suspend fun updateAlarmWithGroupId(alarmId: Long, groupId: Long)

    @Query("DELETE FROM alarmentity WHERE id LIKE :id")
    suspend fun deleteAlarm(id: Long)

    @Query("DELETE FROM alarmentity")
    suspend fun deleteAllAlarms()

    @Transaction
    @Query("SELECT * FROM groupentity WHERE id IN (SELECT groupId FROM (SELECT * FROM alarmentity WHERE id = :alarmId))")
    suspend fun getAlarmGroup(alarmId : Long) : GroupWithAlarmsRelation?

}