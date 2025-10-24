package com.hdy.compose_examples.ui.basic.layout

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId


// 实现比较复杂的布局需求时，可以使用constraint layout，
// 代替 Column, Row, Box等布局

// 解耦API: 将约束抽取出来
// TODO: 旋转模式器没有改变
@Composable
fun ConstraintLayout4() {
    BoxWithConstraints {
        println("$maxWidth, $maxHeight")
        val constraints = remember(maxWidth, maxHeight) {
            if (maxWidth < maxHeight) {
                decoupledConstraints(2.dp)
            } else {
                decoupledConstraints(32.dp)
            }
        }

        ConstraintLayout(
            constraintSet = constraints)
        {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.layoutId("button")
            ) {
                Text(text = "button")
            }
            Text(
                text = "text",
                modifier = Modifier.layoutId("text")
            )
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")

        constrain(button) {
            top.linkTo(parent.top, margin)
        }

        constrain(text) {
            top.linkTo(button.bottom, margin)
        }
    }
}