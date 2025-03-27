package com.droidcon.composablebank.ui.feedback_loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import com.droidcon.composablebank.components.InteractiveButton
import com.droidcon.composablebank.components.InteractiveColorPicker
import com.droidcon.composablebank.components.InteractiveSwitch
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import com.droidcon.composablebank.components.InteractiveSlider
import com.droidcon.composablebank.utils.CustomTopAppBar

@Composable
fun CircularProgressIndicator(navController: NavController, name: String) {
    var progress by remember { mutableFloatStateOf(0f) }
    var isIndeterminate by remember { mutableStateOf(true) }
    var strokeWidth by remember { mutableStateOf(4.dp) }
    var size by remember { mutableStateOf(60.dp) }
    var color by remember { mutableStateOf(Color(0xFF6200EE)) }

    Scaffold(
        topBar = { CustomTopAppBar(title = name, navController = navController) },
        content = { paddingValues ->
            MainProgressContent(
                paddingValues = paddingValues,
                name = name,
                progress = progress,
                isIndeterminate = isIndeterminate,
                strokeWidth = strokeWidth,
                size = size,
                color = color,
                onIndeterminateChange = {
                    isIndeterminate = it
                    if (!it) progress = 0f
                },
                onStrokeWidthChange = { strokeWidth = it.dp },
                onSizeChange = { size = it.dp },
                onColorChange = { color = it },
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
    strokeWidth: Dp,
    size: Dp,
    color: Color,
    onIndeterminateChange: (Boolean) -> Unit,
    onStrokeWidthChange: (Float) -> Unit,
    onSizeChange: (Float) -> Unit,
    onColorChange: (Color) -> Unit,
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProgressTitle(name)
            ControlsSection(
                isIndeterminate = isIndeterminate,
                strokeWidth = strokeWidth.value,
                size = size.value,
                color = color,
                onIndeterminateChange = onIndeterminateChange,
                onStrokeWidthChange = onStrokeWidthChange,
                onSizeChange = onSizeChange,
                onColorChange = onColorChange,
                showProgressButton = !isIndeterminate,
                onProgressIncrement = onProgressIncrement
            )
            ProgressDisplay(
                isIndeterminate = isIndeterminate,
                progress = progress,
                color = color,
                strokeWidth = strokeWidth,
                size = size
            )
        }
    }
}

@Composable
private fun ProgressTitle(name: String) {
    Text(
        text = "$name Examples",
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@Composable
private fun ControlsSection(
    isIndeterminate: Boolean,
    strokeWidth: Float,
    size: Float,
    color: Color,
    onIndeterminateChange: (Boolean) -> Unit,
    onStrokeWidthChange: (Float) -> Unit,
    onSizeChange: (Float) -> Unit,
    onColorChange: (Color) -> Unit,
    showProgressButton: Boolean,
    onProgressIncrement: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InteractiveSwitch(
            label = "Indeterminate",
            checked = isIndeterminate,
            onCheckedChange = onIndeterminateChange
        )
        Spacer(modifier = Modifier.height(8.dp))

        ProgressSlider(
            label = "Stroke Width (dp)",
            value = strokeWidth,
            onValueChange = onStrokeWidthChange,
            valueRange = 2f..10f
        )

        ProgressSlider(
            label = "Size (dp)",
            value = size,
            onValueChange = onSizeChange,
            valueRange = 24f..100f
        )

        InteractiveColorPicker(
            label = "Progress Color",
            selectedColor = color,
            onColorSelected = onColorChange
        )

        if (showProgressButton) {
            Spacer(modifier = Modifier.height(16.dp))
            InteractiveButton(
                text = "Simulate Progress",
                onClick = onProgressIncrement,
                backgroundColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ProgressSlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>
) {
    InteractiveSlider(
        label = label,
        value = value,
        onValueChange = onValueChange,
        valueRange = valueRange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    )
}

@Composable
private fun ProgressDisplay(
    isIndeterminate: Boolean,
    progress: Float,
    color: Color,
    strokeWidth: Dp,
    size: Dp
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .background(MaterialTheme.colorScheme.surface, shape = CircleShape)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            if (isIndeterminate) {
                CircularProgressIndicator(
                    modifier = Modifier.size(size),
                    color = color,
                    strokeWidth = strokeWidth
                )
            } else {
                CircularProgressIndicator(
                    modifier = Modifier.size(size),
                    color = color,
                    strokeWidth = strokeWidth,
                    progress = { progress }
                )
            }
        }

        if (!isIndeterminate) {
            Text(
                text = "Progress: ${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}