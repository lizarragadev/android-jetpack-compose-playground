package com.droidcon.composablebank.ui.basic_comp_layouts

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droidcon.composablebank.components.*
import com.droidcon.composablebank.utils.CustomTopAppBar

@Composable
internal fun TextComposable(navController: NavController, name: String) {
    var textColor by remember { mutableStateOf(Color.Black) }
    var fontSize by remember { mutableFloatStateOf(16f) }
    var textAlign by remember { mutableStateOf("Start") }
    var letterSpacing by remember { mutableFloatStateOf(0f) }
    var lineHeight by remember { mutableFloatStateOf(20f) }
    var textDecoration by remember { mutableStateOf("None") }
    var textTransform by remember { mutableStateOf("None") }
    var textPosition by remember { mutableStateOf("Normal") }
    var selectedFont by remember { mutableStateOf("Sans Serif") }

    val fontFamilies = listOf("Sans Serif", "Serif", "Monospace", "Cursive")
    val textTransforms = listOf("None", "Uppercase", "Lowercase")
    val textPositions = listOf("Normal", "Superscript", "Subscript")
    val textDecorations = listOf("None", "Underline", "Line Through")
    val alignments = listOf("Start", "Center", "End", "Justify")
    var isBold by remember { mutableStateOf(false) }
    var isItalic by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { CustomTopAppBar(title = name, navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                tonalElevation = 4.dp,
                shape = MaterialTheme.shapes.medium
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    InteractiveColorPicker(
                        label = "Text Color",
                        selectedColor = textColor,
                        onColorSelected = { textColor = it }
                    )

                    InteractiveDropdown(
                        options = fontFamilies,
                        selectedOption = selectedFont,
                        onOptionSelected = { selectedFont = it },
                        label = "Font Family"
                    )

                    InteractiveSlider(
                        label = "Font Size: ${fontSize.toInt()}sp",
                        value = fontSize,
                        onValueChange = { fontSize = it },
                        valueRange = 12f..48f
                    )

                    InteractiveSlider(
                        label = "Letter Spacing: ${"%.1f".format(letterSpacing)}sp",
                        value = letterSpacing,
                        onValueChange = { letterSpacing = it },
                        valueRange = 0f..2f
                    )

                    InteractiveSlider(
                        label = "Line Height: ${lineHeight.toInt()}sp",
                        value = lineHeight,
                        onValueChange = { lineHeight = it },
                        valueRange = 16f..48f
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(modifier = Modifier.width(IntrinsicSize.Min).weight(1f)) {
                            InteractiveDropdown(
                                options = textTransforms,
                                selectedOption = textTransform,
                                onOptionSelected = { textTransform = it },
                                label = "Text Case",
                                modifier = Modifier.fillMaxWidth(0.95f)
                            )
                        }

                        Box(modifier = Modifier.width(IntrinsicSize.Min).weight(1f)) {
                            InteractiveDropdown(
                                options = textDecorations,
                                selectedOption = textDecoration,
                                onOptionSelected = { textDecoration = it },
                                label = "Text Decoration",
                                modifier = Modifier.fillMaxWidth(0.95f)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(modifier = Modifier.width(IntrinsicSize.Min).weight(1f)) {
                            InteractiveDropdown(
                                options = textPositions,
                                selectedOption = textPosition,
                                onOptionSelected = { textPosition = it },
                                label = "Text Position",
                                modifier = Modifier.fillMaxWidth(0.95f)
                            )
                        }

                        Box(modifier = Modifier.width(IntrinsicSize.Min).weight(1f)) {
                            InteractiveDropdown(
                                options = alignments,
                                selectedOption = textAlign,
                                onOptionSelected = { textAlign = it },
                                label = "Text Alignment",
                                modifier = Modifier.fillMaxWidth(0.95f)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            InteractiveSwitch(
                                label = "Bold",
                                checked = isBold,
                                onCheckedChange = { isBold = it },
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        }

                        Box(modifier = Modifier.weight(1f)) {
                            InteractiveSwitch(
                                label = "Italic",
                                checked = isItalic,
                                onCheckedChange = { isItalic = it },
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    text = applyTextTransforms("Jetpack Compose Text Demo", textTransform),
                    color = textColor,
                    fontSize = fontSize.sp,
                    fontFamily = when(selectedFont) {
                        "Serif" -> FontFamily.Serif
                        "Monospace" -> FontFamily.Monospace
                        "Cursive" -> FontFamily.Cursive
                        else -> FontFamily.SansSerif
                    },
                    fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
                    fontStyle = if (isItalic) FontStyle.Italic else FontStyle.Normal,
                    letterSpacing = letterSpacing.sp,
                    lineHeight = lineHeight.sp,
                    textAlign = when(textAlign) {
                        "Start" -> TextAlign.Start
                        "Center" -> TextAlign.Center
                        "End" -> TextAlign.End
                        else -> TextAlign.Justify
                    },
                    textDecoration = when(textDecoration) {
                        "Underline" -> TextDecoration.Underline
                        "Line Through" -> TextDecoration.LineThrough
                        else -> null
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = buildAnnotatedString {
                        append("H")
                        withStyle(SpanStyle(
                            baselineShift = when(textPosition) {
                                "Superscript" -> BaselineShift.Superscript
                                "Subscript" -> BaselineShift.Subscript
                                else -> BaselineShift.None
                            },
                            fontSize = (fontSize * 0.75f).sp
                        )) {
                            append(when(textPosition) {
                                "Superscript" -> "2"
                                "Subscript" -> "O"
                                else -> " "
                            })
                        }
                        append(if(textPosition == "Subscript") "2" else "ello World")
                    },
                    color = textColor,
                    fontSize = fontSize.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(
                            color = Color.Red,
                            fontSize = (fontSize * 1.2f).sp
                        )) {
                            append("Multi")
                        }
                        append(" Style ")
                        withStyle(SpanStyle(
                            color = Color.Blue,
                            textDecoration = TextDecoration.Underline
                        )) {
                            append("Text")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

private fun applyTextTransforms(text: String, transform: String): String {
    return when(transform) {
        "Uppercase" -> text.uppercase()
        "Lowercase" -> text.lowercase()
        else -> text
    }
}