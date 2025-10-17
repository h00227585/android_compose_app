package com.hdy.compose_examples.ui.basic

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hdy.compose_examples.R

// Surface: 给文本设置背景
// animateColorAsState: 颜色切换
@Composable
fun List3(modifier: Modifier = Modifier) {
    MessageList(modifier = modifier, msgList = MessageData.messageList)
}
@Composable
private fun MessageList(modifier: Modifier = Modifier, msgList: List<Message>) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp) // 列表整体边距
    ) {
        items(msgList) { msg ->
            MessageItem(msg = msg)
            HorizontalDivider(// 添加分割线
                modifier = Modifier.padding(horizontal = 16.dp),
                thickness = 2.2.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
            )
        }
    }
}
@Composable
private fun MessageItem(msg: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp) // 调整间距
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Profile image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(12.dp)) // 图片和文本之间的间距
        // enableExpand: 控制内容是否展开显示
        // 通过 by remember 可以保存 enableExpand 的值，重绘的时候可以使用该值
        var enableExpand by remember { mutableStateOf(false) }
        // 切换颜色
        val surfaceColor: Color by animateColorAsState(targetValue =
            if (enableExpand) {
                MaterialTheme.colorScheme.surfaceTint
            } else {
                MaterialTheme.colorScheme.surface
            }, label = ""
        )
        Column(
            modifier = Modifier.clickable { enableExpand = !enableExpand }
        ) {
            Text(
                text = msg.title,
                style = MaterialTheme.typography.titleMedium, // 使用主题样式
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp)) // 减小标题和内容间距
            Surface(
                color = surfaceColor,
                // 设置内容区域的圆角大小
                shape = MaterialTheme.shapes.small,
                // 内容区域逐渐变大或缩小
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = msg.context,
                    style = MaterialTheme.typography.bodyMedium,
//                maxLines = 2, // 限制行数防止溢出
                    maxLines = if (enableExpand) Int.MAX_VALUE else 2,
                    overflow = TextOverflow.Ellipsis // 超出显示省略号
                )
            }
        }
    }
}
data class Message(val title: String, val context: String)
object MessageData {
    val messageList = listOf(
        Message(
            title = "Morning Greeting",
            context = "Good morning! Have a great day."
        ),
        Message(
            title = "Weather Update",
            context = "Today will be sunny with a high of 75°F."
        ),
        Message(
            title = "Reminder",
            context = "Don't forget the meeting at 2 PM."
        ),
        Message(
            title = "Lunch Plans",
            context = "Let's grab lunch at the new cafe."
        ),
        Message(
            title = "Weekend Plans",
            context = "We should go hiking this weekend."
        ),
        Message(
            title = "Book Recommendation",
            context = "I just read a great book you might like."
        ),
        Message(
            title = "Movie Night",
            context = "How about watching a movie tonight?"
        ),
        Message(
            title = "Project Update",
            context = "The project is on track for Friday."
        ),
        Message(
            title = "Travel Ideas",
            context = "Cheng du would be perfect for our next trip." +
                    "Cheng du would be perfect for our next trip." +
                    "Cheng du would be perfect for our next trip."
        ),
        Message(
            title = "Fitness Goal",
            context = "I ran 5 miles today. Feeling great!"
        ),
        Message(
            title = "Recipe Share",
            context = "Try this delicious pasta recipe I found."
        ),
        Message(
            title = "Tech News",
            context = "New smartphone released with amazing features."
        )
    )
}