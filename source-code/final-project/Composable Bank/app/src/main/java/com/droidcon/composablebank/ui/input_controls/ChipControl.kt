package com.droidcon.composablebank.ui.input_controls

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.NewReleases
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.droidcon.composablebank.components.InteractiveColorPicker
import com.droidcon.composablebank.components.InteractiveRadioButtonGroup
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.components.InteractiveToast
import com.droidcon.composablebank.utils.CustomTopAppBar
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults

@Composable
internal fun ChipControl(navController: NavController, name: String) {
    val context = LocalContext.current
    var selectedColor by remember { mutableStateOf(Color.Blue) }
    var selectedChip by remember { mutableStateOf("") }
    var filterState by remember { mutableStateOf(false) }
    var isEnabled by remember { mutableStateOf(true) }
    var chipType by remember { mutableStateOf("Input") }

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
            ChipConfigurationSection(
                selectedColor = selectedColor,
                onColorChange = { selectedColor = it },
                isEnabled = isEnabled,
                onEnabledChange = { isEnabled = it },
                chipType = chipType,
                onTypeChange = { chipType = it }
            )

            when (chipType) {
                "Input" -> InputChipDemo(
                    selectedChip = selectedChip,
                    onSelectionChange = { selectedChip = it },
                    enabled = isEnabled,
                    selectedColor = selectedColor
                )
                "Filter" -> FilterChipDemo(
                    filterState = filterState,
                    onStateChange = { filterState = it },
                    enabled = isEnabled,
                    selectedColor = selectedColor
                )
                "Suggestion" -> SuggestionChipDemo(
                    enabled = isEnabled,
                    selectedColor = selectedColor
                )
            }

            InteractiveToast(context = context, text = "Selected: $selectedChip")
        }
    }
}

@Composable
private fun InputChipDemo(
    selectedChip: String,
    onSelectionChange: (String) -> Unit,
    enabled: Boolean,
    selectedColor: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        SectionHeader("Input Chips - Select Platform")

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf("Android", "iOS", "Web").forEach { platform ->
                InputChip(
                    selected = platform == selectedChip,
                    onClick = {
                        onSelectionChange(if (platform == selectedChip) "" else platform)
                    },
                    label = {
                        Text(
                            text = platform,
                            color = if (platform == selectedChip)
                                Color.White
                            else
                                MaterialTheme.colorScheme.onSurface
                        )
                    },
                    enabled = enabled,
                    colors = InputChipDefaults.inputChipColors(
                        containerColor = if (platform == selectedChip)
                            selectedColor
                        else
                            Color.LightGray,
                        disabledContainerColor = Color.Gray.copy(alpha = 0.3f),
                        selectedContainerColor = selectedColor,
                        disabledSelectedContainerColor = selectedColor.copy(alpha = 0.3f)
                    ),
                    leadingIcon = if (platform == selectedChip) {
                        {
                            Icon(
                                Icons.Filled.Check,
                                contentDescription = "Selected",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    } else null,
                    border = InputChipDefaults.inputChipBorder(
                        enabled = true,
                        selected = (platform == selectedChip),
                        borderColor = if (platform == selectedChip) selectedColor else Color.LightGray,
                        selectedBorderColor = selectedColor,
                        disabledBorderColor = Color.Gray.copy(alpha = 0.3f)
                    )
                )
            }
        }
    }
}

@Composable
private fun FilterChipDemo(
    filterState: Boolean,
    onStateChange: (Boolean) -> Unit,
    enabled: Boolean,
    selectedColor: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        SectionHeader("Filter Chips - Content Filters")
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            FilterChip(
                selected = filterState,
                onClick = { onStateChange(!filterState) },
                label = { Text("Active Content") },
                enabled = enabled,
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = selectedColor,
                    containerColor = Color.LightGray,
                    disabledContainerColor = Color.Gray.copy(alpha = 0.3f),
                    selectedLabelColor = Color.White,
                    disabledLabelColor = Color.Gray
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = enabled,
                    selected = filterState,
                    borderColor = Color.LightGray,
                    selectedBorderColor = selectedColor,
                    disabledBorderColor = Color.Gray.copy(alpha = 0.3f)
                ),
                leadingIcon = if (filterState) {
                    {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = "Active",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                } else null
            )

            var androidFilter by remember { mutableStateOf(false) }
            FilterChip(
                selected = androidFilter,
                onClick = { androidFilter = !androidFilter },
                label = { Text("Android Only") },
                enabled = true,
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = selectedColor,
                    containerColor = Color.LightGray,
                    disabledContainerColor = Color.Gray.copy(alpha = 0.3f),
                    selectedLabelColor = Color.White
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = androidFilter,
                    borderColor = Color.LightGray,
                    selectedBorderColor = selectedColor,
                    disabledBorderColor = Color.Gray.copy(alpha = 0.3f)
                ),
                leadingIcon = {
                    Icon(
                        Icons.Filled.Android,
                        contentDescription = "Android",
                        modifier = Modifier.size(20.dp)
                    )
                }
            )
        }
    }
}

@Composable
private fun SuggestionChipDemo(
    enabled: Boolean,
    selectedColor: Color
) {
    var selectedSuggestion by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        SectionHeader("Suggestion Chips - Common Actions")
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf("Save", "Share", "Print", "Favorite").forEach { action ->
                val isSelected = selectedSuggestion == action
                SuggestionChip(
                    onClick = {
                        selectedSuggestion = if (isSelected) "" else action
                    },
                    label = {
                        Text(
                            action,
                            color = if (isSelected) Color.White else Color.Black
                        )
                    },
                    enabled = enabled,
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = if (isSelected)
                            selectedColor
                        else
                            Color.LightGray.copy(alpha = 0.6f),
                        disabledContainerColor = Color.Gray.copy(alpha = 0.2f)
                    ),
                    border = SuggestionChipDefaults.suggestionChipBorder(
                        enabled = enabled,
                        borderColor = Color.LightGray,
                        disabledBorderColor = Color.Gray.copy(alpha = 0.3f),
                        borderWidth = 1.dp
                    ),
                    icon = {
                        Icon(
                            imageVector = when (action) {
                                "Save" -> Icons.Filled.Save
                                "Share" -> Icons.Filled.Share
                                "Favorite" -> Icons.Filled.Favorite
                                else -> Icons.Filled.Print
                            },
                            contentDescription = action,
                            tint = if (isSelected) Color.White else LocalContentColor.current,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            SuggestionChip(
                onClick = {  },
                label = {
                    Text(
                        "Settings",
                        color = selectedColor.copy(alpha = 0.9f)
                    )
                },
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = selectedColor.copy(alpha = 0.1f)
                ),
                icon = {
                    Icon(
                        Icons.Filled.Settings,
                        contentDescription = "Settings",
                        tint = selectedColor,
                        modifier = Modifier.size(20.dp))
                }
            )

            SuggestionChip(
                onClick = {  },
                label = { Text("Help") },
                enabled = false,
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.Gray.copy(alpha = 0.1f),
                    disabledLabelColor = Color.Gray.copy(alpha = 0.5f)
                ),
                border = SuggestionChipDefaults.suggestionChipBorder(
                    enabled = false,
                    borderColor = Color.Gray.copy(alpha = 0.2f)
                ),
                icon = {
                    Icon(
                        Icons.Filled.Help,
                        contentDescription = "Help",
                        tint = Color.Gray.copy(alpha = 0.5f),
                        modifier = Modifier.size(20.dp))
                }
            )

            var showNew by remember { mutableStateOf(true) }
            if (showNew) {
                SuggestionChip(
                    onClick = { showNew = false },
                    label = {
                        Text(
                            "New",
                            color = Color.White
                        )
                    },
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = Color(0xFF4CAF50)
                    ),
                    icon = {
                        Icon(
                            Icons.Filled.NewReleases,
                            contentDescription = "New",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun ChipConfigurationSection(
    selectedColor: Color,
    onColorChange: (Color) -> Unit,
    isEnabled: Boolean,
    onEnabledChange: (Boolean) -> Unit,
    chipType: String,
    onTypeChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        SectionHeader("Configuration")

        InteractiveColorPicker(
            label = "Active Color",
            selectedColor = selectedColor,
            onColorSelected = onColorChange
        )

        InteractiveSwitch(
            label = "Enable Chips",
            checked = isEnabled,
            onCheckedChange = onEnabledChange
        )

        InteractiveRadioButtonGroup(
            options = listOf("Input", "Filter", "Suggestion"),
            selectedOption = chipType,
            onOptionSelected = onTypeChange
        )
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    Spacer(modifier = Modifier.height(4.dp))
}