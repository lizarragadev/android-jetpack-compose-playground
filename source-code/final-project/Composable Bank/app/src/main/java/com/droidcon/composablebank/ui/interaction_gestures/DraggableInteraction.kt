package com.droidcon.composablebank.ui.interaction_gestures

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavController
import com.droidcon.composablebank.components.InteractiveColorPicker
import com.droidcon.composablebank.components.InteractiveSlider
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.utils.CustomTopAppBar
import kotlin.math.roundToInt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.Scaffold
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity

@Composable
internal fun DraggableInteraction(navController: NavController, name: String) {
    val scrollState = rememberScrollState()
    var horizontalOffset by remember { mutableFloatStateOf(0f) }
    var verticalOffset by remember { mutableFloatStateOf(0f) }
    var freeOffsetX by remember { mutableFloatStateOf(0f) }
    var freeOffsetY by remember { mutableFloatStateOf(0f) }
    var dragEnabled by remember { mutableStateOf(true) }
    var sensitivity by remember { mutableFloatStateOf(1f) }
    var boxColor by remember { mutableStateOf(Color.Blue) }
    var maxDragLimit by remember { mutableFloatStateOf(200f) }

    Scaffold(
        topBar = { CustomTopAppBar(title = name, navController = navController) },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                InteractiveSwitch(
                    label = "Enable Drag",
                    checked = dragEnabled,
                    onCheckedChange = { dragEnabled = it }
                )

                Spacer(modifier = Modifier.height(8.dp))

                InteractiveSlider(
                    label = "Sensitivity",
                    value = sensitivity,
                    onValueChange = { sensitivity = it },
                    valueRange = 0.5f..3f
                )

                Spacer(modifier = Modifier.height(8.dp))

                InteractiveSlider(
                    label = "Maximum limit",
                    value = maxDragLimit,
                    onValueChange = { maxDragLimit = it },
                    valueRange = 100f..300f
                )

                Spacer(modifier = Modifier.height(8.dp))

                InteractiveColorPicker(
                    label = "Element color",
                    selectedColor = boxColor,
                    onColorSelected = { boxColor = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDragExample(
                    offset = horizontalOffset,
                    onOffsetChange = { horizontalOffset = it },
                    enabled = dragEnabled,
                    sensitivity = sensitivity,
                    boxColor = boxColor,
                    maxLimit = maxDragLimit
                )

                Spacer(modifier = Modifier.height(16.dp))

                VerticalDragExample(
                    offset = verticalOffset,
                    onOffsetChange = { verticalOffset = it },
                    enabled = dragEnabled,
                    sensitivity = sensitivity,
                    boxColor = boxColor,
                    maxLimit = maxDragLimit
                )

                Spacer(modifier = Modifier.height(16.dp))

                FreeDragExample(
                    offsetX = freeOffsetX,
                    offsetY = freeOffsetY,
                    onOffsetChange = { x, y ->
                        freeOffsetX = x
                        freeOffsetY = y
                    },
                    enabled = dragEnabled,
                    boxColor = boxColor,
                    maxLimit = maxDragLimit,
                    sensitivity = sensitivity
                )
            }
        }
    )
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
private fun HorizontalDragExample(
    offset: Float,
    onOffsetChange: (Float) -> Unit,
    enabled: Boolean,
    sensitivity: Float,
    boxColor: Color,
    maxLimit: Float
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Horizontal Drag", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(8.dp)
            ) {
                val density = LocalDensity.current
                val elementWidthPx = with(density) { 60.dp.toPx() }
                val containerWidthPx = with(density) { maxWidth.toPx() }
                val maxLimitPx = with(density) { maxLimit.dp.toPx() }
                val maxX = (containerWidthPx - elementWidthPx).coerceAtMost(maxLimitPx)
                var localOffset by remember { mutableFloatStateOf(offset.coerceIn(0f, maxX)) }

                Box(
                    modifier = Modifier
                        .offset {
                            IntOffset(
                                localOffset.roundToInt(),
                                0
                            )
                        }
                        .size(60.dp)
                        .background(boxColor, RoundedCornerShape(8.dp))
                        .pointerInput(enabled, sensitivity, maxX) {
                            if (!enabled) return@pointerInput
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                localOffset = (localOffset + dragAmount.x * sensitivity)
                                    .coerceIn(0f, maxX)
                                onOffsetChange(localOffset)
                            }
                        }
                )
            }
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
private fun VerticalDragExample(
    offset: Float,
    onOffsetChange: (Float) -> Unit,
    enabled: Boolean,
    sensitivity: Float,
    boxColor: Color,
    maxLimit: Float
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Vertical Drag", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(8.dp)
            ) {
                val density = LocalDensity.current

                val elementHeightPx = with(density) { 60.dp.toPx() }
                val containerHeightPx = with(density) { maxHeight.toPx() }
                val maxLimitPx = with(density) { maxLimit.dp.toPx() }

                val maxY = (containerHeightPx - elementHeightPx).coerceAtMost(maxLimitPx)

                var localOffset by remember { mutableFloatStateOf(offset.coerceIn(0f, maxY)) }

                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset { IntOffset(0, localOffset.roundToInt()) }
                        .size(60.dp)
                        .background(boxColor, RoundedCornerShape(8.dp))
                        .pointerInput(enabled, sensitivity, maxY) {
                            if (!enabled) return@pointerInput
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                localOffset = (localOffset + dragAmount.y * sensitivity)
                                    .coerceIn(0f, maxY)
                                onOffsetChange(localOffset)
                            }
                        }
                )
            }
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
private fun FreeDragExample(
    offsetX: Float,
    offsetY: Float,
    onOffsetChange: (Float, Float) -> Unit,
    enabled: Boolean,
    boxColor: Color,
    maxLimit: Float,
    sensitivity: Float
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Combined 2D Drag", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(8.dp)
            ) {
                val density = LocalDensity.current

                val elementSizePx = with(density) { 60.dp.toPx() }
                val containerWidthPx = with(density) { maxWidth.toPx() }
                val containerHeightPx = with(density) { maxHeight.toPx() }

                val maxLimitXPx = with(density) { maxLimit.dp.toPx() }
                val maxLimitYPx = with(density) { maxLimit.dp.toPx() }

                val maxX = (containerWidthPx - elementSizePx).coerceAtMost(maxLimitXPx)
                val maxY = (containerHeightPx - elementSizePx).coerceAtMost(maxLimitYPx)

                var localOffsetX by remember {
                    mutableFloatStateOf(offsetX.coerceIn(0f, maxX))
                }
                var localOffsetY by remember {
                    mutableFloatStateOf(offsetY.coerceIn(0f, maxY))
                }

                Box(
                    modifier = Modifier
                        .offset {
                            IntOffset(
                                localOffsetX.roundToInt(),
                                localOffsetY.roundToInt()
                            )
                        }
                        .size(60.dp)
                        .background(boxColor, RoundedCornerShape(8.dp))
                        .pointerInput(enabled, sensitivity, maxX, maxY) {
                            if (!enabled) return@pointerInput
                            detectDragGestures { _, dragAmount ->
                                val newX = (localOffsetX + dragAmount.x * sensitivity)
                                    .coerceIn(0f, maxX)
                                val newY = (localOffsetY + dragAmount.y * sensitivity)
                                    .coerceIn(0f, maxY)
                                localOffsetX = newX
                                localOffsetY = newY
                                onOffsetChange(newX, newY)
                            }
                        }
                )
            }
        }
    }
}