package com.droidcon.composablebank.ui.input_controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.droidcon.composablebank.utils.CustomTopAppBar
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.graphics.Color
import com.droidcon.composablebank.components.InteractiveColorPicker
import com.droidcon.composablebank.components.InteractiveRadioButtonGroup
import com.droidcon.composablebank.components.InteractiveSwitch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Sliders(navController: NavController, name: String) {
    var sliderType by remember { mutableStateOf("continuous") }
    var showValue by remember { mutableStateOf(true) }
    var sliderColor by remember { mutableStateOf(Color.Blue) }
    var isEnabled by remember { mutableStateOf(true) }
    var steps by remember { mutableIntStateOf(10) }

    var sliderValue by remember { mutableFloatStateOf(50f) }
    var sliderRangeStart by remember { mutableFloatStateOf(25f) }
    var sliderRangeEnd by remember { mutableFloatStateOf(75f) }

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
                        options = listOf("Continuous", "Discrete", "Range"),
                        selectedOption = sliderType,
                        onOptionSelected = { sliderType = it.lowercase() }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    InteractiveSwitch(
                        label = "Show Value",
                        checked = showValue,
                        onCheckedChange = { showValue = it }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    InteractiveColorPicker(
                        label = "Slider Color",
                        selectedColor = sliderColor,
                        onColorSelected = { sliderColor = it }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    InteractiveSwitch(
                        label = "Enabled",
                        checked = isEnabled,
                        onCheckedChange = { isEnabled = it }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    when (sliderType) {
                        "continuous" -> ContinuousSlider(
                            value = sliderValue,
                            onValueChange = { sliderValue = it },
                            showValue = showValue,
                            sliderColor = sliderColor,
                            isEnabled = isEnabled
                        )
                        "discrete" -> DiscreteSlider(
                            value = sliderValue,
                            onValueChange = { sliderValue = it },
                            steps = steps,
                            showValue = showValue,
                            sliderColor = sliderColor,
                            isEnabled = isEnabled
                        )
                        "range" -> RangeSlider(
                            start = sliderRangeStart,
                            end = sliderRangeEnd,
                            onValueChange = { range ->
                                sliderRangeStart = range.start
                                sliderRangeEnd = range.endInclusive
                            },
                            sliderColor = sliderColor,
                            isEnabled = isEnabled
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    )
}

@Composable
private fun ContinuousSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    showValue: Boolean,
    sliderColor: Color,
    isEnabled: Boolean
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..100f,
            enabled = isEnabled,
            colors = SliderDefaults.colors(
                thumbColor = sliderColor,
                activeTrackColor = sliderColor,
                inactiveTrackColor = sliderColor.copy(alpha = 0.3f)
            )
        )
        if (showValue) {
            Text(
                text = "Value: ${value.toInt()}",
                style = MaterialTheme.typography.bodyLarge,
                color = sliderColor
            )
        }
    }
}

@Composable
private fun DiscreteSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    steps: Int,
    showValue: Boolean,
    sliderColor: Color,
    isEnabled: Boolean
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..100f,
            steps = steps,
            enabled = isEnabled,
            colors = SliderDefaults.colors(
                thumbColor = sliderColor,
                activeTrackColor = sliderColor,
                inactiveTrackColor = sliderColor.copy(alpha = 0.3f)
            ),
        )
        if (showValue) {
            Text(
                text = "Discrete Value: ${value.toInt()}",
                style = MaterialTheme.typography.bodyLarge,
                color = sliderColor
            )
        }
    }
}

@Composable
private fun RangeSlider(
    start: Float,
    end: Float,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    sliderColor: Color,
    isEnabled: Boolean
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        RangeSlider(
            value = start..end,
            onValueChange = onValueChange,
            valueRange = 0f..100f,
            enabled = isEnabled,
            colors = SliderDefaults.colors(
                thumbColor = sliderColor,
                activeTrackColor = sliderColor,
                inactiveTrackColor = sliderColor.copy(alpha = 0.3f)
            )
        )
        Text(
            text = "Range: ${start.toInt()} - ${end.toInt()}",
            style = MaterialTheme.typography.bodyLarge,
            color = sliderColor
        )
    }
}