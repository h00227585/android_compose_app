package com.hdy.compose_examples.ui.blur

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hdy.compose_examples.R
import com.hdy.compose_examples.data.local.staticdata.model.BlurAmount
import androidx.core.net.toUri
import com.hdy.compose_examples.utils.Log

private const val TAG = "BlurScreen"

@Composable
fun BlurScreen(blurViewModel: BlurViewModel = viewModel(factory = BlurViewModel.Factory)) {
    val uiState by blurViewModel.blurUiState.collectAsStateWithLifecycle()
    Log.d(TAG, "uiState: $uiState")
    val layoutDirection = LocalLayoutDirection.current
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(
                start = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateStartPadding(layoutDirection),
                end = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateEndPadding(layoutDirection)
            )
    ) {
        BlurScreenContent(
            blurUiState = uiState,
            blurAmountOptions = blurViewModel.blurAmount,
            applyBlur = blurViewModel::applyBlur,
            cancelWork = blurViewModel::cancelWork,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}

@Composable
fun BlurScreenContent(
    blurUiState: BlurUiState,
    blurAmountOptions: List<BlurAmount>,
    applyBlur: (Int) -> Unit,
    cancelWork: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedValue by rememberSaveable { mutableStateOf(1) }
    val context = LocalContext.current
    Column(modifier = modifier) {
        Image(
            painter = painterResource(R.drawable.android_cupcake),
            contentDescription = stringResource(R.string.description_image),
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            contentScale = ContentScale.Fit,
        )
        BlurAmountContent(
            selectedValue = selectedValue,
            blurAmounts = blurAmountOptions,
            modifier = Modifier.fillMaxWidth(),
            onSelectedValueChange = { selectedValue = it }
        )
        // 点击 Start 按钮进行模糊处理并保存文件
        // 点击 See File 查看处理后的文件
        BlurActions(
            blurUiState = blurUiState,
            onStartClick = { applyBlur(selectedValue) },
            onSeeFileClick = { currentUri ->
                showBlurredImage(context, currentUri)
            },
            onCancelClick = { cancelWork() },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun BlurActions(
    blurUiState: BlurUiState,
    onStartClick: () -> Unit,
    onSeeFileClick: (String) -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        when (blurUiState) {
            is BlurUiState.Default -> {
                Button(
                    onClick = onStartClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.start))
                }
            }

            // 处理中，显示取消按钮和进度条
            is BlurUiState.Loading -> {
                FilledTonalButton(onCancelClick) { Text(stringResource(R.string.cancel_work)) }
                CircularProgressIndicator(modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)))
            }

            // 处理完成，显示Start和See File
            is BlurUiState.Complete -> {
                Button(onStartClick) { Text(stringResource(R.string.start)) }
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
                FilledTonalButton({ onSeeFileClick(blurUiState.outputUri) })
                { Text(stringResource(R.string.see_file)) }
            }
        }
    }
}

@Composable
private fun BlurAmountContent(
    selectedValue: Int,
    blurAmounts: List<BlurAmount>,
    modifier: Modifier = Modifier,
    onSelectedValueChange: (Int) -> Unit
) {
    Column(
        modifier = modifier.selectableGroup()
    ) {
        Text(
            text = stringResource(R.string.blur_title),
            style = MaterialTheme.typography.headlineSmall
        )
        blurAmounts.forEach { amount ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        role = Role.RadioButton,
                        selected = (selectedValue == amount.blurAmount),
                        onClick = { onSelectedValueChange(amount.blurAmount) }
                    )
                    .size(48.dp)
            ) {
                RadioButton(
                    selected = (selectedValue == amount.blurAmount),
                    onClick = null,
                    modifier = Modifier.size(48.dp),
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary
                    )
                )
                Text(stringResource(amount.blurAmountRes))
            }
        }
    }
}

// 因为currentUri是处理后的图片路径，
// 这段代码会尝试跳转到设备上最合适的图片查看器 Activity，
// 跳转到的 Activity 不是固定的，但它是设备上的以下 Activity 之一：
// - 默认图库应用（如 Google Photos 或 OEM 自带的图库）中的图片查看界面。
// - 文件管理器应用中内置的图片预览 Activity。
// - 任何注册了处理 ACTION_VIEW 和 image/* MIME 类型的第三方图片应用中的 Activity。
private fun showBlurredImage(context: Context, currentUri: String) {
    val uri = if (currentUri.isNotEmpty()) {
        currentUri.toUri()
    } else {
        null
    }
    val actionView = Intent(Intent.ACTION_VIEW, uri)
    context.startActivity(actionView)
}