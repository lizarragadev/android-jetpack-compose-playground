package com.droidcon.composablebank.ui.animations_effects

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.droidcon.composablebank.utils.CustomTopAppBar
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.droidcon.composablebank.components.InteractiveButton

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Crossfade(navController: NavController, name: String) {
    var selectedState by remember { mutableStateOf("Home") }
    val states = listOf("Home", "Search", "Profile")

    Scaffold(
        topBar = {
            CustomTopAppBar(title = name, navController = navController)
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Select a State",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                states.forEach { state ->
                    InteractiveButton(
                        text = state,
                        onClick = { selectedState = state },
                        backgroundColor = if (state == selectedState) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                        contentColor = if (state == selectedState) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Crossfade(
                    targetState = selectedState,
                    animationSpec = tween(1000),
                    label = "CrossfadeExample"
                ) { currentState ->
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .background(getColorForState(currentState)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = currentState,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                        )
                    }
                }
            }
        }
    )
}

private fun getColorForState(state: String): Color {
    return when (state) {
        "Home" -> Color.Red
        "Search" -> Color.Green
        "Profile" -> Color.Blue
        else -> Color.DarkGray
    }
}