package com.hdy.compose_examples.ui.basic.state

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.RestoreFromTrash
import androidx.compose.ui.graphics.vector.ImageVector
import com.hdy.compose_examples.R

enum class TodoIcon(
    val imageVector: ImageVector,
    @field:StringRes val description: Int
) {
    Expand(Icons.Default.CropSquare, R.string.expand),
    Done(Icons.Default.Done, R.string.done),
    Event(Icons.Default.Event, R.string.event),
    Privacy(Icons.Default.PrivacyTip, R.string.privacy),
    Restore(Icons.Default.RestoreFromTrash, R.string.restore);

    companion object {
        val Default = Expand

        fun random(): TodoIcon {
            return entries.random()
        }
    }
}