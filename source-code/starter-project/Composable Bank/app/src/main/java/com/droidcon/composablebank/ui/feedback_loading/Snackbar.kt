package com.droidcon.composablebank.ui.feedback_loading

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
    var actionLabel by remember { mutableStateOf("Action") }
    var containerColor by remember { mutableStateOf(Color(0xFF323232)) }
    var contentColor by remember { mutableStateOf(Color.White) }
    var actionColor by remember { mutableStateOf(Color.Cyan) }

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


    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {


    }
}

