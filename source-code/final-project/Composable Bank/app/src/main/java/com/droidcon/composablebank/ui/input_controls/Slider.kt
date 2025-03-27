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
    var sliderType by remember { mutableStateOf("Continuous") }
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
            MainContent(
                paddingValues = paddingValues,
                sliderType = sliderType,
                showValue = showValue,
                sliderColor = sliderColor,
                isEnabled = isEnabled,
                steps = steps,
                sliderValue = sliderValue,
                sliderRangeStart = sliderRangeStart,
                sliderRangeEnd = sliderRangeEnd,
                onTypeChange = { sliderType = it },
                onShowValueChange = { showValue = it },
                onColorChange = { sliderColor = it },
                onEnabledChange = { isEnabled = it },
                onStepsChange = { steps = it },
                onValueChange = { sliderValue = it },
                onRangeChange = { start, end ->
                    sliderRangeStart = start
                    sliderRangeEnd = end
                }
            )
        }
    )
}

@Composable
private fun MainContent(
    paddingValues: PaddingValues,
    sliderType: String,
    showValue: Boolean,
    sliderColor: Color,
    isEnabled: Boolean,
    steps: Int,
    sliderValue: Float,
    sliderRangeStart: Float,
    sliderRangeEnd: Float,
    onTypeChange: (String) -> Unit,
    onShowValueChange: (Boolean) -> Unit,
    onColorChange: (Color) -> Unit,
    onEnabledChange: (Boolean) -> Unit,
    onStepsChange: (Int) -> Unit,
    onValueChange: (Float) -> Unit,
    onRangeChange: (Float, Float) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SliderConfigurationPanel(
            sliderType = sliderType,
            showValue = showValue,
            sliderColor = sliderColor,
            isEnabled = isEnabled,
            onTypeChange = onTypeChange,
            onShowValueChange = onShowValueChange,
            onColorChange = onColorChange,
            onEnabledChange = onEnabledChange,
        )

        Spacer(modifier = Modifier.height(24.dp))

        SliderDisplay(
            sliderType = sliderType,
            sliderValue = sliderValue,
            sliderRangeStart = sliderRangeStart,
            sliderRangeEnd = sliderRangeEnd,
            showValue = showValue,
            sliderColor = sliderColor,
            isEnabled = isEnabled,
            steps = steps,
            onValueChange = onValueChange,
            onRangeChange = onRangeChange
        )
    }
}

@Composable
private fun SliderConfigurationPanel(
    sliderType: String,
    showValue: Boolean,
    sliderColor: Color,
    isEnabled: Boolean,
    onTypeChange: (String) -> Unit,
    onShowValueChange: (Boolean) -> Unit,
    onColorChange: (Color) -> Unit,
    onEnabledChange: (Boolean) -> Unit,
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
            onOptionSelected = { onTypeChange(it) }
        )

        ConfigurationSwitch("Show Value", showValue, onShowValueChange)
        ColorSelection("Slider Color", sliderColor, onColorChange)
        ConfigurationSwitch("Enabled", isEnabled, onEnabledChange)
    }
}

@Composable
private fun SliderDisplay(
    sliderType: String,
    sliderValue: Float,
    sliderRangeStart: Float,
    sliderRangeEnd: Float,
    showValue: Boolean,
    sliderColor: Color,
    isEnabled: Boolean,
    steps: Int,
    onValueChange: (Float) -> Unit,
    onRangeChange: (Float, Float) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        when (sliderType) {
            "Continuous" -> ContinuousSliderComponent(
                value = sliderValue,
                showValue = showValue,
                color = sliderColor,
                enabled = isEnabled,
                onValueChange = onValueChange
            )
            "Discrete" -> DiscreteSliderComponent(
                value = sliderValue,
                steps = steps,
                showValue = showValue,
                color = sliderColor,
                enabled = isEnabled,
                onValueChange = onValueChange
            )
            "Range" -> RangeSliderComponent(
                start = sliderRangeStart,
                end = sliderRangeEnd,
                color = sliderColor,
                enabled = isEnabled,
                onRangeChange = onRangeChange
            )
        }
    }
}

@Composable
private fun ContinuousSliderComponent(
    value: Float,
    showValue: Boolean,
    color: Color,
    enabled: Boolean,
    onValueChange: (Float) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..100f,
            enabled = enabled,
            colors = sliderColors(color)
        )
        ValueDisplay(showValue, "Value: ${value.toInt()}", color)
    }
}

@Composable
private fun DiscreteSliderComponent(
    value: Float,
    steps: Int,
    showValue: Boolean,
    color: Color,
    enabled: Boolean,
    onValueChange: (Float) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..100f,
            steps = steps,
            enabled = enabled,
            colors = sliderColors(color)
        )
        ValueDisplay(showValue, "Discrete Value: ${value.toInt()}", color)
    }
}

@Composable
private fun RangeSliderComponent(
    start: Float,
    end: Float,
    color: Color,
    enabled: Boolean,
    onRangeChange: (Float, Float) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        RangeSlider(
            value = start..end,
            onValueChange = { range ->
                onRangeChange(range.start, range.endInclusive)
            },
            valueRange = 0f..100f,
            enabled = enabled,
            colors = sliderColors(color)
        )
        Text(
            text = "Range: ${start.toInt()} - ${end.toInt()}",
            style = MaterialTheme.typography.bodyLarge,
            color = color
        )
    }
}

@Composable
private fun sliderColors(color: Color) = SliderDefaults.colors(
    thumbColor = color,
    activeTrackColor = color,
    inactiveTrackColor = color.copy(alpha = 0.3f)
)

@Composable
private fun ValueDisplay(visible: Boolean, text: String, color: Color) {
    if (visible) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = color
        )
    }
}

@Composable
private fun ConfigurationSwitch(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Column {
        Spacer(modifier = Modifier.height(8.dp))
        InteractiveSwitch(
            label = label,
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
private fun ColorSelection(label: String, color: Color, onColorSelected: (Color) -> Unit) {
    Column {
        Spacer(modifier = Modifier.height(8.dp))
        InteractiveColorPicker(
            label = label,
            selectedColor = color,
            onColorSelected = onColorSelected
        )
    }
}