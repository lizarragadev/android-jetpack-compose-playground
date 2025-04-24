package com.droidcon.composablebank.ui.basic_comp_layouts

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Transform
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.droidcon.composablebank.components.*
import com.droidcon.composablebank.utils.CustomTopAppBar

@Composable
internal fun SurfaceLayout(navController: NavController, name: String) {
    var surfaceColor by remember { mutableStateOf(Color.White) }
    var elevation by remember { mutableFloatStateOf(0f) }
    var borderWidth by remember { mutableFloatStateOf(0f) }
    var borderColor by remember { mutableStateOf(Color.Black) }
    var roundedCorners by remember { mutableStateOf(false) }
    var animatedSize by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { CustomTopAppBar(title = name, navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                tonalElevation = 2.dp,
                shape = MaterialTheme.shapes.medium
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    InteractiveColorPicker(
                        label = "Surface Color",
                        selectedColor = surfaceColor,
                        onColorSelected = { surfaceColor = it }
                    )

                    InteractiveSlider(
                        label = "Elevation: ${elevation.toInt()}dp",
                        value = elevation,
                        onValueChange = { elevation = it },
                        valueRange = 0f..20f
                    )

                    InteractiveSlider(
                        label = "Border Width: ${borderWidth.toInt()}dp",
                        value = borderWidth,
                        onValueChange = { borderWidth = it },
                        valueRange = 0f..8f
                    )

                    InteractiveColorPicker(
                        label = "Border Color",
                        selectedColor = borderColor,
                        onColorSelected = { borderColor = it }
                    )

                    InteractiveSwitch(
                        label = "Rounded Corners",
                        checked = roundedCorners,
                        onCheckedChange = { roundedCorners = it }
                    )

                    InteractiveSwitch(
                        label = "Animated Size",
                        checked = animatedSize,
                        onCheckedChange = { animatedSize = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            GenericAnimatedSurface(
                surfaceColor = surfaceColor,
                elevation = elevation,
                borderWidth = borderWidth,
                borderColor = borderColor,
                roundedCorners = roundedCorners,
                animatedSize = animatedSize
            )
        }
    }
}

@Composable
private fun GenericAnimatedSurface(
    surfaceColor: Color,
    elevation: Float,
    borderWidth: Float,
    borderColor: Color,
    roundedCorners: Boolean,
    animatedSize: Boolean
) {
    val size by animateDpAsState(
        targetValue = if (animatedSize) 250.dp else 150.dp,
        label = "sizeAnimation"
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(size),
        color = surfaceColor,
        tonalElevation = elevation.dp,
        shadowElevation = elevation.dp,
        border = BorderStroke(borderWidth.dp, borderColor),
        shape = if (roundedCorners) MaterialTheme.shapes.extraLarge else MaterialTheme.shapes.medium
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Default.Transform,
                    contentDescription = "Animated",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Animated Surface", style = MaterialTheme.typography.titleMedium)
                Text("Size: ${size.value.toInt()}dp", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}