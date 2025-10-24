package com.hdy.compose_examples.ui.basic.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hdy.compose_examples.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// animateScrollToItem是suspend函数，需要在协程作用域调用
@Composable
fun List2(modifier: Modifier = Modifier) {
    val listSize = 16
    val scrollState: LazyListState = rememberLazyListState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()  // 协程作用域

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            // Note:
            // 如果使用Modifier.padding()会影响垂直对齐
            // 如果使用Modifier.width()也会影响垂直对齐
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    coroutineScope.launch {
                        scrollState.animateScrollToItem(0)
                    }
                }
            ) {
                Text(text = "Scroll to top")
            }
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    coroutineScope.launch {
                        scrollState.animateScrollToItem(listSize - 1)
                    }
                }
            ) {
                Text(text = "Scroll to bottom")
            }
        }
        ImageList(
            size = listSize,
            scrollState = scrollState
        )
    }
}
@Composable
private fun ImageList(size: Int, scrollState: LazyListState) {
    LazyColumn(
        state = scrollState
    ) {
        items(size) {
            ImageItem(index = it)
        }
    }
}
@Composable
private fun ImageItem(index: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp, horizontal = 16.dp)
    ) {
        Image(painter = painterResource(
            id = R.drawable.ic_launcher_background),
            contentDescription = "",
            modifier = Modifier
                .size(26.dp)
                .clip(shape = CircleShape))
        Spacer(modifier = Modifier.size(9.dp))
        Text(text = "Text item $index", style = MaterialTheme.typography.bodyMedium)
    }
}