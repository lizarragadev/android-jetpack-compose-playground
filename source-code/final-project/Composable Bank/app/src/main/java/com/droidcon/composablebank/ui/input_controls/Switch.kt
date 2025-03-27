package com.droidcon.composablebank.ui.input_controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.droidcon.composablebank.utils.CustomTopAppBar
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.components.InteractiveRadioButtonGroup
import com.droidcon.composablebank.components.InteractiveColorPicker
import com.droidcon.composablebank.components.InteractiveSlider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Switches(navController: NavController, name: String) {
    var switchType by remember { mutableStateOf("Normal") }
    var switchColor by remember { mutableStateOf(Color.Blue) }
    var thumbSize by remember { mutableStateOf(20.dp) }
    var isEnabled by remember { mutableStateOf(true) }
    var checkedState by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { CustomTopAppBar(title = name, navController = navController) },
        content = { paddingValues ->
            MainContent(
                paddingValues = paddingValues,
                switchType = switchType,
                switchColor = switchColor,
                thumbSize = thumbSize,
                isEnabled = isEnabled,
                checkedState = checkedState,
                onTypeChange = { switchType = it },
                onColorChange = { switchColor = it },
                onThumbSizeChange = { thumbSize = it },
                onEnabledChange = { isEnabled = it },
                onCheckedChange = { checkedState = it }
            )
        }
    )
}

@Composable
private fun MainContent(
    paddingValues: PaddingValues,
    switchType: String,
    switchColor: Color,
    thumbSize: Dp,
    isEnabled: Boolean,
    checkedState: Boolean,
    onTypeChange: (String) -> Unit,
    onColorChange: (Color) -> Unit,
    onThumbSizeChange: (Dp) -> Unit,
    onEnabledChange: (Boolean) -> Unit,
    onCheckedChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConfigurationPanel(
            switchType = switchType,
            isEnabled = isEnabled,
            switchColor = switchColor,
            thumbSize = thumbSize,
            onTypeChange = onTypeChange,
            onEnabledChange = onEnabledChange,
            onColorChange = onColorChange,
            onThumbSizeChange = onThumbSizeChange
        )

        Spacer(modifier = Modifier.height(24.dp))

        SwitchDisplay(
            switchType = switchType,
            checkedState = checkedState,
            switchColor = switchColor,
            thumbSize = thumbSize,
            isEnabled = isEnabled,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
private fun ConfigurationPanel(
    switchType: String,
    isEnabled: Boolean,
    switchColor: Color,
    thumbSize: Dp,
    onTypeChange: (String) -> Unit,
    onEnabledChange: (Boolean) -> Unit,
    onColorChange: (Color) -> Unit,
    onThumbSizeChange: (Dp) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        InteractiveRadioButtonGroup(
            options = listOf("Normal", "Check/Cross", "Day/Night", "On/Off"),
            selectedOption = switchType,
            onOptionSelected = onTypeChange
        )

        SwitchConfigItem("Enabled", isEnabled, onEnabledChange)
        ColorConfigItem(switchColor, onColorChange)
        ThumbSizeConfigItem(thumbSize, onThumbSizeChange)
    }
}

@Composable
private fun SwitchDisplay(
    switchType: String,
    checkedState: Boolean,
    switchColor: Color,
    thumbSize: Dp,
    isEnabled: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    when (switchType) {
        "Normal" -> BasicSwitch(
            checked = checkedState,
            color = switchColor,
            thumbSize = thumbSize,
            enabled = isEnabled,
            onCheckedChange = onCheckedChange
        )
        "Check/Cross" -> CheckCrossSwitch(
            checked = checkedState,
            color = switchColor,
            enabled = isEnabled,
            onCheckedChange = onCheckedChange
        )
        "Day/Night" -> DayNightSwitch(
            checked = checkedState,
            color = switchColor,
            enabled = isEnabled,
            onCheckedChange = onCheckedChange
        )
        "On/Off" -> OnOffSwitch(
            checked = checkedState,
            color = switchColor,
            enabled = isEnabled,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
private fun BasicSwitch(
    checked: Boolean,
    color: Color,
    thumbSize: Dp,
    enabled: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    SwitchRow(label = "Normal Switch") {
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            colors = switchColors(color),
            thumbContent = {
                if (checked) Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Active",
                    modifier = Modifier.size(thumbSize),
                    tint = Color.White
                )
            }
        )
    }
}

@Composable
private fun CheckCrossSwitch(
    checked: Boolean,
    color: Color,
    enabled: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    SwitchRow(label = "Check/Cross") {
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            colors = SwitchDefaults.colors(
                checkedThumbColor = color,
                checkedTrackColor = color.copy(0.3f),
                uncheckedThumbColor = Color.Red,
                uncheckedTrackColor = Color.Red.copy(0.3f)
            ),
            thumbContent = {
                Icon(
                    imageVector = if (checked) Icons.Default.Check else Icons.Default.Close,
                    contentDescription = if (checked) "Active" else "Inactive",
                    modifier = Modifier.size(18.dp),
                    tint = Color.White
                )
            }
        )
    }
}

@Composable
private fun DayNightSwitch(
    checked: Boolean,
    color: Color,
    enabled: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    SwitchRow(label = "Day/Night Mode") {
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            colors = switchColors(color).copy(
                uncheckedThumbColor = Color.Blue,
                uncheckedTrackColor = Color.Blue.copy(0.3f)
            ),
            thumbContent = {
                Icon(
                    imageVector = if (checked) Icons.Default.LightMode else Icons.Default.DarkMode,
                    contentDescription = "Theme",
                    modifier = Modifier.size(18.dp),
                    tint = Color.White
                )
            }
        )
    }
}

@Composable
private fun OnOffSwitch(
    checked: Boolean,
    color: Color,
    enabled: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    SwitchRow(label = "On/Off") {
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            colors = switchColors(color).copy(
                uncheckedThumbColor = Color.DarkGray,
                uncheckedTrackColor = Color.Gray
            ),
            thumbContent = {
                Text(
                    text = if (checked) "ON" else "OFF",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
            }
        )
    }
}

@Composable
private fun SwitchRow(
    label: String,
    content: @Composable () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(0.8f)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        content()
    }
}

@Composable
private fun switchColors(color: Color) = SwitchDefaults.colors(
    checkedThumbColor = color,
    checkedTrackColor = color.copy(alpha = 0.3f)
)

@Composable
private fun SwitchConfigItem(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Column {
        Spacer(Modifier.height(8.dp))
        InteractiveSwitch(label, checked, onCheckedChange)
    }
}

@Composable
private fun ColorConfigItem(color: Color, onColorChange: (Color) -> Unit) {
    Column {
        Spacer(Modifier.height(8.dp))
        InteractiveColorPicker("Switch Color", color, onColorChange)
    }
}

@Composable
private fun ThumbSizeConfigItem(thumbSize: Dp, onThumbSizeChange: (Dp) -> Unit) {
    Column {
        Spacer(Modifier.height(8.dp))
        InteractiveSlider(
            label = "Thumb Size",
            value = thumbSize.value,
            onValueChange = { onThumbSizeChange(it.dp) },
            valueRange = 10f..30f
        )
    }
}