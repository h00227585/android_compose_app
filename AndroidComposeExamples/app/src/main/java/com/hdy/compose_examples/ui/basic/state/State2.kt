package com.hdy.compose_examples.ui.basic.state

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

// ViewModel+LiveData在compose中的简单示例

@Composable
fun State2(helloViewModel: HelloViewModel = viewModel()) {
    // 观察 TextField 的当前输入值
    // 使用 observeAsState() 将 LiveData 转换为 Compose 的可观测状态 (State)
    val currentInputValue by helloViewModel.currentInput.observeAsState("")

    // 观察需要显示的最终文本
    val displayText by helloViewModel.displayedContent.observeAsState("Hello")

    Column {
        // 显示累加后的文本
        Text(displayText)

        TextField(
            // TextField 的值绑定到 ViewModel 的当前输入状态
            value = currentInputValue,
            // 当值改变时，调用 ViewModel 的方法
            onValueChange = { newContent ->
                helloViewModel.onContentChanged(newContent)
            },
            label = { Text("输入内容") } // 增加一个标签
        )
    }
}

class HelloViewModel: ViewModel() {
    // 用于保存当前的 TextField 输入内容
    private val _currentInput = MutableLiveData<String>("")
    val currentInput: LiveData<String> = _currentInput

    // 用于保存所有已输入的、不为空的内容（以逗号分隔）
    private val _displayedContent = MutableLiveData<String>("Hello")
    val displayedContent: LiveData<String> = _displayedContent

    fun onContentChanged(newContent: String) {
        _currentInput.value = newContent
        updateDisplayedContent(newContent)
    }

    private fun updateDisplayedContent(newInput: String) {
        // 只有当输入不为空时才追加
        if (newInput.isNotBlank()) {
            // 获取当前的显示内容，并追加新的输入内容
            val currentBaseText = _displayedContent.value ?: "Hello"

            // 检查是否已经包含这个输入（防止重复追加，可以根据你的需求调整逻辑）
            // 这里我们假设只有当输入变化时才追加，并且如果上一个显示内容已经包含新的输入，则不追加。
            // 更好的做法是：基础文本 + "," + 新的输入，但你需要决定如何处理已有的输入。
            // 按照你的要求 "text的显示内容是增加", {textarea} 的内容"：
            // 我们将“Hello”作为初始内容，之后每次输入非空内容就追加到末尾。

            val newDisplayedText = "Hello, $newInput"
            _displayedContent.value = newDisplayedText
        } else {
            _displayedContent.value = "Hello"
        }
        // 如果输入为空，我们保持 _displayedContent 不变，或者根据需求重置。
        // 这里我们选择保持不变，除非你希望输入为空时移除最后一个追加的内容。
    }
}


