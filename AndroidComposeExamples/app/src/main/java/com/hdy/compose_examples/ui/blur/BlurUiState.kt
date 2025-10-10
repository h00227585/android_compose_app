package com.hdy.compose_examples.ui.blur

// 当一个状态不仅可以表示状态是什么，还可以携带参数的时候，使用sealed interface/class，而不是enum class
// sealed interface/class的子类（或实现）必须在同一个文件或同一个模块内定义。
//
// sealed interface的子类可以是不同的类型、拥有不同的参数、更灵活
sealed interface BlurUiState {
    object Default : BlurUiState
    object Loading : BlurUiState
    data class Complete(val outputUri: String) : BlurUiState
}