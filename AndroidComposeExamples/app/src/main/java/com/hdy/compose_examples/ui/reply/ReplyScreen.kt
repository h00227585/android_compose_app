package com.hdy.compose_examples.ui.reply

import android.app.Activity
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hdy.compose_examples.data.model.Email
import com.hdy.compose_examples.data.model.MailboxType
import com.hdy.compose_examples.ui.reply.util.ReplyNavigationType
import com.hdy.compose_examples.ui.theme.AndroidComposeExamplesTheme


// 如果是竖屏，导航在下方
// 如果是横屏，导航在左侧
//
// home page 没有返回 button，可以通过返回按键返回
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun ReplyScreen(
    onBackClick: () -> Unit = {}
) {
    AndroidComposeExamplesTheme {
        val layoutDirection = LocalLayoutDirection.current
        Surface (
            modifier = Modifier
                .padding(
                    start = WindowInsets.safeDrawing.asPaddingValues()
                        .calculateStartPadding(layoutDirection),
                    end = WindowInsets.safeDrawing.asPaddingValues()
                        .calculateEndPadding(layoutDirection)
                )
        ) {
            val viewModel: ReplyViewModel = viewModel()
            val replyUiState = viewModel.uiState.collectAsState().value

            val windowSize = calculateWindowSizeClass(LocalContext.current as Activity).widthSizeClass

            val navigationType: ReplyNavigationType = when (windowSize) {
                WindowWidthSizeClass.Compact -> {   // 小屏幕
                    ReplyNavigationType.BOTTOM_NAVIGATION
                }

                WindowWidthSizeClass.Medium -> {   // 中屏幕
                    ReplyNavigationType.NAVIGATION_RAIL
                }

                WindowWidthSizeClass.Expanded -> {   // 大屏幕
                    ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER
                }

                else -> {
                    ReplyNavigationType.BOTTOM_NAVIGATION
                }
            }
            ReplyHomeScreen(
                navigationType = navigationType,
                replyUiState = replyUiState,
                onTabPressed = { mailboxType: MailboxType ->
                    viewModel.updateCurrentMailbox(mailboxType = mailboxType)
                    viewModel.resetHomeScreenStates()
                },
                onEmailCardPressed = { email: Email ->
                    viewModel.updateDetailsScreenStates(
                        email = email
                    )
                },
                onDetailScreenBackPressed = {
                    viewModel.resetHomeScreenStates()
                }
            )
        }
    }
}