package com.droidcon.composablebank.ui.input_controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    InteractiveRadioButtonGroup(
                        options = listOf("Normal", "Check/Cross", "Day/Night", "On/Off"),
                        selectedOption = switchType.lowercase(),
                        onOptionSelected = { switchType = it }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    InteractiveSwitch(
                        label = "Enabled",
                        checked = isEnabled,
                        onCheckedChange = { isEnabled = it }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    InteractiveColorPicker(
                        label = "Switch Color",
                        selectedColor = switchColor,
                        onColorSelected = { switchColor = it }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    InteractiveSlider(
                        label = "Thumb Size",
                        value = thumbSize.value,
                        onValueChange = { thumbSize = it.dp },
                        valueRange = 10f..30f,
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                when (switchType) {
                    "Normal".lowercase() -> BasicSwitchExample(
                        checked = checkedState,
                        onCheckedChange = { checkedState = it },
                        color = switchColor,
                        thumbSize = thumbSize,
                        enabled = isEnabled
                    )
                    "Check/Cross".lowercase() -> CheckCrossSwitchExample(
                        checked = checkedState,
                        onCheckedChange = { checkedState = it },
                        color = switchColor,
                        enabled = isEnabled
                    )
                    "Day/Night".lowercase() -> DayNightSwitchExample(
                        checked = checkedState,
                        onCheckedChange = { checkedState = it },
                        color = switchColor,
                        enabled = isEnabled
                    )
                    "On/Off".lowercase() -> OnOffSwitchExample(
                        checked = checkedState,
                        onCheckedChange = { checkedState = it },
                        color = switchColor,
                        enabled = isEnabled
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    )
}

@Composable
private fun BasicSwitchExample(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    color: Color,
    thumbSize: Dp,
    enabled: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(0.8f)
    ) {
        Text(
            text = "Normal Switch",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            colors = SwitchDefaults.colors(
                checkedThumbColor = color,
                checkedTrackColor = color.copy(alpha = 0.3f),
            ),
            thumbContent = {
                if (checked) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Active",
                        tint = Color.White,
                        modifier = Modifier.size(thumbSize)
                    )
                }
            },
        )
    }
}

@Composable
private fun CheckCrossSwitchExample(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    color: Color,
    enabled: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(0.8f)
    ) {
        Text(
            text = "Check/Cross",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            colors = SwitchDefaults.colors(
                checkedThumbColor = color,
                checkedTrackColor = color.copy(alpha = 0.3f),
                uncheckedThumbColor = Color.Red,
                uncheckedTrackColor = Color.Red.copy(alpha = 0.3f)
            ),
            thumbContent = {
                Icon(
                    imageVector = if (checked) Icons.Default.Check else Icons.Default.Close,
                    contentDescription = if (checked) "Active" else "Inactive",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        )
    }
}

@Composable
private fun DayNightSwitchExample(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    color: Color,
    enabled: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(0.8f)
    ) {
        Text(
            text = "Day/Night Mode",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            colors = SwitchDefaults.colors(
                checkedThumbColor = color,
                checkedTrackColor = color.copy(alpha = 0.3f),
                uncheckedThumbColor = Color.Blue,
                uncheckedTrackColor = Color.Blue.copy(alpha = 0.3f)
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
private fun OnOffSwitchExample(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    color: Color,
    enabled: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(0.8f)
    ) {
        Text(
            text = "On/Off",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            colors = SwitchDefaults.colors(
                checkedThumbColor = color,
                checkedTrackColor = color.copy(alpha = 0.3f),
                uncheckedThumbColor = Color.DarkGray,
                uncheckedTrackColor = Color.Gray
            ),
            thumbContent = {
                Text(
                    text = if (checked) "ON" else "OFF",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                )
            }
        )
    }
}