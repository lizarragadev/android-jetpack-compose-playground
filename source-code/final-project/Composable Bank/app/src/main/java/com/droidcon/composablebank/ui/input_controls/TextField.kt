package com.droidcon.composablebank.ui.input_controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.droidcon.composablebank.utils.CustomTopAppBar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.components.InteractiveRadioButtonGroup
import androidx.compose.runtime.remember
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFields(navController: NavController, name: String) {
    var textFieldType by remember { mutableStateOf("filled") }
    var showHelperText by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var showPrefixSuffix by remember { mutableStateOf(false) }
    var showCharacterCounter by remember { mutableStateOf(false) }
    var isReadOnly by remember { mutableStateOf(false) }
    var useCompactDensity by remember { mutableStateOf(false) }

    var text by remember { mutableStateOf("") }
    var creditCardNumber by remember { mutableStateOf("") }

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
                        options = listOf("Filled", "Outlined"),
                        selectedOption = textFieldType,
                        onOptionSelected = { textFieldType = it.lowercase() }
                    )
                    InteractiveSwitch(
                        label = "Show Helper Text",
                        checked = showHelperText,
                        onCheckedChange = { showHelperText = it }
                    )
                    InteractiveSwitch(
                        label = "Show Error State",
                        checked = showError,
                        onCheckedChange = { showError = it }
                    )
                    InteractiveSwitch(
                        label = "Show Prefix/Suffix",
                        checked = showPrefixSuffix,
                        onCheckedChange = { showPrefixSuffix = it }
                    )
                    InteractiveSwitch(
                        label = "Character Counter",
                        checked = showCharacterCounter,
                        onCheckedChange = { showCharacterCounter = it }
                    )
                    InteractiveSwitch(
                        label = "Read-Only",
                        checked = isReadOnly,
                        onCheckedChange = { isReadOnly = it }
                    )
                    InteractiveSwitch(
                        label = "Compact Density",
                        checked = useCompactDensity,
                        onCheckedChange = { useCompactDensity = it }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                when (textFieldType) {
                    "filled" -> TextField(
                        value = text,
                        onValueChange = { text = it },
                        label = { Text("Full Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.LightGray,
                            unfocusedContainerColor = Color.LightGray,
                            focusedIndicatorColor = Color.Cyan,
                            unfocusedIndicatorColor = Color.Gray
                        ),
                        supportingText = if (showHelperText) { { Text("Ej: Gustavo Lizarraga") } } else null,
                        isError = showError,
                        trailingIcon = if (showError) { { Icon(Icons.Default.Error, "Error") } } else null,
                        prefix = if (showPrefixSuffix) { { Text("+1 ") } } else null,
                        suffix = if (showPrefixSuffix) { { Text("(max 10)") } } else null,
                        readOnly = isReadOnly,
                        singleLine = true,
                        maxLines = 1,
                        textStyle = if (useCompactDensity) MaterialTheme.typography.bodySmall else MaterialTheme.typography.bodyLarge
                    )

                    "outlined" -> OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        label = { Text("Full name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        ),
                        supportingText = if (showHelperText) { { Text("Ej: Gustavo Lizarraga") } } else null,
                        isError = showError,
                        leadingIcon = { Icon(Icons.Default.Person, "User") },
                        trailingIcon = if (showError) { { Icon(Icons.Default.Warning, "Error") } } else null,
                        prefix = if (showPrefixSuffix) { { Text("Sr. ") } } else null,
                        suffix = if (showPrefixSuffix) { { Text("(required)") } } else null,
                        readOnly = isReadOnly,
                        singleLine = true,
                        maxLines = 1,
                        textStyle = if (useCompactDensity) MaterialTheme.typography.bodySmall else MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = creditCardNumber,
                    onValueChange = {
                        creditCardNumber = it.replace(Regex("[^0-9]"), "").take(16)
                    },
                    label = { Text("Card number") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    visualTransformation = CreditCardTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = creditCardNumber.length < 16 && creditCardNumber.isNotEmpty(),
                    supportingText = {
                        if (creditCardNumber.isNotEmpty()) {
                            Text(
                                text = if (creditCardNumber.length < 16) "Must have 16 digits" else "Valid",
                                color = if (creditCardNumber.length < 16) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    leadingIcon = { Icon(Icons.Default.CreditCard, "Card") },
                    trailingIcon = {
                        if (creditCardNumber.isNotEmpty()) {
                            Icon(
                                imageVector = if (creditCardNumber.length == 16) Icons.Default.Check else Icons.Default.Clear,
                                contentDescription = "Validation",
                                tint = if (creditCardNumber.length == 16) Color.Green else Color.Red
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (showCharacterCounter) {
                    OutlinedTextField(
                        value = text,
                        onValueChange = { if (it.length <= 20) text = it },
                        label = { Text("Comment") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        supportingText = {
                            Text(
                                text = "${text.length}/20 characters",
                                color = if (text.length > 20) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                            )
                        },
                        isError = text.length > 20,
                        maxLines = 3
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = "Read-only information",
                    onValueChange = {},
                    label = { Text("Details") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    readOnly = true,
                    leadingIcon = { Icon(Icons.Default.Info, "Info") }
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    )
}

class CreditCardTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val formattedText = buildAnnotatedString {
            text.forEachIndexed { index, char ->
                append(char)
                if (index % 4 == 3 && index != 15) append(" ")
            }
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                var transformedOffset = offset
                for (i in 0 until offset) {
                    if (i % 4 == 3 && i != 15) transformedOffset++
                }
                return transformedOffset
            }

            override fun transformedToOriginal(offset: Int): Int {
                var originalOffset = offset
                for (i in 0 until offset) {
                    if (i % 5 == 4) originalOffset--
                }
                return originalOffset
            }
        }
        return TransformedText(formattedText, offsetMapping)
    }
}