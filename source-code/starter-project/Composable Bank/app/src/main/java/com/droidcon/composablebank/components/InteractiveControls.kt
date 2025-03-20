package com.droidcon.composablebank.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color

@Composable
fun InteractiveCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {


}

@Composable
fun InteractiveSlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    modifier: Modifier = Modifier
) {


}

@Composable
fun InteractiveButton(
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    modifier: Modifier = Modifier
) {


}

@Composable
fun InteractiveSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {


}

@Composable
fun InteractiveTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {


}

@Composable
fun InteractiveColorPicker(
    label: String,
    selectedColor: Color,
    onColorSelected: (Color) -> Unit
) {



}

fun InteractiveToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

@Composable
fun InteractiveSwitch(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {


}

@Composable
fun InteractiveRadioButtonGroup(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {



}