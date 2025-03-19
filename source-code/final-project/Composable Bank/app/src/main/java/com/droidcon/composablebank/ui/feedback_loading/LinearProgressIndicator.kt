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
                        .background(MaterialTheme.colorScheme.background)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$name Example",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    InteractiveSwitch(
                        label = "Indeterminate",
                        checked = isIndeterminate,
                        onCheckedChange = { isChecked ->
                            isIndeterminate = isChecked
                            if (!isChecked) {
                                progress = 0f
                                trackColor = Color.LightGray
                                progressColor = Color.Cyan
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    InteractiveColorPicker(
                        label = "Track Color",
                        selectedColor = trackColor,
                        onColorSelected = { trackColor = it }
                    )
                    InteractiveColorPicker(
                        label = "Progress Color",
                        selectedColor = progressColor,
                        onColorSelected = { progressColor = it }
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
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Spacer(modifier = Modifier.height(30.dp))

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