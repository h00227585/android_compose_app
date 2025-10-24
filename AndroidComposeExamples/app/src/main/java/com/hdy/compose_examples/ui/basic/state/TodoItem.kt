package com.hdy.compose_examples.ui.basic.state

import com.android.identity.util.UUID

data class TodoItem(
    val task: String,
    val icon: TodoIcon,
    val id: UUID = UUID.randomUUID()
)