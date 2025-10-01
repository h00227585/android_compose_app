package com.hdy.compose_examples.ui.task_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


/**
 * 任务列表 ViewModel
 */
class TaskListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        TaskListUiState(
            tasks = listOf("学习 Compose", "写代码", "喝咖啡")
        )
    )
    val uiState: StateFlow<TaskListUiState> = _uiState.asStateFlow()

    /**
     * 添加任务
     */
    fun addTask(task: String) {
        if (task.isBlank()) return

        viewModelScope.launch {
            val currentTasks = _uiState.value.tasks
            _uiState.value = _uiState.value.copy(
                tasks = currentTasks + task
            )
        }
    }

    /**
     * 删除任务
     */
    fun deleteTask(task: String) {
        viewModelScope.launch {
            val currentTasks = _uiState.value.tasks
            _uiState.value = _uiState.value.copy(
                tasks = currentTasks - task
            )
        }
    }
}