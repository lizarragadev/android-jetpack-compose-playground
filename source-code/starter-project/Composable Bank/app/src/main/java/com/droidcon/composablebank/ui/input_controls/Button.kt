package com.droidcon.composablebank.ui.input_controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.droidcon.composablebank.utils.CustomTopAppBar
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.droidcon.composablebank.components.InteractiveRadioButtonGroup
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.components.InteractiveColorPicker
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Buttons(navController: NavController, name: String) {
    var showSnackbar by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    var buttonStyle by remember { mutableStateOf("filled") }
    var showIcon by remember { mutableStateOf(true) }
    var isLoading by remember { mutableStateOf(false) }
    var buttonColor by remember { mutableStateOf(Color.Blue) }
    var useExtendedFab by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { CustomTopAppBar(title = name, navController = navController) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {

        },
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
                        options = listOf("Filled", "Tonal", "Outlined", "Text", "Elevated"),
                        selectedOption = buttonStyle,
                        onOptionSelected = { buttonStyle = it.lowercase() }
                    )
                    InteractiveSwitch(
                        label = "Show Icon",
                        checked = showIcon,
                        onCheckedChange = { showIcon = it }
                    )
                    InteractiveSwitch(
                        label = "Loading State",
                        checked = isLoading,
                        onCheckedChange = { isLoading = it }
                    )
                    InteractiveColorPicker(
                        label = "Button Color",
                        selectedColor = buttonColor,
                        onColorSelected = { buttonColor = it }
                    )
                    InteractiveSwitch(
                        label = "Extended FAB",
                        checked = useExtendedFab,
                        onCheckedChange = { useExtendedFab = it }
                    )
                }



                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Icon Button",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(8.dp)
                )


                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Music Genres",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(8.dp)
                )


                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Hobbies",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(8.dp)
                )

            }
        }
    )

    if (showSnackbar) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar("${buttonStyle.uppercase()} button clicked")
            showSnackbar = false
        }
    }
}