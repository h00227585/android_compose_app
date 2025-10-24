package com.hdy.compose_examples.ui.basic.layout

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout


// 实现比较复杂的布局需求时，可以使用constraint layout，
// 代替 Column, Row, Box等布局
@Composable
fun ConstraintLayout2() {
    ConstraintLayout {
        // 创建引用（类似于视图ID）
        val (button1, button2, text) = createRefs()

        Button(
            onClick = { /* Do something */ },
            modifier = Modifier.constrainAs(button1) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start, margin = 16.dp)
            }
        ) {
            Text("Left button")
        }

        Text(
            "Hello",
            modifier = Modifier.constrainAs(text) {
                top.linkTo(button1.bottom, margin = 16.dp)
                centerAround(button1.end)
            }
        )

        // 将button1和text组合起来
        val barrier = createEndBarrier(button1, text)

        Button(
            onClick = { /* Do something */ },
            modifier = Modifier.constrainAs(button2) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(barrier)
            }
        ) {
            Text("Right button")
        }
    }
}