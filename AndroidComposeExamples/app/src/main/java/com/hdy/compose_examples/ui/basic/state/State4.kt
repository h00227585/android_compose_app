package com.hdy.compose_examples.ui.basic.state

import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.identity.documenttype.Icon

// 状态提升+单向数据流: 将状态放到viewmodel
// remember: 保存重组时不需要改变的内部状态。

@Composable
fun State4(
    todoViewModel: TodoViewModel = viewModel(),
    initData: List<TodoItem> = TodoData.data
) {
    // 副作用：初始化加载用户数据，只执行一次
    LaunchedEffect(initData) {
        todoViewModel.initialize(initData)
    }

    TodoItemInput(todoViewModel)
}

@Composable
private fun TodoItemInput(
    viewModel: TodoViewModel,
    modifier: Modifier = Modifier.height(320.dp)
) {
    val todoItems by viewModel.todoItems.observeAsState(emptyList())

    val taskContent = remember { mutableStateOf("") }
    val currIcon = remember { mutableStateOf(TodoIcon.Default) }

    val listState = rememberLazyListState()

    Column(modifier = modifier) {
        // 新增的输入行
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = taskContent.value,
                onValueChange = { taskContent.value = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Enter task") }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    viewModel.addItem(TodoItem(taskContent.value, currIcon.value))
                    taskContent.value = "" // ✅ 重置输入内容
                    currIcon.value = TodoIcon.Default // ✅ 重置图标
                },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(),
                enabled = taskContent.value.isNotBlank(),
            ) {
                Text("Add")
            }
        }

        // 图标选择行
        if (taskContent.value.isNotBlank()) {
            AnimatedIconRow(
                todoIcon = currIcon.value,
                onIconSelected = { newIcon -> currIcon.value = newIcon },
                visible = true
            )
        } else {
            Spacer(modifier = Modifier.height(4.dp))
        }


        // 分隔线
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            state = listState
        ) {
            items(todoItems) { item ->
                TodoRow(
                    todoItem = item,
                    onItemClick = { viewModel.removeItem(item) }
                )
            }
        }
    }
}

@Composable
fun AnimatedIconRow(
    todoIcon: TodoIcon,
    onIconSelected: (TodoIcon) -> Unit,
    visible: Boolean,
    modifier: Modifier = Modifier
) {
    val enter = remember { fadeIn(TweenSpec(300, easing = FastOutLinearInEasing)) }
    val exit = remember { fadeOut(TweenSpec(100, easing = FastOutSlowInEasing)) }
    Box(modifier.defaultMinSize(minHeight = 16.dp)) {
        AnimatedVisibility(
            visible = visible,
            enter = enter,
            exit = exit
        ) {
            IconRow(
                todoIcon = todoIcon,
                onIconSelected = onIconSelected,
                modifier = modifier)
        }
    }
}

@Composable
fun IconRow(
    todoIcon: TodoIcon,
    onIconSelected: (TodoIcon) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        TodoIcon.entries.forEach { icon ->
            SelectableIcon(
                imageVector = icon.imageVector,
                description = stringResource(icon.description),
                onIconSelected = { onIconSelected(icon) },
                isSelected = (todoIcon == icon)
            )
        }
    }
}

@Composable
fun SelectableIcon(
    imageVector: ImageVector,
    description: String,
    onIconSelected: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = { onIconSelected() },
        shape = CircleShape,
        modifier = modifier
    ) {
        val iconTint = if (isSelected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = description,
                tint = iconTint
            )
            if (isSelected) {
                // 为了保证下方有一条线，必须设置background属性
                Box(modifier = Modifier
                    .padding(3.dp)
                    .width(imageVector.defaultWidth)
                    .height(1.dp)
                    .background(iconTint)
                )
            } else {
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
private fun TodoRow(
    todoItem: TodoItem,
    onItemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onItemClick() },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = todoItem.task)
        Icon(
            imageVector = todoItem.icon.imageVector,
            contentDescription = stringResource(id = todoItem.icon.description)
        )
    }
}
