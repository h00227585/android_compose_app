package com.hdy.compose_examples.ui.business_card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hdy.compose_examples.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessCardScreen(
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("名片") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center // 整体垂直居中
            ) {
                Head(
                    fullName = "Man Hu",
                    title = "Android Developer",
                )

                Spacer(modifier = Modifier.height(60.dp)) // 控制 Head 和 Footer 间距

                Footer(
                    phoneNumber = "+86 xxxxxxxxxxx",
                    socialMedia = "xxx@socialmediea",
                    email = "xxx@yyy.com"
                )
            }
        }
    )
}

@Composable
fun Head(fullName: String, title: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.android_logo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.DarkGray)
        )
        Text(
            text = fullName,
            fontSize = 36.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = title,
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF2196F3)
        )
    }
}

@Composable
fun Footer(phoneNumber: String,
           socialMedia: String,
           email: String) {
    Column(
        modifier = Modifier.alpha(0.8f),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        InfoRow(Icons.Filled.Phone, phoneNumber, iconTint = Color(0xFF2196F3))
        InfoRow(Icons.Filled.Share, socialMedia, iconTint = Color(0xFF2196F3))
        InfoRow(Icons.Filled.Email, email, iconTint = Color(0xFF2196F3))
    }
}

@Composable
private fun InfoRow(icon: ImageVector,
                    text: String,
                    iconTint: Color = MaterialTheme.colorScheme.primary) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint
        )
        Spacer(Modifier.width(8.dp))
        Text(text = text)
    }
}
