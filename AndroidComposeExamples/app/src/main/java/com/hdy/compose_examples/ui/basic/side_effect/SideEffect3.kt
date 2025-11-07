package com.hdy.compose_examples.ui.basic.side_effect

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import com.hdy.compose_examples.utils.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// rememberUpdatedState()

@Composable
fun SideEffect3(
    modifier: Modifier = Modifier) {
    val onTimeout1: () -> Unit = { Log.d(msg = "1 竖屏") }
    val onTimeout2: () -> Unit = { Log.d(msg = "2 横屏") }

    val onTimeoutState = remember { mutableStateOf(onTimeout1) }

    Column {
        Button(
            onClick = {
                if (onTimeoutState.value == onTimeout1) {
                    onTimeoutState.value = onTimeout2
                } else {
                    onTimeoutState.value = onTimeout1
                }
            }
        ) {
            Text(text = "Choose onTimeout${if (onTimeoutState.value == onTimeout1) 1 else 2}")
        }
        Timer(onTimeoutState.value)
    }
}

@Composable
fun Timer(onTimeout: () -> Unit) {
    // 关键：始终保持 onTimeout 的最新引用
    val updatedOnTimeout = rememberUpdatedState(onTimeout)

    LaunchedEffect(Unit) {
        repeat(6) {
            delay(1000)
            Log.d(msg = "delay ${it + 1} s")
        }
        updatedOnTimeout.value() // 永远调用最新的回调
    }
}



