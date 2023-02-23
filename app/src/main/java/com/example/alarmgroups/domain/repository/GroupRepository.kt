package com.example.alarmgroups.domain.repository

import com.example.alarmgroups.data.model.relations.GroupWithAlarms
import com.example.alarmgroups.domain.model.Group
import kotlinx.coroutines.flow.Flow

interface GroupRepository {

    fun getAllGroups(): Flow<List<Group>>

    suspend fun getGroup(id: Long): Group?

    suspend fun insert(group: Group) : Long

    suspend fun update(group: Group)

    fun getGroupWithAlarms(groupId: Long): Flow<List<GroupWithAlarms>>

}