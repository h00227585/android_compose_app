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
    private val hello = "Hello"

    // 用于保存当前的 TextField 输入内容
    private val _currentInput = MutableLiveData("")
    val currentInput: LiveData<String> = _currentInput

    // 用于保存所有已输入的、不为空的内容（以逗号分隔）
    private val _displayedContent = MutableLiveData(hello)
    val displayedContent: LiveData<String> = _displayedContent

    fun onContentChanged(newContent: String) {
        _currentInput.value = newContent
        updateDisplayedContent(newContent)
    }

    private fun updateDisplayedContent(newInput: String) {
        // 只有当输入不为空时才追加
        if (newInput.isNotBlank()) {
            val newDisplayedText = "$hello, $newInput"
            _displayedContent.value = newDisplayedText
        } else {
            _displayedContent.value = hello
        }
        // 如果输入为空，我们保持 _displayedContent 不变，或者根据需求重置。
        // 这里我们选择保持不变，除非你希望输入为空时移除最后一个追加的内容。
    }
}


