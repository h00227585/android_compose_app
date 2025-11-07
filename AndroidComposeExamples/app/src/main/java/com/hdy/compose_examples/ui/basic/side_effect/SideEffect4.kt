package com.hdy.compose_examples.ui.basic.side_effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.hdy.compose_examples.utils.Log


// DisposableEffect

@Composable
fun SideEffect4() {
    val onStart: () -> Unit = { Log.d(msg = "onStart") }
    val onStop: () -> Unit = { Log.d(msg = "onStop") }

    InnerScreen(onStart, onStop)
}

@Composable
fun InnerScreen(
    onStart: () -> Unit, // Send the 'started' analytics event
    onStop: () -> Unit, // Send the 'stopped' analytics event
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    // Safely update the current lambdas when a new one is provided
    val currentOnStart by rememberUpdatedState(onStart)
    val currentOnStop by rememberUpdatedState(onStop)

    // If `lifecycleOwner` changes, dispose and reset the effect
    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        // for sending analytics events
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                currentOnStart()
            } else if (event == Lifecycle.Event.ON_STOP) {
                currentOnStop()
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}
