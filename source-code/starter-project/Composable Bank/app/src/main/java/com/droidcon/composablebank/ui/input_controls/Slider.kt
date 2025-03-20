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



                }

                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {



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



        Text(
            text = "Range: ${start.toInt()} - ${end.toInt()}",
            style = MaterialTheme.typography.bodyLarge,
            color = sliderColor
        )
    }
}