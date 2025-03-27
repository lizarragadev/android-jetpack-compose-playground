package com.droidcon.composablebank.ui.input_controls

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.droidcon.composablebank.utils.CustomTopAppBar
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.graphics.Color
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.components.InteractiveRadioButtonGroup
import com.droidcon.composablebank.components.InteractiveColorPicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RadioButtons(navController: NavController, name: String) {
    var showSnackbar by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    var alignment by remember { mutableStateOf("Vertical") }
    var selectedOption by remember { mutableStateOf("") }
    var isEnabled by remember { mutableStateOf(true) }
    var radioColor by remember { mutableStateOf(Color.Blue) }

    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { CustomTopAppBar(title = name, navController = navController) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { paddingValues ->
            MainContent(
                paddingValues = paddingValues,
                alignment = alignment,
                selectedOption = selectedOption,
                isEnabled = isEnabled,
                radioColor = radioColor,
                options = options,
                onAlignmentChange = { alignment = it },
                onOptionSelected = { selectedOption = it },
                onEnabledChange = { isEnabled = it },
                onColorChange = { radioColor = it }
            )
        }
    )

    HandleSnackbar(showSnackbar, snackbarHostState, selectedOption) {
        showSnackbar = false
    }
}

@Composable
private fun MainContent(
    paddingValues: PaddingValues,
    alignment: String,
    selectedOption: String,
    isEnabled: Boolean,
    radioColor: Color,
    options: List<String>,
    onAlignmentChange: (String) -> Unit,
    onOptionSelected: (String) -> Unit,
    onEnabledChange: (Boolean) -> Unit,
    onColorChange: (Color) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConfigurationSection(
            alignment = alignment,
            isEnabled = isEnabled,
            radioColor = radioColor,
            onAlignmentChange = onAlignmentChange,
            onEnabledChange = onEnabledChange,
            onColorChange = onColorChange
        )

        Spacer(modifier = Modifier.height(24.dp))

        RadioButtonsContainer(
            alignment = alignment,
            options = options,
            selectedOption = selectedOption,
            onOptionSelected = onOptionSelected,
            radioColor = radioColor,
            isEnabled = isEnabled
        )
    }
}

@Composable
private fun ConfigurationSection(
    alignment: String,
    isEnabled: Boolean,
    radioColor: Color,
    onAlignmentChange: (String) -> Unit,
    onEnabledChange: (Boolean) -> Unit,
    onColorChange: (Color) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InteractiveRadioButtonGroup(
            options = listOf("Vertical", "Horizontal"),
            selectedOption = alignment,
            onOptionSelected = { onAlignmentChange(it) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        InteractiveSwitch(
            label = "Enabled",
            checked = isEnabled,
            onCheckedChange = onEnabledChange
        )

        Spacer(modifier = Modifier.height(8.dp))

        InteractiveColorPicker(
            label = "Radio Color",
            selectedColor = radioColor,
            onColorSelected = onColorChange
        )
    }
}

@Composable
private fun RadioButtonsContainer(
    alignment: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    radioColor: Color,
    isEnabled: Boolean
) {
    when (alignment) {
        "Vertical" -> VerticalRadioGroup(
            options = options,
            selectedOption = selectedOption,
            onOptionSelected = onOptionSelected,
            color = radioColor,
            enabled = isEnabled
        )
        "Horizontal" -> HorizontalRadioGroup(
            options = options,
            selectedOption = selectedOption,
            onOptionSelected = onOptionSelected,
            color = radioColor,
            enabled = isEnabled
        )
    }
}

@Composable
private fun VerticalRadioGroup(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    color: Color,
    enabled: Boolean
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth(0.8f)
    ) {
        options.forEach { option ->
            RadioButtonItem(
                label = option,
                selected = option == selectedOption,
                onSelect = { onOptionSelected(option) },
                color = color,
                enabled = enabled
            )
        }
    }
}

@Composable
private fun HorizontalRadioGroup(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    color: Color,
    enabled: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        options.forEach { option ->
            RadioButtonItem(
                label = option,
                selected = option == selectedOption,
                onSelect = { onOptionSelected(option) },
                color = color,
                enabled = enabled
            )
        }
    }
}

@Composable
private fun RadioButtonItem(
    label: String,
    selected: Boolean,
    onSelect: () -> Unit,
    color: Color,
    enabled: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.selectable(
            selected = selected,
            onClick = onSelect,
            enabled = enabled
        )
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
            enabled = enabled,
            colors = RadioButtonDefaults.colors(
                selectedColor = color,
                unselectedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
private fun HandleSnackbar(
    showSnackbar: Boolean,
    snackbarHostState: SnackbarHostState,
    selectedOption: String,
    onDismiss: () -> Unit
) {
    if (showSnackbar) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar("Selected: $selectedOption")
            onDismiss()
        }
    }
}