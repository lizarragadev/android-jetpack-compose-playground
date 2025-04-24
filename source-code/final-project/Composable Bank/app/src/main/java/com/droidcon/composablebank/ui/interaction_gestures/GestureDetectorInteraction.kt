package com.droidcon.composablebank.ui.interaction_gestures

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.droidcon.composablebank.components.InteractiveButton
import com.droidcon.composablebank.components.InteractiveColorPicker
import com.droidcon.composablebank.components.InteractiveSlider
import com.droidcon.composablebank.components.InteractiveToast
import com.droidcon.composablebank.utils.CustomTopAppBar
import kotlin.math.roundToInt
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale

@Composable
internal fun GestureDetectorInteraction(navController: NavController, name: String) {
    val context = LocalContext.current
    var dragSensitivity by remember { mutableFloatStateOf(1f) }
    var selectedColor by remember { mutableStateOf(Color.Blue) }
    var currentGesture by remember { mutableStateOf("None") }

    Scaffold(
        topBar = { CustomTopAppBar(title = name, navController = navController) },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            GestureConfigurationSection(
                selectedColor = selectedColor,
                onColorChange = { selectedColor = it }
            )

            TapGestureDemoSection(
                selectedColor = selectedColor,
                onGestureDetected = { currentGesture = it }
            )

            MultiTouchDemoSection(
                selectedColor = selectedColor,
                onGestureDetected = { currentGesture = it }
            )

            DragGestureDemoSection(
                dragSensitivity = dragSensitivity,
                onDragSensitivityChange = { dragSensitivity = it },
                onGestureDetected = { currentGesture = it },
                selectedColor = selectedColor
            )

            InteractiveToast(
                context = context,
                text = "Last gesture: $currentGesture",
            )
        }
    }
}

@Composable
private fun GestureConfigurationSection(
    selectedColor: Color,
    onColorChange: (Color) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        SectionHeader("Gestures")

        InteractiveColorPicker(
            label = "Panel color",
            selectedColor = selectedColor,
            onColorSelected = onColorChange
        )
    }
}

@Composable
private fun TapGestureDemoSection(
    selectedColor: Color,
    onGestureDetected: (String) -> Unit
) {
    var totalTaps by remember { mutableIntStateOf(0) }
    var consecutiveTaps by remember { mutableIntStateOf(0) }
    var lastTapTime by remember { mutableLongStateOf(0L) }
    val tapTimeout = 300L

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        SectionHeader("Tap Demonstration")

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(selectedColor)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            onGestureDetected("Double tap")
                            consecutiveTaps = 2
                        },
                        onTap = { offset ->
                            totalTaps++
                            val currentTime = System.currentTimeMillis()
                            if (currentTime - lastTapTime < tapTimeout) {
                                consecutiveTaps++
                            } else {
                                consecutiveTaps = 1
                            }
                            lastTapTime = currentTime
                            onGestureDetected("Single tap")
                        }
                    )
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Total taps: $totalTaps",
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Consecutive taps: $consecutiveTaps",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
private fun MultiTouchDemoSection(
    selectedColor: Color,
    onGestureDetected: (String) -> Unit
) {
    var pointerCount by remember { mutableIntStateOf(0) }
    var scale by remember { mutableFloatStateOf(1f) }
    var rotation by remember { mutableFloatStateOf(0f) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        SectionHeader("Multi-Touch Demonstration")

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(selectedColor)
                .pointerInput(Unit) {
                    detectTransformGestures(
                        onGesture = { centroid, pan, gestureZoom, gestureRotation ->
                            scale *= gestureZoom
                            rotation += gestureRotation
                            onGestureDetected("Pinch scale: ${"%.1f".format(scale)}x")
                        }
                    )
                }
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            pointerCount = event.changes.size
                            onGestureDetected("$pointerCount fingers touching")
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .scale(scale)
                    .rotate(rotation)
                    .background(Color.Magenta, RoundedCornerShape(8.dp))
            ) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$pointerCount fingers",
                        color = Color.White
                    )
                    Text(
                        text = "Scale: ${"%.1f".format(scale)}x",
                        color = Color.White
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            InteractiveButton(
                text = "Reset Transform",
                onClick = {
                    scale = 1f
                    rotation = 0f
                    onGestureDetected("Transform reset")
                }
            )
        }
    }
}

@Composable
private fun DragGestureDemoSection(
    dragSensitivity: Float,
    onDragSensitivityChange: (Float) -> Unit,
    onGestureDetected: (String) -> Unit,
    selectedColor: Color
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        SectionHeader("Drag Demonstration")

        InteractiveSlider(
            label = "Drag sensitivity",
            value = dragSensitivity,
            onValueChange = onDragSensitivityChange,
            valueRange = 0.5f..3f,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                    .background(selectedColor, RoundedCornerShape(8.dp))
                    .pointerInput(dragSensitivity) {
                        detectDragGestures(
                            onDragStart = {
                                onGestureDetected("Drag started")
                            },
                            onDragEnd = {
                                onGestureDetected("Drag ended")
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                offsetX += dragAmount.x * dragSensitivity
                                offsetY += dragAmount.y * dragSensitivity
                                onGestureDetected("Drag: (${"%.1f".format(dragAmount.x)}, ${"%.1f".format(dragAmount.y)})")
                            }
                        )
                    }
            ) {
                Text(
                    text = "Drag me",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            InteractiveButton(
                text = "Reset position",
                onClick = {
                    offsetX = 0f
                    offsetY = 0f
                    onGestureDetected("Position reset")
                },
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    Spacer(modifier = Modifier.height(4.dp))
}
