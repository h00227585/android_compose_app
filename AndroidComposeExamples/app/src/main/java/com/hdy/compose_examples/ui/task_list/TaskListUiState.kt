package com.hdy.compose_examples.ui.task_list

/**
 * 任务列表的 UI 状态
 */
data class TaskListUiState(
    val tasks: List<String> = emptyList(),
    val isLoading: Boolean = false
)