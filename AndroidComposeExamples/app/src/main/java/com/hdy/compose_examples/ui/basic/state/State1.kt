package com.hdy.compose_examples.ui.basic.state

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

// 无状态的静态页面

@Composable
fun State1() {
    TodoList(TodoData.data)
}

@Composable
private fun TodoList(todoList: List<TodoItem>) {
    Column(modifier = Modifier
        .height(320.dp)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(8.dp, 12.dp)
        ) {
            items(todoList) {
                TodoRow(todoItem = it)
            }
        }
        Button(
            onClick = { /*TODO*/ },
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
    modifier: Modifier = Modifier
) {
    Row( // 记得设置 fillMaxWidth()，否则不会均匀分布在两端
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween  // 子控件均匀分布
    ) {
        Text(
            text = todoItem.task
        )
        Icon(
            imageVector = todoItem.icon.imageVector,
            contentDescription = stringResource(id = todoItem.icon.description)
        )
    }
}
