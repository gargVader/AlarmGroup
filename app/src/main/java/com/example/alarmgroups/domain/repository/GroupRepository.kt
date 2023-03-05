package com.example.alarmgroups.domain.repository

import com.example.alarmgroups.domain.model.Group
import com.example.alarmgroups.domain.model.GroupWithAlarms
import kotlinx.coroutines.flow.Flow

interface GroupRepository {

    fun getAllGroups(): Flow<List<Group>>

    suspend fun getGroup(id: Long): Group?

    suspend fun insert(group: Group): Long

    suspend fun update(group: Group)

    suspend fun updateGroupIsActive(groupId : Long, isActive : Boolean)

    fun getGroupWithAlarms(groupId: Long): Flow<List<GroupWithAlarms>>

    fun getAllGroupWithAlarms(): Flow<List<GroupWithAlarms>>

    suspend fun deleteGroup(groupId: Long)

}