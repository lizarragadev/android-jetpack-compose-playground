package com.droidcon.composablebank.ui.animations_effects

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.droidcon.composablebank.components.InteractiveColorPicker
import com.droidcon.composablebank.components.InteractiveRadioButtonGroup
import com.droidcon.composablebank.components.InteractiveSlider
import com.droidcon.composablebank.utils.CustomTopAppBar
import kotlin.math.roundToInt

@Composable
internal fun GraphicsLayerEffect(navController: NavController, name: String) {
    var rotation by remember { mutableFloatStateOf(0f) }
    var scale by remember { mutableFloatStateOf(1f) }
    var alpha by remember { mutableFloatStateOf(1f) }
    var shadowElevation by remember { mutableFloatStateOf(0f) }
    var translationX by remember { mutableFloatStateOf(0f) }
    var translationY by remember { mutableFloatStateOf(0f) }
    var transformOrigin by remember { mutableStateOf(TransformOrigin.Center) }
    var selectedColor by remember { mutableStateOf(Color.Blue) }

    Scaffold(
        topBar = { CustomTopAppBar(title = name, navController = navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                GraphicsLayerDemo(
                    rotation = rotation,
                    scale = scale,
                    alpha = alpha,
                    shadowElevation = shadowElevation,
                    translationX = translationX,
                    translationY = translationY,
                    transformOrigin = transformOrigin,
                    backgroundColor = selectedColor
                )
            }

            Box(
                modifier = Modifier
                    .weight(0.6f)
                    .verticalScroll(rememberScrollState())
            ) {
                Column {
                    TransformationControlsSection(
                        rotation = rotation,
                        scale = scale,
                        translationX = translationX,
                        translationY = translationY,
                        onRotationChange = { rotation = it },
                        onScaleChange = { scale = it },
                        onTranslationXChange = { translationX = it },
                        onTranslationYChange = { translationY = it }
                    )

                    AdvancedEffectsSection(
                        alpha = alpha,
                        shadowElevation = shadowElevation,
                        onAlphaChange = { alpha = it },
                        onShadowChange = { shadowElevation = it }
                    )

                    TransformOriginSection(
                        selectedOrigin = transformOrigin,
                        onOriginSelected = { transformOrigin = it }
                    )

                    ColorCompositionSection(
                        selectedColor = selectedColor,
                        onColorSelected = { selectedColor = it }
                    )
                }
            }
        }
    }
}

@Composable
private fun TransformationControlsSection(
    rotation: Float,
    scale: Float,
    translationX: Float,
    translationY: Float,
    onRotationChange: (Float) -> Unit,
    onScaleChange: (Float) -> Unit,
    onTranslationXChange: (Float) -> Unit,
    onTranslationYChange: (Float) -> Unit
) {
    Surface(
        modifier = Modifier.padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Basic Transformations", style = MaterialTheme.typography.headlineSmall)
            InteractiveSlider(
                label = "Rotation: ${rotation.roundToInt()}°",
                value = rotation,
                onValueChange = onRotationChange,
                valueRange = 0f..360f
            )
            InteractiveSlider(
                label = "Scale: ${scale.roundToDecimal(1)}x",
                value = scale,
                onValueChange = onScaleChange,
                valueRange = 0.1f..3f
            )
            InteractiveSlider(
                label = "Translation X: ${translationX.roundToInt()}px",
                value = translationX,
                onValueChange = onTranslationXChange,
                valueRange = -100f..100f
            )
            InteractiveSlider(
                label = "Translation Y: ${translationY.roundToInt()}px",
                value = translationY,
                onValueChange = onTranslationYChange,
                valueRange = -100f..100f
            )
        }
    }
}

@Composable
private fun AdvancedEffectsSection(
    alpha: Float,
    shadowElevation: Float,
    onAlphaChange: (Float) -> Unit,
    onShadowChange: (Float) -> Unit,
) {
    Surface(
        modifier = Modifier.padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Advanced Effects", style = MaterialTheme.typography.headlineSmall)
            InteractiveSlider(
                label = "Alpha: ${alpha.roundToDecimal(2)}",
                value = alpha,
                onValueChange = onAlphaChange,
                valueRange = 0f..1f
            )
            InteractiveSlider(
                label = "Shadow Elevation: ${shadowElevation.roundToInt()}px",
                value = shadowElevation,
                onValueChange = onShadowChange,
                valueRange = 0f..32f
            )
        }
    }
}

@Composable
private fun TransformOriginSection(
    selectedOrigin: TransformOrigin,
    onOriginSelected: (TransformOrigin) -> Unit
) {
    val transformOrigins = listOf(
        "Center" to TransformOrigin.Center,
        "TopStart" to TransformOrigin(0f, 0f),
        "BottomEnd" to TransformOrigin(1f, 1f)
    )

    Surface(
        modifier = Modifier.padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Transform Origin", style = MaterialTheme.typography.headlineSmall)
            InteractiveRadioButtonGroup(
                options = transformOrigins.map { it.first },
                selectedOption = transformOrigins.first { it.second == selectedOrigin }.first,
                onOptionSelected = { selectedOption ->
                    onOriginSelected(transformOrigins.first { it.first == selectedOption }.second)
                }
            )
        }
    }
}

@Composable
private fun ColorCompositionSection(
    selectedColor: Color,
    onColorSelected: (Color) -> Unit
) {
    Surface(
        modifier = Modifier.padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Color Composition", style = MaterialTheme.typography.headlineSmall)
            InteractiveColorPicker(
                label = "Select Background Color",
                selectedColor = selectedColor,
                onColorSelected = onColorSelected
            )
        }
    }
}

@Composable
private fun GraphicsLayerDemo(
    rotation: Float,
    scale: Float,
    alpha: Float,
    shadowElevation: Float,
    translationX: Float,
    translationY: Float,
    transformOrigin: TransformOrigin,
    backgroundColor: Color
) {
    val animatedRotation by animateFloatAsState(
        targetValue = rotation,
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )
    val elevation = shadowElevation.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
            .graphicsLayer {
                this.rotationZ = animatedRotation
                this.scaleX = scale
                this.scaleY = scale
                this.alpha = alpha
                this.translationX = translationX
                this.translationY = translationY
                this.transformOrigin = transformOrigin
            }
            .shadow(
                elevation = elevation,
                shape = MaterialTheme.shapes.large,
                clip = true
            )
            .background(backgroundColor, MaterialTheme.shapes.large)
            .aspectRatio(1f)
    ) {
        Text(
            text = "Droidcon!",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

private fun Float.roundToDecimal(decimals: Int): Float {
    var multiplier = 1f
    repeat(decimals) { multiplier *= 10f }
    return (this * multiplier).roundToInt() / multiplier
}