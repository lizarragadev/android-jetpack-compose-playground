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
        topBar = { CustomTopAppBar(title = name, navController = navController) },
        content = { paddingValues ->
            MainContent(
                paddingValues = paddingValues,
                snackbarMessage = snackbarMessage,
                actionLabel = actionLabel,
                containerColor = containerColor,
                contentColor = contentColor,
                actionColor = actionColor,
                onMessageChange = { snackbarMessage = it },
                onActionLabelChange = { actionLabel = it },
                onContainerColorChange = { containerColor = it },
                onContentColorChange = { contentColor = it },
                onActionColorChange = { actionColor = it },
                onShowCustomSnackbar = {
                    coroutineScope.launch {
                        customSnackbarHostState.showSnackbar(
                            message = snackbarMessage,
                            actionLabel = if (actionLabel.isNotEmpty()) actionLabel else null,
                            duration = SnackbarDuration.Indefinite
                        )
                    }
                },
                onShowGenericSnackbar = { message, action, duration ->
                    coroutineScope.launch {
                        genericSnackbarHostState.showSnackbar(
                            message = message,
                            actionLabel = action,
                            duration = duration
                        )
                    }
                }
            )
        }
    )

    SnackbarHostsSection(
        customSnackbarHostState = customSnackbarHostState,
        genericSnackbarHostState = genericSnackbarHostState,
        containerColor = containerColor,
        contentColor = contentColor,
        actionColor = actionColor,
        actionLabel = actionLabel,
        snackbarMessage = snackbarMessage
    )
}

@Composable
private fun MainContent(
    paddingValues: PaddingValues,
    snackbarMessage: String,
    actionLabel: String,
    containerColor: Color,
    contentColor: Color,
    actionColor: Color,
    onMessageChange: (String) -> Unit,
    onActionLabelChange: (String) -> Unit,
    onContainerColorChange: (Color) -> Unit,
    onContentColorChange: (Color) -> Unit,
    onActionColorChange: (Color) -> Unit,
    onShowCustomSnackbar: () -> Unit,
    onShowGenericSnackbar: (String, String?, SnackbarDuration) -> Unit
) {
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
            ConfigurationSection(
                snackbarMessage = snackbarMessage,
                actionLabel = actionLabel,
                containerColor = containerColor,
                contentColor = contentColor,
                actionColor = actionColor,
                onMessageChange = onMessageChange,
                onActionLabelChange = onActionLabelChange,
                onContainerColorChange = onContainerColorChange,
                onContentColorChange = onContentColorChange,
                onActionColorChange = onActionColorChange,
                onShowCustomSnackbar = onShowCustomSnackbar
            )

            DividerSection()

            PredefinedExamplesSection(
                onShowGenericSnackbar = onShowGenericSnackbar
            )
        }
    }
}

@Composable
private fun ConfigurationSection(
    snackbarMessage: String,
    actionLabel: String,
    containerColor: Color,
    contentColor: Color,
    actionColor: Color,
    onMessageChange: (String) -> Unit,
    onActionLabelChange: (String) -> Unit,
    onContainerColorChange: (Color) -> Unit,
    onContentColorChange: (Color) -> Unit,
    onActionColorChange: (Color) -> Unit,
    onShowCustomSnackbar: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Snackbar Configuration",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        InteractiveTextField(
            value = snackbarMessage,
            onValueChange = onMessageChange,
            label = "Message"
        )
        Spacer(modifier = Modifier.height(8.dp))

        InteractiveTextField(
            value = actionLabel,
            onValueChange = onActionLabelChange,
            label = "Action Label"
        )

        ColorSelectionSection(
            containerColor = containerColor,
            contentColor = contentColor,
            actionColor = actionColor,
            onContainerColorChange = onContainerColorChange,
            onContentColorChange = onContentColorChange,
            onActionColorChange = onActionColorChange
        )

        InteractiveButton(
            text = "Show Custom Snackbar",
            onClick = onShowCustomSnackbar,
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun ColorSelectionSection(
    containerColor: Color,
    contentColor: Color,
    actionColor: Color,
    onContainerColorChange: (Color) -> Unit,
    onContentColorChange: (Color) -> Unit,
    onActionColorChange: (Color) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        InteractiveColorPicker(
            label = "Container Color",
            selectedColor = containerColor,
            onColorSelected = onContainerColorChange
        )
        InteractiveColorPicker(
            label = "Content Color",
            selectedColor = contentColor,
            onColorSelected = onContentColorChange
        )
        InteractiveColorPicker(
            label = "Action Color",
            selectedColor = actionColor,
            onColorSelected = onActionColorChange
        )
    }
}

@Composable
private fun DividerSection() {
    HorizontalDivider(
        color = MaterialTheme.colorScheme.outlineVariant,
        thickness = 1.dp,
        modifier = Modifier.padding(vertical = 16.dp)
    )
}

@Composable
private fun PredefinedExamplesSection(
    onShowGenericSnackbar: (String, String?, SnackbarDuration) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Predefined Examples",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        val examples = listOf(
            Triple(
                "Show Single-Line Snackbar, short duration",
                "This is a single-line Snackbar.",
                SnackbarDuration.Short to null
            ),
            Triple(
                "Show Two-Line Snackbar, long duration",
                "This is a two-line Snackbar. It can display more information in a compact space.",
                SnackbarDuration.Long to null
            ),
            Triple(
                "Show Single-Line Snackbar with Action",
                "Single-line Snackbar with action.",
                SnackbarDuration.Short to "UNDO"
            ),
            Triple(
                "Show Two-Line Snackbar with Action",
                "Two-line Snackbar with an action. This demonstrates how actions wrap.",
                SnackbarDuration.Short to "RETRY"
            )
        )

        examples.forEach { (buttonText, message, config) ->
            SnackbarExampleButton(
                buttonText = buttonText,
                message = message,
                action = config.second,
                duration = config.first,
                onShowSnackbar = onShowGenericSnackbar
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun SnackbarExampleButton(
    buttonText: String,
    message: String,
    action: String?,
    duration: SnackbarDuration,
    onShowSnackbar: (String, String?, SnackbarDuration) -> Unit
) {
    InteractiveButton(
        text = buttonText,
        onClick = { onShowSnackbar(message, action, duration) },
        backgroundColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun SnackbarHostsSection(
    customSnackbarHostState: SnackbarHostState,
    genericSnackbarHostState: SnackbarHostState,
    containerColor: Color,
    contentColor: Color,
    actionColor: Color,
    actionLabel: String,
    snackbarMessage: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 70.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        CustomSnackbarHost(
            hostState = customSnackbarHostState,
            containerColor = containerColor,
            contentColor = contentColor,
            actionColor = actionColor,
            actionLabel = actionLabel,
            snackbarMessage = snackbarMessage
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        GenericSnackbarHost(
            hostState = genericSnackbarHostState,
            containerColor = containerColor,
            contentColor = contentColor,
            actionColor = actionColor
        )
    }
}

@Composable
private fun CustomSnackbarHost(
    hostState: SnackbarHostState,
    containerColor: Color,
    contentColor: Color,
    actionColor: Color,
    actionLabel: String,
    snackbarMessage: String
) {
    SnackbarHost(hostState = hostState) { data ->
        Snackbar(
            modifier = Modifier.padding(16.dp),
            action = {
                if (actionLabel.isNotEmpty()) {
                    InteractiveButton(
                        text = actionLabel,
                        onClick = { hostState.currentSnackbarData?.dismiss() },
                        backgroundColor = actionColor,
                        contentColor = containerColor,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            },
            containerColor = containerColor,
            contentColor = contentColor,
            shape = CutCornerShape(10.dp)
        ) {
            Text(text = snackbarMessage)
        }
    }
}

@Composable
private fun GenericSnackbarHost(
    hostState: SnackbarHostState,
    containerColor: Color,
    contentColor: Color,
    actionColor: Color
) {
    SnackbarHost(hostState = hostState) { data ->
        Snackbar(
            modifier = Modifier.padding(16.dp),
            action = {
                if (data.visuals.actionLabel != null) {
                    TextButton(
                        onClick = {
                            handleSnackbarAction(data.visuals.actionLabel.toString())
                            hostState.currentSnackbarData?.dismiss()
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

enum class SnackbarAction { RETRY, UNDO, DISMISS }

fun handleSnackbarAction(actionName: String) {
    when (SnackbarAction.valueOf(actionName)) {
        SnackbarAction.RETRY -> println("Retry Action Executed")
        SnackbarAction.UNDO -> println("Undo Action Executed")
        SnackbarAction.DISMISS -> println("Dismiss action clicked")
    }
}