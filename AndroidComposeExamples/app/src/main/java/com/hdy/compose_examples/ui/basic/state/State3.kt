package com.hdy.compose_examples.ui.basic.state

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.random.Random

// 状态提升+单向数据流: 将状态放到viewmodel
// remember: 保存重组时不需要改变的内部状态。

@Composable
fun State3(
    todoViewModel: TodoViewModel = viewModel(),
    initData: List<TodoItem> = TodoData.data
) {
    val todoItems: List<TodoItem> by todoViewModel.todoItems.observeAsState(emptyList())

    // 副作用：初始化加载用户数据，只执行一次
    LaunchedEffect(initData) {
        todoViewModel.initialize(initData)
    }

    TodoList(todoList = todoItems,
        onItemAdd = { todoViewModel.addItem(it) },
        onItemRemove = { todoViewModel.removeItem(it) })
}

@Composable
private fun TodoList(
    todoList: List<TodoItem>,
    onItemAdd: (TodoItem) -> Unit,
    onItemRemove: (TodoItem) -> Unit
) {
    Column(modifier = Modifier
        .height(320.dp)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(8.dp, 12.dp)
        ) {
            items(items = todoList) {
                TodoRow(
                    todoItem = it,
                    onItemClick = onItemRemove)
            }
        }
        Button(
            onClick = { onItemAdd(generateRandomTodoItem()) },
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Add random task"
            )
        }
    }
}

@Composable
private fun TodoRow(
    todoItem: TodoItem,
    onItemClick: (item: TodoItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Row( // 记得设置 fillMaxWidth()，否则不会均匀分布在两端
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onItemClick(todoItem) },
        horizontalArrangement = Arrangement.SpaceBetween,  // 子控件均匀分布
    ) {
        Text(
            text = todoItem.task
        )

        // TODO:
        // add的时候是正确的，
        // remove的时候是错误的，透明度发生了改变。
        val iconAlpha: Float = remember(todoItem.id) { Random.nextFloat().coerceIn(0.3f, 0.9f) }

        Icon(
            imageVector = todoItem.icon.imageVector,
            contentDescription = stringResource(id = todoItem.icon.description),
            tint = LocalContentColor.current.copy(alpha = iconAlpha)
        )
    }
}

private fun generateRandomTodoItem(): TodoItem {
    val randomTask = "Task ${Random.nextInt(1000)}"
    val randomIcon = TodoIcon.entries.toTypedArray().random()
    return TodoItem(task = randomTask, randomIcon)
}
