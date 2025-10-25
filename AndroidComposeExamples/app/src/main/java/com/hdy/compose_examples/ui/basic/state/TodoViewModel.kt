package com.hdy.compose_examples.ui.basic.state

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TodoViewModel() : ViewModel() {
    private val _todoItems = MutableLiveData(emptyList<TodoItem>())
    val todoItems: LiveData<List<TodoItem>> = _todoItems

    private val _selectedIcon = MutableLiveData(TodoIcon.Default)
    val selectedIcon: LiveData<TodoIcon> = _selectedIcon

    fun initialize(initData: List<TodoItem>) {
        // 仅在第一次初始化时设置数据（防止重复）
        if (_todoItems.value.isNullOrEmpty()) {
            _todoItems.value = initData
        }
    }

    fun addItem(item: TodoItem) {
        val currentList = _todoItems.value?.toMutableList() ?: mutableListOf()
        currentList.add(item)
        _todoItems.value = currentList
    }

    fun removeItem(item: TodoItem) {
        val currentList = _todoItems.value?.toMutableList() ?: mutableListOf()
        currentList.remove(item)
        _todoItems.value = currentList
    }

    fun selectIcon(icon: TodoIcon) {
        _selectedIcon.value = icon
    }
}