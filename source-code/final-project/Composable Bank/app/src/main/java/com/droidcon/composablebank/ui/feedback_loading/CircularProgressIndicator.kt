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
import com.droidcon.composablebank.components.InteractiveSlider
import com.droidcon.composablebank.components.InteractiveSwitch
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.droidcon.composablebank.utils.CustomTopAppBar

@Composable
fun CircularProgressIndicator(navController: NavController, name: String) {
    var progress by remember { mutableFloatStateOf(0f) }
    var isIndeterminate by remember { mutableStateOf(true) }
    var strokeWidth by remember { mutableStateOf(4.dp) }
    var size by remember { mutableStateOf(60.dp) }
    var color by remember { mutableStateOf(Color(0xFF6200EE) ) }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = name,
                navController = navController
            )
        },
        content = { paddingValues ->
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
                    Text(
                        text = "$name Examples",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    InteractiveSwitch(
                        label = "Indeterminate",
                        checked = isIndeterminate,
                        onCheckedChange = {
                            isIndeterminate = it
                            if (!it) {
                                progress = 0f
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    InteractiveSlider(
                        label = "Stroke Width (dp)",
                        value = strokeWidth.value,
                        onValueChange = { strokeWidth = it.dp },
                        valueRange = 2f..10f
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    InteractiveSlider(
                        label = "Size (dp)",
                        value = size.value,
                        onValueChange = { size = it.dp },
                        valueRange = 24f..100f
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    InteractiveColorPicker(
                        label = "Progress Color",
                        selectedColor = color,
                        onColorSelected = { color = it }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    if (!isIndeterminate) {
                        InteractiveButton(
                            text = "Simulate Progress",
                            onClick = {
                                progress = (progress + 0.1f).coerceIn(0f, 1f)
                            },
                            backgroundColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }

                    Spacer(modifier = Modifier.height(30.dp))
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
        }
    )
}