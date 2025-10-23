package com.hdy.compose_examples.ui.basic

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout


// 实现比较复杂的布局需求时，可以使用constraint layout，
// 代替 Column, Row, Box等布局
@Composable
fun ConstraintLayout1() {
    ConstraintLayout {
        // 创建引用
        val (button, text) = createRefs()

        Button(
            onClick = { /* Do something */ },
            // 将引用button赋值给Button组件并添加约束
            modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text("Button")
        }

        Text(
            "Text",
            // 将引用赋值给Text组件并添加约束
            Modifier.constrainAs(text) {
                top.linkTo(button.bottom, margin = 2.dp)
                centerHorizontallyTo(parent)
            }
        )
    }
}