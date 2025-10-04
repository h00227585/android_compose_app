package com.hdy.compose_examples.ui.dice_roller

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hdy.compose_examples.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiceRollerScreen(
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("掷筛子") },
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
            DiceRoller(modifier = Modifier.padding(paddingValues))
        }
    )
}

@Composable
fun DiceRoller(modifier: Modifier = Modifier
) {
    DiceWithButtonAndImage(modifier = modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.Center))
}

@Composable
fun DiceWithButtonAndImage(modifier: Modifier = Modifier) {
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // composable function 随时可能被调用，普通变量不能持久存储
        // 通过 remember 规避这一点
        var result by remember { mutableIntStateOf(1) }

        val imageResource = when (result) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }

        Image(painter = painterResource(imageResource), contentDescription = result.toString())
        Spacer(modifier = Modifier.padding(6.dp))
        Button(onClick = { result = (1..6).random() }) {
            Text("Roll")
        }
    }
}
