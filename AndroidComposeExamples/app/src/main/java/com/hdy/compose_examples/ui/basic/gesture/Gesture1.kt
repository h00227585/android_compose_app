package com.hdy.compose_examples.ui.basic.gesture

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput


// 点击

@Composable
fun Gesture1(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ClickDemo()
    }
}

@Composable
fun ClickDemo() {
    var count1 by remember {
        mutableIntStateOf(0)
    }
    Text(
        text = "$count1",
        modifier = Modifier
            .clickable {
                count1 += 1
            }
    )

    var count2 by remember {
        mutableIntStateOf(0)
    }
    Text(
        text = "$count2",
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { count2 += 1 }
                )
            }
    )
}