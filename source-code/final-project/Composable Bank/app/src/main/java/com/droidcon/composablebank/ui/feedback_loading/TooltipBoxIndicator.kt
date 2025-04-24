package com.droidcon.composablebank.ui.feedback_loading

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.droidcon.composablebank.components.InteractiveColorPicker
import com.droidcon.composablebank.utils.CustomTopAppBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TooltipBoxIndicator(navController: NavController, name: String) {
    var tooltipBackgroundColor by remember { mutableStateOf(Color.Gray) }
    var tooltipContentColor by remember { mutableStateOf(Color.DarkGray) }

    Scaffold(
        topBar = { CustomTopAppBar(title = name, navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            ControlsSection(
                tooltipBackgroundColor = tooltipBackgroundColor,
                tooltipContentColor = tooltipContentColor,
                onBackgroundColorChange = { tooltipBackgroundColor = it },
                onContentColorChange = { tooltipContentColor = it }
            )

            TooltipExamplesSection(
                tooltipBackgroundColor = tooltipBackgroundColor,
                tooltipContentColor = tooltipContentColor
            )
        }
    }
}

@Composable
private fun ControlsSection(
    tooltipBackgroundColor: Color,
    tooltipContentColor: Color,
    onBackgroundColorChange: (Color) -> Unit,
    onContentColorChange: (Color) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        InteractiveColorPicker(
            label = "Background Color",
            selectedColor = tooltipBackgroundColor,
            onColorSelected = onBackgroundColorChange
        )

        InteractiveColorPicker(
            label = "Content Color",
            selectedColor = tooltipContentColor,
            onColorSelected = onContentColorChange
        )
    }
}

@Composable
private fun TooltipExamplesSection(
    tooltipBackgroundColor: Color,
    tooltipContentColor: Color
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Text(
            text = "Tooltip Variants",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        TooltipExampleWrapper(
            title = "Plain Tooltip",
        ) {
            PlainTooltipExample(
                backgroundColor = tooltipBackgroundColor,
                contentColor = tooltipContentColor
            )
        }

        TooltipExampleWrapper(
            title = "Rich Tooltip",
        ) {
            RichTooltipExample(
                backgroundColor = tooltipBackgroundColor,
                contentColor = tooltipContentColor
            )
        }

        TooltipExampleWrapper(
            title = "Custom Content",
        ) {
            CustomTooltipExample(
                backgroundColor = tooltipBackgroundColor,
                contentColor = tooltipContentColor
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlainTooltipExample(
    backgroundColor: Color,
    contentColor: Color
) {
    val tooltipState = rememberTooltipState()
    val coroutineScope = rememberCoroutineScope()

    val positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider()

    TooltipBox(
        positionProvider = positionProvider,
        state = tooltipState,
        tooltip = {
            PlainTooltip(
                containerColor = backgroundColor,
                contentColor = contentColor,
                modifier = Modifier
                    .widthIn(max = 200.dp)            ) {
                Text("Information tooltip", modifier = Modifier.wrapContentWidth())
            }
        }
    ) {
        Button(
            onClick = { coroutineScope.launch { tooltipState.show() } },
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Info, contentDescription = "Info")
                Spacer(Modifier.size(8.dp))
                Text("Hover for info")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RichTooltipExample(
    backgroundColor: Color,
    contentColor: Color
) {
    val tooltipState = rememberTooltipState()
    val coroutineScope = rememberCoroutineScope()

    val positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider()

    TooltipBox(
        positionProvider = positionProvider,
        state = tooltipState,
        tooltip = {
            RichTooltip(
                colors = TooltipDefaults.richTooltipColors(
                    containerColor = backgroundColor,
                    contentColor = contentColor
                ),
                title = { Text("Advanced Settings") },
                action = {
                    Button(
                        onClick = { coroutineScope.launch { tooltipState.dismiss() } }
                    ) {
                        Text("Got it!")
                    }
                }
            ) {
                Text("Configure your preferences here", modifier = Modifier.fillMaxWidth())
            }
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(
                onClick = { coroutineScope.launch { tooltipState.show() } }
            ) {
                Icon(Icons.Default.Settings, contentDescription = "Settings")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomTooltipExample(
    backgroundColor: Color,
    contentColor: Color
) {
    val tooltipState = rememberTooltipState()
    val coroutineScope = rememberCoroutineScope()

    val positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider()

    TooltipBox(
        positionProvider = positionProvider,
        state = tooltipState,
        tooltip = {
            RichTooltip(
                colors = TooltipDefaults.richTooltipColors(
                    containerColor = backgroundColor,
                    contentColor = contentColor
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.Info, contentDescription = null)
                    Spacer(Modifier.height(8.dp))
                    Text("Custom Content", style = MaterialTheme.typography.titleSmall)
                    Text("With multiple elements", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            TextButton(
                onClick = { coroutineScope.launch { tooltipState.show() } }
            ) {
                Text("Custom Content Trigger",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun TooltipExampleWrapper(
    title: String,
    content: @Composable () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        content()
    }
}