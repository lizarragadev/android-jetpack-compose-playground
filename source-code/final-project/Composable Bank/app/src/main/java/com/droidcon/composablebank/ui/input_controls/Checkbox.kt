package com.droidcon.composablebank.ui.input_controls

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.droidcon.composablebank.utils.CustomTopAppBar
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.graphics.Color
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.components.InteractiveRadioButtonGroup
import com.droidcon.composablebank.components.InteractiveColorPicker
import androidx.compose.runtime.mutableStateListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Checkboxes(navController: NavController, name: String) {
    var checkboxType by remember { mutableStateOf("Single") }
    var checkboxColor by remember { mutableStateOf(Color.Blue) }
    var isEnabled by remember { mutableStateOf(true) }
    var alignment by remember { mutableStateOf("Vertical") }
    var showSnackbar by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val items = listOf("Option 1", "Option 2", "Option 3", "Option 4")
    val selectedItems = remember { mutableStateListOf(false, false, false, false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { CustomTopAppBar(title = name, navController = navController) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CheckboxSettingsPanel(
                    checkboxType = checkboxType,
                    isEnabled = isEnabled,
                    checkboxColor = checkboxColor,
                    alignment = alignment,
                    onTypeChange = { checkboxType = it },
                    onColorChange = { checkboxColor = it },
                    onEnabledChange = { isEnabled = it },
                    onAlignmentChange = { alignment = it }
                )

                Spacer(modifier = Modifier.height(24.dp))

                when (checkboxType) {
                    "Single" -> SingleCheckboxExample(
                        color = checkboxColor,
                        enabled = isEnabled
                    )
                    "Group" -> GroupCheckboxExample(
                        items = items,
                        selectedItems = selectedItems,
                        alignment = alignment,
                        color = checkboxColor,
                        enabled = isEnabled
                    )
                    "Select All" -> SelectAllCheckboxExample(
                        items = items,
                        selectedItems = selectedItems,
                        alignment = alignment,
                        color = checkboxColor,
                        enabled = isEnabled
                    )
                }
            }
        }
    )

    if (showSnackbar) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar("$checkboxType checkbox updated")
            showSnackbar = false
        }
    }
}

@Composable
private fun CheckboxSettingsPanel(
    checkboxType: String,
    isEnabled: Boolean,
    checkboxColor: Color,
    alignment: String,
    onTypeChange: (String) -> Unit,
    onColorChange: (Color) -> Unit,
    onEnabledChange: (Boolean) -> Unit,
    onAlignmentChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        InteractiveRadioButtonGroup(
            options = listOf("Single", "Group", "Select All"),
            selectedOption = checkboxType,
            onOptionSelected = onTypeChange
        )
        Spacer(modifier = Modifier.height(8.dp))
        InteractiveSwitch(
            label = "Enabled",
            checked = isEnabled,
            onCheckedChange = onEnabledChange
        )
        Spacer(modifier = Modifier.height(8.dp))
        InteractiveColorPicker(
            label = "Checkbox Color",
            selectedColor = checkboxColor,
            onColorSelected = onColorChange
        )
        Spacer(modifier = Modifier.height(8.dp))
        InteractiveRadioButtonGroup(
            options = listOf("Horizontal", "Vertical"),
            selectedOption = alignment,
            onOptionSelected = onAlignmentChange
        )
    }
}

@Composable
private fun SingleCheckboxExample(
    color: Color,
    enabled: Boolean
) {
    var isChecked by remember { mutableStateOf(false) }

    CheckboxWithLabel(
        label = if (isChecked) "Checked" else "Unchecked",
        checked = isChecked,
        onCheckedChange = { isChecked = it },
        color = color,
        enabled = enabled
    )
}

@Composable
private fun GroupCheckboxExample(
    items: List<String>,
    selectedItems: MutableList<Boolean>,
    alignment: String,
    color: Color,
    enabled: Boolean
) {
    val isHorizontal = alignment == "Horizontal"
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxWidth(if (isHorizontal) 1f else 0.8f)
    ) {
        if (isHorizontal) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .horizontalScroll(scrollState)
                    .padding(horizontal = 16.dp)
            ) {
                items.forEachIndexed { index, item ->
                    CheckboxWithLabel(
                        label = item,
                        checked = selectedItems[index],
                        onCheckedChange = { selectedItems[index] = it },
                        color = color,
                        enabled = enabled
                    )
                }
            }
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                items.forEachIndexed { index, item ->
                    CheckboxWithLabel(
                        label = item,
                        checked = selectedItems[index],
                        onCheckedChange = { selectedItems[index] = it },
                        color = color,
                        enabled = enabled
                    )
                }
            }
        }
    }
}

@Composable
private fun SelectAllCheckboxExample(
    items: List<String>,
    selectedItems: MutableList<Boolean>,
    alignment: String,
    color: Color,
    enabled: Boolean
) {
    val allChecked = selectedItems.all { it }

    Column(
        modifier = Modifier.fillMaxWidth(0.8f),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CheckboxWithLabel(
            label = "Select All",
            checked = allChecked,
            onCheckedChange = { checked ->
                selectedItems.indices.forEach { index ->
                    selectedItems[index] = checked
                }
            },
            color = color,
            enabled = enabled
        )

        GroupCheckboxExample(
            items = items,
            selectedItems = selectedItems,
            alignment = alignment,
            color = color,
            enabled = enabled
        )
    }
}

@Composable
private fun CheckboxWithLabel(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    color: Color,
    enabled: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            colors = CheckboxDefaults.colors(
                checkedColor = color,
                uncheckedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                checkmarkColor = MaterialTheme.colorScheme.onPrimary
            )
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}