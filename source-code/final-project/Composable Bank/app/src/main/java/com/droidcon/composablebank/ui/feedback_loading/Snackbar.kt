package com.droidcon.composablebank.ui.feedback_loading

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.droidcon.composablebank.components.InteractiveButton
import com.droidcon.composablebank.components.InteractiveColorPicker
import com.droidcon.composablebank.components.InteractiveTextField
import com.droidcon.composablebank.utils.CustomTopAppBar
import kotlinx.coroutines.launch

@Composable
fun Snackbar(navController: NavController, name: String) {
    var snackbarMessage by remember { mutableStateOf("Default message") }
    var actionLabel by remember { mutableStateOf("Action") }
    var containerColor by remember { mutableStateOf(Color(0xFF323232)) }
    var contentColor by remember { mutableStateOf(Color.White) }
    var actionColor by remember { mutableStateOf(Color.Cyan) }

    val customSnackbarHostState = remember { SnackbarHostState() }
    val genericSnackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = name,
                navController = navController
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$name Examples",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    InteractiveTextField(
                        value = snackbarMessage,
                        onValueChange = { newValue -> snackbarMessage = newValue },
                        label = "Message"
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    InteractiveTextField(
                        value = actionLabel,
                        onValueChange = { newValue -> actionLabel = newValue },
                        label = "Action Label"
                    )

                    InteractiveColorPicker(
                        label = "Container Color",
                        selectedColor = containerColor,
                        onColorSelected = { containerColor = it }
                    )
                    InteractiveColorPicker(
                        label = "Content Color",
                        selectedColor = contentColor,
                        onColorSelected = { contentColor = it }
                    )

                    InteractiveColorPicker(
                        label = "Action Color",
                        selectedColor = actionColor,
                        onColorSelected = { actionColor = it }
                    )

                    InteractiveButton(
                        text = "Show Custom Snackbar",
                        onClick = {
                            coroutineScope.launch {
                                customSnackbarHostState.showSnackbar(
                                    message = snackbarMessage,
                                    actionLabel = if (actionLabel.isNotEmpty()) actionLabel else null,
                                    duration = SnackbarDuration.Indefinite
                                )
                            }
                        },
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.fillMaxWidth()
                    )

                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.outlineVariant,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )

                    InteractiveButton(
                        text = "Show Single-Line Snackbar, short duration",
                        onClick = {
                            coroutineScope.launch {
                                genericSnackbarHostState.showSnackbar(
                                    message = "This is a single-line Snackbar.",
                                    actionLabel = null,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        },
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    InteractiveButton(
                        text = "Show Two-Line Snackbar, long duration",
                        onClick = {
                            coroutineScope.launch {
                                genericSnackbarHostState.showSnackbar(
                                    message = "This is a two-line Snackbar. It can display more information in a compact space.",
                                    actionLabel = null,
                                    duration = SnackbarDuration.Long
                                )
                            }
                        },
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    InteractiveButton(
                        text = "Show Single-Line Snackbar with Action",
                        onClick = {
                            coroutineScope.launch {
                                genericSnackbarHostState.showSnackbar(
                                    message = "Single-line Snackbar with action.",
                                    actionLabel = "UNDO",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        },
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    InteractiveButton(
                        text = "Show Two-Line Snackbar with Action",
                        onClick = {
                            coroutineScope.launch {
                                genericSnackbarHostState.showSnackbar(
                                    message = "Two-line Snackbar with an action. This demonstrates how actions wrap.",
                                    actionLabel = "RETRY",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        },
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 70.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        SnackbarHost(hostState = customSnackbarHostState) { data ->
            Snackbar(
                modifier = Modifier.padding(16.dp),
                action = {
                    if (actionLabel.isNotEmpty()) {
                        InteractiveButton(
                            text = actionLabel,
                            onClick = { customSnackbarHostState.currentSnackbarData?.dismiss() },
                            backgroundColor = actionColor,
                            contentColor = containerColor,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                },
                containerColor = containerColor,
                contentColor = contentColor,
                shape = CutCornerShape(topStart = 10.dp, topEnd = 10.dp, bottomEnd = 10.dp, bottomStart = 10.dp)
            ) {
                Text(text = snackbarMessage)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        SnackbarHost(hostState = genericSnackbarHostState) { data ->
            Snackbar(
                modifier = Modifier.padding(16.dp),
                action = {
                    if (data.visuals.actionLabel != null) {
                        TextButton(
                            onClick = {
                                handleSnackbarAction(data.visuals.actionLabel.toString())
                                genericSnackbarHostState.currentSnackbarData?.dismiss()
                            },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = actionColor
                            ),
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text(
                                text = data.visuals.actionLabel.toString(),
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                },
                containerColor = containerColor,
                contentColor = contentColor,
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(text = data.visuals.message)
            }
        }
    }
}

enum class SnackbarAction {
    RETRY,
    UNDO,
    DISMISS
}

fun handleSnackbarAction(actionName: String) {
    val action = SnackbarAction.valueOf(actionName)
    when (action) {
        SnackbarAction.RETRY -> {
            println("Retry Action Executed")
        }
        SnackbarAction.UNDO -> {
            println("Undo Action Executed")
        }
        SnackbarAction.DISMISS -> {
            println("Dismiss action clicked")
        }
        else -> {
            println("Unknown action: $actionName")
        }
    }
}