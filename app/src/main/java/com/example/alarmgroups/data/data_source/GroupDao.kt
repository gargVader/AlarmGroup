package com.example.alarmgroups.data.data_source

import androidx.room.*
import com.example.alarmgroups.data.model.GroupEntity
import com.example.alarmgroups.data.model.relations.GroupWithAlarms
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

    @Transaction
    @Query("SELECT * FROM groupentity WHERE id = :groupId")
    fun getGroupWithAlarms(groupId: Long): Flow<List<GroupWithAlarms>>
}