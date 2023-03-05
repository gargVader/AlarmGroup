package com.example.alarmgroups.data.data_source

import androidx.room.*
import com.example.alarmgroups.data.model.GroupEntity
import com.example.alarmgroups.data.model.relations.GroupWithAlarmsRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {

    @Query("SELECT * FROM groupentity")
    fun getAllGroups(): Flow<List<GroupEntity>>

    @Query("SELECT * FROM groupentity WHERE id = :id")
    suspend fun getGroup(id: Long): GroupEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(group: GroupEntity): Long

    @Update
    suspend fun update(group: GroupEntity)

    @Query(
        """
        UPDATE groupentity
        SET isActive = :isActive
        WHERE id = :groupId
    """
    )
    suspend fun updateGroupIsActive(groupId: Long, isActive: Boolean)

    @Transaction
    @Query("SELECT * FROM groupentity WHERE id = :groupId")
    fun getGroupWithAlarms(groupId: Long): Flow<List<GroupWithAlarmsRelation>>

    @Transaction
    @Query("SELECT * FROM groupentity")
    fun getAllGroupWithAlarms(): Flow<List<GroupWithAlarmsRelation>>

    @Query("DELETE FROM groupentity WHERE id LIKE :groupId")
    suspend fun deleteGroup(groupId: Long)

    @Query("DELETE FROM groupentity")
    suspend fun deleteAllGroups()


}