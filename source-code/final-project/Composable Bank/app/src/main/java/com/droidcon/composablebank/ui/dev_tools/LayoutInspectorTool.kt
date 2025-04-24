package com.droidcon.composablebank.ui.dev_tools

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.droidcon.composablebank.components.InteractiveColorPicker
import com.droidcon.composablebank.components.InteractiveRadioButtonGroup
import com.droidcon.composablebank.components.InteractiveSlider
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.utils.CustomTopAppBar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import com.droidcon.composablebank.components.InteractiveDropdown

@Composable
internal fun LayoutInspectorTool(navController: NavController, name: String) {
    var padding by remember { mutableFloatStateOf(16f) }
    var alignment by remember { mutableStateOf(Alignment.CenterHorizontally) }
    var showBox by remember { mutableStateOf(true) }
    var backgroundColor by remember { mutableStateOf(Color.White) }
    var columnWeight by remember { mutableFloatStateOf(0.5f) }
    var rowArrangement by remember { mutableStateOf(Arrangement.Start) }

    Scaffold(
        topBar = { CustomTopAppBar(title = name, navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            ConfigurationSection(
                padding = padding,
                onPaddingChange = { padding = it },
                alignmentOptions = listOf(
                    Alignment.Start,
                    Alignment.CenterHorizontally,
                    Alignment.End
                ),
                selectedAlignment = alignment,
                onAlignmentChange = { alignment = it },
                showBox = showBox,
                onShowBoxChange = { showBox = it },
                backgroundColor = backgroundColor,
                onBackgroundColorChange = { backgroundColor = it },
                columnWeight = columnWeight,
                onColumnWeightChange = { columnWeight = it },
                rowArrangementOptions = listOf(
                    Arrangement.Start,
                    Arrangement.Center,
                    Arrangement.End,
                    Arrangement.SpaceBetween
                ),
                selectedArrangement = rowArrangement,
                onArrangementChange = { rowArrangement = it }
            )

            PreviewSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(16.dp),
                padding = padding.toInt(),
                alignment = alignment,
                showBox = showBox,
                backgroundColor = backgroundColor,
                columnWeight = columnWeight,
                rowArrangement = rowArrangement
            )

            CodeSnippetSection(
                padding = padding.toInt(),
                alignment = alignment,
                showBox = showBox,
                backgroundColor = backgroundColor,
                columnWeight = columnWeight,
                rowArrangement = rowArrangement
            )
        }
    }
}

@Composable
private fun ConfigurationSection(
    padding: Float,
    onPaddingChange: (Float) -> Unit,
    alignmentOptions: List<Alignment.Horizontal>,
    selectedAlignment: Alignment.Horizontal,
    onAlignmentChange: (Alignment.Horizontal) -> Unit,
    showBox: Boolean,
    onShowBoxChange: (Boolean) -> Unit,
    backgroundColor: Color,
    onBackgroundColorChange: (Color) -> Unit,
    columnWeight: Float,
    onColumnWeightChange: (Float) -> Unit,
    rowArrangementOptions: List<Arrangement.Horizontal>,
    selectedArrangement: Arrangement.Horizontal,
    onArrangementChange: (Arrangement.Horizontal) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Layout Configuration",
                style = MaterialTheme.typography.headlineSmall
            )

            InteractiveSlider(
                label = "Padding: ${padding.toInt()}dp",
                value = padding,
                onValueChange = onPaddingChange,
                valueRange = 0f..64f
            )

            val alignmentDisplayNames = alignmentOptions.map { alignment ->
                when(alignment) {
                    Alignment.Start -> "Start"
                    Alignment.CenterHorizontally -> "Center"
                    Alignment.End -> "End"
                    else -> "Unknown"
                }
            }

            InteractiveRadioButtonGroup(
                options = alignmentDisplayNames,
                selectedOption = alignmentToDisplayName(selectedAlignment),
                onOptionSelected = { option ->
                    val newAlignment = when(option) {
                        "Start" -> Alignment.Start
                        "Center" -> Alignment.CenterHorizontally
                        "End" -> Alignment.End
                        else -> Alignment.CenterHorizontally
                    }
                    onAlignmentChange(newAlignment)
                }
            )

            InteractiveSwitch(
                label = "Show Box",
                checked = showBox,
                onCheckedChange = onShowBoxChange
            )

            InteractiveColorPicker(
                label = "Background Color",
                selectedColor = backgroundColor,
                onColorSelected = onBackgroundColorChange
            )

            InteractiveSlider(
                label = "Column Weight: ${"%.2f".format(columnWeight)}",
                value = columnWeight,
                onValueChange = onColumnWeightChange,
                valueRange = 0f..1f
            )

            val arrangementDisplayNames = rowArrangementOptions.map { arrangement ->
                when(arrangement) {
                    Arrangement.Start -> "Start"
                    Arrangement.Center -> "Center"
                    Arrangement.End -> "End"
                    Arrangement.SpaceBetween -> "Space Between"
                    else -> "Unknown"
                }
            }

            InteractiveDropdown(
                options = arrangementDisplayNames,
                selectedOption = arrangementToDisplayName(selectedArrangement),
                onOptionSelected = { option ->
                    val newArrangement = when(option) {
                        "Start" -> Arrangement.Start
                        "Center" -> Arrangement.Center
                        "End" -> Arrangement.End
                        "Space Between" -> Arrangement.SpaceBetween
                        else -> Arrangement.Start
                    }
                    onArrangementChange(newArrangement)
                },
                label = "Row Arrangement"
            )
        }
    }
}

@Composable
private fun PreviewSection(
    modifier: Modifier = Modifier,
    padding: Int,
    alignment: Alignment.Horizontal,
    showBox: Boolean,
    backgroundColor: Color,
    columnWeight: Float,
    rowArrangement: Arrangement.Horizontal
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 4.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding.dp)
                .background(backgroundColor)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = alignment
            ) {
                if (showBox) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(MaterialTheme.colorScheme.primary)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = rowArrangement
                ) {
                    Box(
                        modifier = Modifier
                            .weight(columnWeight)
                            .height(50.dp)
                            .background(MaterialTheme.colorScheme.secondary)
                    )
                    Box(
                        modifier = Modifier
                            .weight(1 - columnWeight)
                            .height(50.dp)
                            .background(MaterialTheme.colorScheme.tertiary)
                    )
                }
            }
        }
    }
}

@Composable
private fun CodeSnippetSection(
    padding: Int,
    alignment: Alignment.Horizontal,
    showBox: Boolean,
    backgroundColor: Color,
    columnWeight: Float,
    rowArrangement: Arrangement.Horizontal
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Generated Layout Code",
                style = MaterialTheme.typography.headlineSmall
            )

            val code = buildString {
                appendLine("@Composable")
                appendLine("fun DemoLayout() {")
                appendLine("    Box(")
                appendLine("        modifier = Modifier")
                appendLine("            .fillMaxSize()")
                appendLine("            .padding(${padding}dp)")
                appendLine("            .background(Color(${backgroundColor.value.toInt().toHex()}))")
                appendLine("    ) {")
                appendLine("        Column(")
                appendLine("            modifier = Modifier.fillMaxSize(),")
                appendLine("            verticalArrangement = Arrangement.SpaceEvenly,")
                appendLine("            horizontalAlignment = ${alignmentToCode(alignment)}")
                appendLine("        ) {")
                if (showBox) {
                    appendLine("            Box(")
                    appendLine("                modifier = Modifier")
                    appendLine("                    .size(100.dp)")
                    appendLine("                    .background(MaterialTheme.colorScheme.primary)")
                    appendLine("            )")
                }
                appendLine("            Row(")
                appendLine("                modifier = Modifier.fillMaxWidth(),")
                appendLine("                horizontalArrangement = ${arrangementToCode(rowArrangement)}")
                appendLine("            ) {")
                appendLine("                Box(")
                appendLine("                    modifier = Modifier")
                appendLine("                        .weight(${"%.2f".format(columnWeight)})")
                appendLine("                        .height(50.dp)")
                appendLine("                        .background(MaterialTheme.colorScheme.secondary)")
                appendLine("                )")
                appendLine("                Box(")
                appendLine("                    modifier = Modifier")
                appendLine("                        .weight(${"%.2f".format(1 - columnWeight)})")
                appendLine("                        .height(50.dp)")
                appendLine("                        .background(MaterialTheme.colorScheme.tertiary)")
                appendLine("                )")
                appendLine("            }")
                appendLine("        }")
                appendLine("    }")
                appendLine("}")
            }

            Text(
                text = code,
                style = MaterialTheme.typography.bodySmall,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f))
                    .padding(8.dp)
            )
        }
    }
}

private fun alignmentToDisplayName(alignment: Alignment.Horizontal): String {
    return when(alignment) {
        Alignment.Start -> "Start"
        Alignment.CenterHorizontally -> "Center"
        Alignment.End -> "End"
        else -> "Center"
    }
}

private fun arrangementToDisplayName(arrangement: Arrangement.Horizontal): String {
    return when(arrangement) {
        Arrangement.Start -> "Start"
        Arrangement.Center -> "Center"
        Arrangement.End -> "End"
        Arrangement.SpaceBetween -> "Space Between"
        else -> "Start"
    }
}

private fun alignmentToCode(alignment: Alignment.Horizontal): String {
    return when(alignment) {
        Alignment.Start -> "Alignment.Start"
        Alignment.CenterHorizontally -> "Alignment.CenterHorizontally"
        Alignment.End -> "Alignment.End"
        else -> "Alignment.CenterHorizontally"
    }
}

private fun arrangementToCode(arrangement: Arrangement.Horizontal): String {
    return when(arrangement) {
        Arrangement.Start -> "Arrangement.Start"
        Arrangement.Center -> "Arrangement.Center"
        Arrangement.End -> "Arrangement.End"
        Arrangement.SpaceBetween -> "Arrangement.SpaceBetween"
        else -> "Arrangement.Start"
    }
}

private fun Int.toHex(): String {
    return "%08X".format(this)
}