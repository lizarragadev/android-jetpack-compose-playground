package com.droidcon.composablebank.ui.feedback_loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.ui.window.Dialog
import androidx.compose.material.icons.filled.Close
import com.droidcon.composablebank.components.InteractiveButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import androidx.compose.foundation.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.droidcon.composablebank.R
import com.droidcon.composablebank.components.InteractiveToast
import com.droidcon.composablebank.utils.CustomTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialog(navController: NavController, name: String) {
    var showSimpleDialog by remember { mutableStateOf(false) }
    var showListDialog by remember { mutableStateOf(false) }
    var showCustomDialog by remember { mutableStateOf(false) }
    var showFullScreenDialog by remember { mutableStateOf(false) }

    var inputText by remember { mutableStateOf("") }
    var selectedItem by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

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
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$name Examples",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    InteractiveButton(
                        text = "Show Simple Alert Dialog",
                        onClick = { showSimpleDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    InteractiveButton(
                        text = "Show List Dialog",
                        onClick = { showListDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    InteractiveButton(
                        text = "Show Custom Alert Dialog",
                        onClick = { showCustomDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    InteractiveButton(
                        text = "Show Full-Screen Dialog",
                        onClick = { showFullScreenDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                if (showSimpleDialog) {
                    AlertDialog(
                        onDismissRequest = { showSimpleDialog = false },
                        title = { Text("Simple Alert Dialog") },
                        text = { Text("This is a simple alert dialog with a message.") },
                        confirmButton = {
                            TextButton(onClick = { showSimpleDialog = false }) {
                                Text("Confirm")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showSimpleDialog = false }) {
                                Text("Cancel")
                            }
                        }
                    )
                }

                if (showListDialog) {
                    val items = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9", "Item 10")

                    AlertDialog(
                        onDismissRequest = { showListDialog = false },
                        title = { Text("Select an Item") },
                        text = {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                items.forEach { item ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                selectedItem = item
                                            },
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = selectedItem == item,
                                            onClick = { selectedItem = item }
                                        )
                                        Text(
                                            text = item,
                                            style = MaterialTheme.typography.bodyMedium,
                                            modifier = Modifier.padding(start = 8.dp)
                                        )
                                    }
                                }
                            }
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                println("Selected item: $selectedItem")
                                InteractiveToast(context = context, text = "Selected: $selectedItem")
                                showListDialog = false
                            }) {
                                Text("Confirm")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showListDialog = false }) {
                                Text("Cancel")
                            }
                        }
                    )
                }

                if (showCustomDialog) {

                    AlertDialog(
                        onDismissRequest = { showCustomDialog = false },
                        title = { Text("Custom Alert Dialog") },
                        text = {
                            Column {
                                Text("This is a custom dialog with additional content.")
                                Spacer(modifier = Modifier.height(8.dp))
                                TextField(
                                    value = inputText,
                                    onValueChange = { inputText = it },
                                    label = { Text("Enter your name") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Image(
                                    painter = painterResource(id = R.drawable.droidcon),
                                    contentDescription = "Dialog Image",
                                    modifier = Modifier.size(150.dp).align(Alignment.CenterHorizontally)
                                )
                            }
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                println("User entered: $inputText")
                                showCustomDialog = false }) {
                                Text("OK")
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Save,
                                contentDescription = "Info Icon",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        modifier = Modifier.height(500.dp).width(200.dp)
                    )
                }

                if (showFullScreenDialog) {
                    Dialog(
                        onDismissRequest = { showFullScreenDialog = false },
                        properties = DialogProperties(usePlatformDefaultWidth = false)
                    ) {
                        Scaffold(
                            modifier = Modifier.fillMaxSize(),
                            topBar = {
                                TopAppBar(
                                    title = {
                                        Text(
                                            text = "Full-Screen Dialog",
                                            style = MaterialTheme.typography.titleLarge,

                                            )
                                    },
                                    navigationIcon = {
                                        IconButton(onClick = { showFullScreenDialog = false }) {
                                            Icon(Icons.Default.Close, contentDescription = "Close")
                                        }
                                    },
                                    actions = {
                                        TextButton(onClick = { showFullScreenDialog = false }) {
                                            Text("Save", color = MaterialTheme.colorScheme.primary)
                                        }
                                    },
                                    colors = TopAppBarDefaults.topAppBarColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                        actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                )
                            },
                            content = { paddingValues ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(paddingValues)
                                        .verticalScroll(rememberScrollState())
                                        .background(MaterialTheme.colorScheme.background),
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    Text(
                                        text = "This is the content of the full-screen dialog.",
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                    repeat(10) {
                                        Text(
                                            text = "Scrollable item #$it",
                                            style = MaterialTheme.typography.bodyMedium,
                                            modifier = Modifier.padding(16.dp)
                                        )
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    )
}