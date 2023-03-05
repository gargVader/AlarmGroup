package com.example.alarmgroups.data.repository

import com.example.alarmgroups.data.data_source.GroupDao
import com.example.alarmgroups.domain.model.Group
import com.example.alarmgroups.domain.model.GroupWithAlarms
import com.example.alarmgroups.domain.repository.GroupRepository
import com.example.alarmgroups.mapper.toGroup
import com.example.alarmgroups.mapper.toGroupEntity
import com.example.alarmgroups.mapper.toGroupWithAlarms
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val dao: GroupDao
) : GroupRepository {
    override fun getAllGroups(): Flow<List<Group>> {
        return dao.getAllGroups().map {
            it.map { groupEntity ->
                groupEntity.toGroup()
            }
        }
    }

    override suspend fun getGroup(id: Long): Group? {
        return dao.getGroup(id)?.toGroup()
    }

    override suspend fun insert(group: Group): Long {
        return dao.insert(group.toGroupEntity())
    }

    override suspend fun update(group: Group) {
        dao.update(group.toGroupEntity())
    }

    override suspend fun updateGroupIsActive(groupId: Long, isActive: Boolean) {
        dao.updateGroupIsActive(groupId, isActive)
    }

    override fun getGroupWithAlarms(groupId: Long): Flow<List<GroupWithAlarms>> {
        return dao.getGroupWithAlarms(groupId).map {
            it.map { groupWithAlarmsRelation ->
                groupWithAlarmsRelation.toGroupWithAlarms()
            }
        }
    }

    override fun getAllGroupWithAlarms(): Flow<List<GroupWithAlarms>> {
        return dao.getAllGroupWithAlarms().map {
            it.map { groupWithAlarmsRelation ->
                groupWithAlarmsRelation.toGroupWithAlarms()
            }
        }
    }
}