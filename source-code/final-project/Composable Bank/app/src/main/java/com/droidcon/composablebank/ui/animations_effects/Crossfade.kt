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
    val availableStates = listOf("Home", "Search", "Profile")

    Scaffold(
        topBar = { CustomTopAppBar(title = name, navController = navController) },
        content = { paddingValues ->
            MainCrossfadeContent(
                paddingValues = paddingValues,
                selectedState = selectedState,
                states = availableStates,
                onStateSelected = { selectedState = it }
            )
        }
    )
}

@Composable
private fun MainCrossfadeContent(
    paddingValues: PaddingValues,
    selectedState: String,
    states: List<String>,
    onStateSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StateSelectionHeader()
        StateSelector(
            currentState = selectedState,
            states = states,
            onStateSelected = onStateSelected
        )
        AnimatedContentContainer(selectedState = selectedState)
    }
}

@Composable
private fun StateSelectionHeader() {
    Text(
        text = "Select a State",
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(vertical = 16.dp)
    )
}

@Composable
private fun StateSelector(
    currentState: String,
    states: List<String>,
    onStateSelected: (String) -> Unit
) {
    states.forEach { state ->
        InteractiveButton(
            text = state,
            onClick = { onStateSelected(state) },
            backgroundColor = if (state == currentState) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.surface,
            contentColor = if (state == currentState) MaterialTheme.colorScheme.onPrimary
            else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(8.dp)
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun AnimatedContentContainer(selectedState: String) {
    Crossfade(
        targetState = selectedState,
        animationSpec = tween(1000),
        label = "CrossfadeExample"
    ) { currentState ->
        StatePreviewBox(state = currentState)
    }
}

@Composable
private fun StatePreviewBox(state: String) {
    Box(
        modifier = Modifier
            .size(200.dp)
            .background(getColorForState(state)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = state,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
    }
}

private fun getColorForState(state: String): Color = when (state) {
    "Home" -> Color.Red
    "Search" -> Color.Green
    "Profile" -> Color.Blue
    else -> Color.DarkGray
}