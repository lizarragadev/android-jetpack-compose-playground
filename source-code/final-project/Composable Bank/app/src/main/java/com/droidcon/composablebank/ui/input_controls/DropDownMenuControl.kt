package com.droidcon.composablebank.ui.input_controls

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.droidcon.composablebank.components.InteractiveButton
import com.droidcon.composablebank.components.InteractiveColorPicker
import com.droidcon.composablebank.components.InteractiveSlider
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.utils.CustomTopAppBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.droidcon.composablebank.components.InteractiveToast

@Composable
internal fun DropDownMenuControl(navController: NavController, name: String) {
    Scaffold(
        topBar = { CustomTopAppBar(title = name, navController = navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                BasicDropdown()
                SectionedDropdown()
                DynamicContentDropdown()
                ThemedDropdown()
            }
        }
    }
}

@Composable
private fun BasicDropdown() {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Select option") }

    Box(modifier = Modifier.fillMaxWidth()) {
        InteractiveButton(
            text = selectedText,
            onClick = { expanded = true }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(200.dp)
        ) {
            listOf("Option 1", "Option 2", "Option 3").forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedText = option
                        expanded = false
                        InteractiveToast(context, "Selected: $option")
                    }
                )
            }
        }
    }
}

@Composable
private fun SectionedDropdown() {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxWidth()) {
        InteractiveButton(
            text = "Advanced options",
            onClick = { expanded = true }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(240.dp)
        ) {
            DropdownMenuItem(
                text = { Text("Settings Category", style = MaterialTheme.typography.labelSmall) },
                onClick = {},
                enabled = false
            )
            listOf("Privacy", "Notifications", "Display").forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        selectedItem = item
                        expanded = false
                        InteractiveToast(context, "Selected: $item")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = when(item) {
                                "Privacy" -> Icons.Default.Security
                                "Notifications" -> Icons.Default.Notifications
                                else -> Icons.Default.ScreenRotation
                            },
                            contentDescription = null
                        )
                    }
                )
            }
            HorizontalDivider()
            DropdownMenuItem(
                text = { Text("Advanced settings") },
                onClick = {
                    InteractiveToast(context, "Selected: Advanced settings")
                },
                trailingIcon = {
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, null)
                }
            )
        }
    }
}

@Composable
private fun DynamicContentDropdown() {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var menuOffset by remember { mutableStateOf(DpOffset.Zero) }
    var maxItems by remember { mutableFloatStateOf(5f) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        InteractiveSlider(
            label = "Max visible items",
            value = maxItems,
            onValueChange = { maxItems = it },
            valueRange = 3f..10f
        )

        Box(modifier = Modifier.fillMaxWidth()) {
            InteractiveButton(
                text = "Dynamic Menu",
                onClick = { expanded = true }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                offset = menuOffset,
                scrollState = rememberScrollState(),
                properties = PopupProperties(
                    excludeFromSystemGesture = true,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                )
            ) {
                (1..maxItems.toInt()).forEach { index ->
                    DropdownMenuItem(
                        text = { Text("Item $index") },
                        onClick = {
                            expanded = false
                            InteractiveToast(context, "Selected: Item $index")
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ThemedDropdown() {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var menuColor by remember { mutableStateOf(Color.Gray) }
    var showLeadingIcon by remember { mutableStateOf(true) }
    var showTrailingIcon by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        InteractiveColorPicker(
            label = "Menu Color",
            selectedColor = menuColor,
            onColorSelected = { menuColor = it }
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InteractiveSwitch(
                label = "Leading Icon",
                checked = showLeadingIcon,
                onCheckedChange = { showLeadingIcon = it }
            )
            InteractiveSwitch(
                label = "Trailing Icon",
                checked = showTrailingIcon,
                onCheckedChange = { showTrailingIcon = it }
            )
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            InteractiveButton(
                text = "Themed Menu",
                backgroundColor = menuColor,
                onClick = { expanded = true }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(menuColor)
            ) {
                listOf("Theme 1", "Theme 2", "Theme 3").forEach { theme ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                theme,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        },
                        onClick = {
                            expanded = false
                            InteractiveToast(context, "Selected: $theme")
                        },
                        leadingIcon = if (showLeadingIcon) {
                            {
                                Icon(
                                    Icons.Default.Palette,
                                    contentDescription = "Theme Icon",
                                    tint = Color.White
                                )
                            }
                        } else null,
                        trailingIcon = if (showTrailingIcon) {
                            {
                                Icon(
                                    Icons.Default.Info,
                                    contentDescription = "Info",
                                    tint = Color.White.copy(alpha = 0.7f)
                                )
                            }
                        } else null,
                        modifier = Modifier.background(menuColor)
                    )
                }
            }
        }
    }
}