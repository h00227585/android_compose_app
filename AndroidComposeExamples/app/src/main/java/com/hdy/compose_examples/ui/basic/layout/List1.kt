package com.hdy.compose_examples.ui.basic.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


// note: column如果需要滚动，需要通过Modifier进行配置，例如，verticalScroll
@Composable
fun List1(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        repeat(20) {
            Text(
                text = "Text item $it",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}