package com.droidcon.composablebank.ui.feedback_loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.navigation.NavController
import com.droidcon.composablebank.components.InteractiveButton
import com.droidcon.composablebank.components.InteractiveColorPicker
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.utils.CustomTopAppBar

@Composable
fun LinearProgressIndicator(navController: NavController, name: String) {
    var progress by remember { mutableFloatStateOf(0.0f) }
    var isIndeterminate by remember { mutableStateOf(false) }
    var trackColor by remember { mutableStateOf(Color.LightGray) }
    var progressColor by remember { mutableStateOf(Color.Cyan) }

    Scaffold(
        topBar = { CustomTopAppBar(title = name, navController = navController) },
        content = { paddingValues ->
            MainProgressContent(
                paddingValues = paddingValues,
                name = name,
                progress = progress,
                isIndeterminate = isIndeterminate,
                trackColor = trackColor,
                progressColor = progressColor,
                onIndeterminateChange = { checked ->
                    isIndeterminate = checked
                    if (!checked) {
                        progress = 0f
                        trackColor = Color.LightGray
                        progressColor = Color.Cyan
                    }
                },
                onTrackColorChange = { trackColor = it },
                onProgressColorChange = { progressColor = it },
                onProgressIncrement = { progress = (progress + 0.1f).coerceIn(0f, 1f) }
            )
        }
    )
}

@Composable
private fun MainProgressContent(
    paddingValues: PaddingValues,
    name: String,
    progress: Float,
    isIndeterminate: Boolean,
    trackColor: Color,
    progressColor: Color,
    onIndeterminateChange: (Boolean) -> Unit,
    onTrackColorChange: (Color) -> Unit,
    onProgressColorChange: (Color) -> Unit,
    onProgressIncrement: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProgressTitle(name = name)
            ControlsSection(
                isIndeterminate = isIndeterminate,
                trackColor = trackColor,
                progressColor = progressColor,
                onIndeterminateChange = onIndeterminateChange,
                onTrackColorChange = onTrackColorChange,
                onProgressColorChange = onProgressColorChange
            )
            ProgressControls(
                isIndeterminate = isIndeterminate,
                onProgressIncrement = onProgressIncrement
            )
            ProgressIndicatorSection(
                isIndeterminate = isIndeterminate,
                progress = progress,
                trackColor = trackColor,
                progressColor = progressColor
            )
        }
    }
}

@Composable
private fun ProgressTitle(name: String) {
    Text(
        text = "$name Example",
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@Composable
private fun ControlsSection(
    isIndeterminate: Boolean,
    trackColor: Color,
    progressColor: Color,
    onIndeterminateChange: (Boolean) -> Unit,
    onTrackColorChange: (Color) -> Unit,
    onProgressColorChange: (Color) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        InteractiveSwitch(
            label = "Indeterminate",
            checked = isIndeterminate,
            onCheckedChange = onIndeterminateChange
        )
        Spacer(modifier = Modifier.height(8.dp))
        ColorPickers(
            trackColor = trackColor,
            progressColor = progressColor,
            onTrackColorChange = onTrackColorChange,
            onProgressColorChange = onProgressColorChange
        )
    }
}

@Composable
private fun ColorPickers(
    trackColor: Color,
    progressColor: Color,
    onTrackColorChange: (Color) -> Unit,
    onProgressColorChange: (Color) -> Unit
) {
    Column {
        InteractiveColorPicker(
            label = "Track Color",
            selectedColor = trackColor,
            onColorSelected = onTrackColorChange
        )
        InteractiveColorPicker(
            label = "Progress Color",
            selectedColor = progressColor,
            onColorSelected = onProgressColorChange
        )
    }
}

@Composable
private fun ProgressControls(
    isIndeterminate: Boolean,
    onProgressIncrement: () -> Unit
) {
    if (!isIndeterminate) {
        Column(modifier = Modifier.fillMaxWidth()) {
            InteractiveButton(
                text = "Simulate Progress",
                onClick = onProgressIncrement,
                backgroundColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun ProgressIndicatorSection(
    isIndeterminate: Boolean,
    progress: Float,
    trackColor: Color,
    progressColor: Color
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        LinearProgressComponent(
            isIndeterminate = isIndeterminate,
            progress = progress,
            trackColor = trackColor,
            progressColor = progressColor
        )
        if (!isIndeterminate) {
            ProgressText(progress = progress)
        }
    }
}

@Composable
private fun LinearProgressComponent(
    isIndeterminate: Boolean,
    progress: Float,
    trackColor: Color,
    progressColor: Color
) {
    if (isIndeterminate) {
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth().height(8.dp),
            color = progressColor,
            trackColor = trackColor,
            gapSize = 4.dp,
            strokeCap = StrokeCap.Square
        )
    } else {
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth().height(8.dp),
            color = progressColor,
            trackColor = trackColor,
            progress = { progress },
            gapSize = 1.dp,
            strokeCap = StrokeCap.Round
        )
    }
}

@Composable
private fun ProgressText(progress: Float) {
    Text(
        text = "Progress: ${(progress * 100).toInt()}%",
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(top = 8.dp)
    )
}