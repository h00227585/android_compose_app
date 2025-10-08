package com.hdy.compose_examples.ui.race_tracker

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RaceTrackerScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        RaceTrackerApp()
    }
}