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
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import com.droidcon.composablebank.components.InteractiveColorPicker
import com.droidcon.composablebank.R

@Composable
fun Shadow(navController: NavController, name: String) {
    var elevation by remember { mutableStateOf(8.dp) }
    var shadowColor by remember { mutableStateOf(Color.Black) }
    var offsetX by remember { mutableStateOf(0.dp) }
    var offsetY by remember { mutableStateOf(0.dp) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { CustomTopAppBar(title = name, navController = navController) },
        content = { paddingValues ->
            MainShadowContent(
                paddingValues = paddingValues,
                elevation = elevation,
                shadowColor = shadowColor,
                offsetX = offsetX,
                offsetY = offsetY,
                onElevationChange = { elevation = it },
                onColorChange = { shadowColor = it },
                onOffsetXChange = { offsetX = it },
                onOffsetYChange = { offsetY = it },
                onReset = {
                    elevation = 8.dp
                    shadowColor = Color.Black
                    offsetX = 0.dp
                    offsetY = 0.dp
                }
            )
        }
    )
}

@Composable
private fun MainShadowContent(
    paddingValues: PaddingValues,
    elevation: Dp,
    shadowColor: Color,
    offsetX: Dp,
    offsetY: Dp,
    onElevationChange: (Dp) -> Unit,
    onColorChange: (Color) -> Unit,
    onOffsetXChange: (Dp) -> Unit,
    onOffsetYChange: (Dp) -> Unit,
    onReset: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(32.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ColorSelectionSection(shadowColor, onColorChange)
        ShadowControlsSection(
            elevation = elevation,
            offsetX = offsetX,
            offsetY = offsetY,
            onElevationChange = onElevationChange,
            onOffsetXChange = onOffsetXChange,
            onOffsetYChange = onOffsetYChange
        )
        ShadowPreviewBox(
            elevation = elevation,
            shadowColor = shadowColor,
            offsetX = offsetX,
            offsetY = offsetY
        )
        ResetButton(onReset)
    }
}

@Composable
private fun ColorSelectionSection(selectedColor: Color, onColorSelected: (Color) -> Unit) {
    InteractiveColorPicker(
        label = "Shadow Color",
        selectedColor = selectedColor,
        onColorSelected = onColorSelected
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun ShadowControlsSection(
    elevation: Dp,
    offsetX: Dp,
    offsetY: Dp,
    onElevationChange: (Dp) -> Unit,
    onOffsetXChange: (Dp) -> Unit,
    onOffsetYChange: (Dp) -> Unit
) {
    CustomSlider(
        label = "Elevation",
        value = elevation.value,
        valueRange = 0f..30f,
        onValueChange = { onElevationChange(it.dp) },
        valueSuffix = "dp"
    )

    CustomSlider(
        label = "Offset X",
        value = offsetX.value,
        valueRange = -20f..20f,
        onValueChange = { onOffsetXChange(it.dp) },
        valueSuffix = "dp"
    )

    CustomSlider(
        label = "Offset Y",
        value = offsetY.value,
        valueRange = -20f..20f,
        onValueChange = { onOffsetYChange(it.dp) },
        valueSuffix = "dp"
    )
}

@Composable
private fun CustomSlider(
    label: String,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> Unit,
    valueSuffix: String = ""
) {
    Text(
        text = "$label: ${value.toInt()} $valueSuffix",
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    Slider(
        value = value,
        onValueChange = onValueChange,
        valueRange = valueRange,
        steps = (valueRange.endInclusive - valueRange.start).toInt()
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun ShadowPreviewBox(
    elevation: Dp,
    shadowColor: Color,
    offsetX: Dp,
    offsetY: Dp
) {
    Box(
        modifier = Modifier
            .size(200.dp)
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(16.dp),
                clip = true,
                ambientColor = shadowColor.copy(alpha = 0.5f),
                spotColor = shadowColor
            )
            .offset { IntOffset(offsetX.roundToPx(), offsetY.roundToPx()) }
    ) {
        Image(
            painter = painterResource(id = R.drawable.droidcon),
            contentDescription = "Sample Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun ResetButton(onReset: () -> Unit) {
    Button(onClick = onReset) {
        Text("Reset Shadow")
    }
}