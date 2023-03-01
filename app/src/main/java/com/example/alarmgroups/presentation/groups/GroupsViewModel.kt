package com.example.alarmgroups.presentation.groups

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarmgroups.domain.model.Group
import com.example.alarmgroups.domain.repository.AlarmRepository
import com.example.alarmgroups.domain.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
    private val alarmRepository: AlarmRepository
) : ViewModel() {

    var state by mutableStateOf(GroupsScreenState())

    init {
        getAllGroups()
    }

    fun onEvent(event: GroupsScreenEvents) {
        when (event) {
            is GroupsScreenEvents.ShowNewGroupDialog -> {
                state = if (event.show) {
                    state.copy(showNewGroupDialog = true, newGroupName = "")

                } else {
                    state.copy(showNewGroupDialog = false)
                }
            }
            is GroupsScreenEvents.OnNewGroupNameValueChange -> {
                state = state.copy(newGroupName = event.value)
            }
        }
    }

    private fun getAllGroups() {
        viewModelScope.launch {
            groupRepository.getAllGroupWithAlarms().collect {
                if (!it.isNullOrEmpty()) {
                    state = state.copy(groupWithAlarmsList = it)
                }
            }
        }
    }

    private fun createNewGroup(group: Group) {
        viewModelScope.launch {
            // Create group
            val groupId = groupRepository.insert(group)
            // Update all alarms with the given alarm ids with this new groupId
            // TODO :
        }
    }


}