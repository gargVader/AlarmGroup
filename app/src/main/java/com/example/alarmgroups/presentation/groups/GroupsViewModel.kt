package com.example.alarmgroups.presentation.groups

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarmgroups.domain.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    private val repo: GroupRepository
) : ViewModel() {

    var state by mutableStateOf(GroupsState())

    init {
        getAllGroups()
    }

    private fun getAllGroups() {
        viewModelScope.launch {
            repo.getAllGroupWithAlarms().collect {
                if (!it.isNullOrEmpty()) {
                    state = state.copy(groupWithAlarmsList = it)
                }
            }
        }

    }

}