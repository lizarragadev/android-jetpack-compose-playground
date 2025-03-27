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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.droidcon.composablebank.components.InteractiveRadioButtonGroup
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.R

@Composable
fun Clip(navController: NavController, name: String) {
    var clipShape by remember { mutableStateOf("Circle") }
    var cornerRadius by remember { mutableStateOf(16.dp) }
    var showOriginal by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { CustomTopAppBar(title = name, navController = navController) },
        content = { paddingValues ->
            MainContent(
                paddingValues = paddingValues,
                clipShape = clipShape,
                cornerRadius = cornerRadius,
                showOriginal = showOriginal,
                onShapeChange = { clipShape = it },
                onRadiusChange = { cornerRadius = it },
                onToggleOriginal = { showOriginal = it },
                onReset = {
                    clipShape = "Circle"
                    cornerRadius = 16.dp
                    showOriginal = false
                }
            )
        }
    )
}

@Composable
private fun MainContent(
    paddingValues: PaddingValues,
    clipShape: String,
    cornerRadius: Dp,
    showOriginal: Boolean,
    onShapeChange: (String) -> Unit,
    onRadiusChange: (Dp) -> Unit,
    onToggleOriginal: (Boolean) -> Unit,
    onReset: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(32.dp))

        ShapeSelector(
            currentShape = clipShape,
            onShapeSelected = onShapeChange
        )

        CornerRadiusControl(
            currentShape = clipShape,
            currentRadius = cornerRadius,
            onRadiusChanged = onRadiusChange
        )

        OriginalImageToggle(
            showOriginal = showOriginal,
            onToggle = onToggleOriginal
        )

        ImageContainer(
            clipShape = clipShape,
            cornerRadius = cornerRadius,
            showOriginal = showOriginal
        )

        ResetButton(onReset = onReset)
    }
}

@Composable
private fun ShapeSelector(currentShape: String, onShapeSelected: (String) -> Unit) {
    InteractiveRadioButtonGroup(
        options = listOf("Circle", "Rounded Rectangle", "Custom Shape"),
        selectedOption = currentShape.replaceFirstChar { it.titlecase() },
        onOptionSelected = { onShapeSelected(it) }
    )
    Spacer(Modifier.height(16.dp))
}

@Composable
private fun CornerRadiusControl(
    currentShape: String,
    currentRadius: Dp,
    onRadiusChanged: (Dp) -> Unit
) {
    if (currentShape == "Rounded Rectangle") {
        Text(
            text = "Corner Radius: ${currentRadius.value.toInt()} dp",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Slider(
            value = currentRadius.value,
            onValueChange = { onRadiusChanged(it.dp) },
            valueRange = 0f..50f,
            steps = 50
        )
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun OriginalImageToggle(showOriginal: Boolean, onToggle: (Boolean) -> Unit) {
    InteractiveSwitch(
        label = "Show Original Image",
        checked = showOriginal,
        onCheckedChange = onToggle
    )
    Spacer(Modifier.height(16.dp))
}

@Composable
private fun ImageContainer(clipShape: String, cornerRadius: Dp, showOriginal: Boolean) {
    Box(
        modifier = Modifier
            .size(200.dp)
            .background(Color.DarkGray)
            .padding(16.dp)
            .conditionalClip(
                condition = !showOriginal,
                shapeType = clipShape,
                radius = cornerRadius
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.droidcon),
            contentDescription = "Sample Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun ResetButton(onReset: () -> Unit) {
    Button(
        onClick = onReset,
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Reset Clip")
    }
}

private fun Modifier.conditionalClip(condition: Boolean, shapeType: String, radius: Dp): Modifier {
    return if (condition) {
        when (shapeType) {
            "Circle" -> clip(CircleShape)
            "Rounded Rectangle" -> clip(RoundedCornerShape(radius))
            else -> clip(TriangleShape)
        }
    } else {
        this
    }
}

private val TriangleShape = GenericShape { size, _ ->
    moveTo(size.width / 2f, 0f)
    lineTo(size.width, size.height)
    lineTo(0f, size.height)
    close()
}