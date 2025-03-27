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
import androidx.compose.ui.graphics.vector.ImageVector
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
        topBar = { CustomTopAppBar(title = name, navController = navController) },
        content = { paddingValues ->
            MainDialogContent(
                paddingValues = paddingValues,
                name = name,
                onShowSimple = { showSimpleDialog = true },
                onShowList = { showListDialog = true },
                onShowCustom = { showCustomDialog = true },
                onShowFullScreen = { showFullScreenDialog = true }
            )

            if (showSimpleDialog) SimpleAlertDialog(onDismiss = { showSimpleDialog = false })
            if (showListDialog) ListAlertDialog(
                selectedItem = selectedItem,
                onSelect = { selectedItem = it },
                onConfirm = {
                    InteractiveToast(context = context, text = "Selected: $selectedItem")
                    showListDialog = false
                },
                onDismiss = { showListDialog = false }
            )
            if (showCustomDialog) CustomAlertDialog(
                inputText = inputText,
                onInputChange = { inputText = it },
                onDismiss = { showCustomDialog = false }
            )
            if (showFullScreenDialog) FullScreenDialog(onDismiss = { showFullScreenDialog = false })
        }
    )
}

@Composable
private fun MainDialogContent(
    paddingValues: PaddingValues,
    name: String,
    onShowSimple: () -> Unit,
    onShowList: () -> Unit,
    onShowCustom: () -> Unit,
    onShowFullScreen: () -> Unit
) {
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
            DialogSectionTitle(name = name)
            DialogButton("Show Simple Alert Dialog", onShowSimple)
            DialogButton("Show List Dialog", onShowList)
            DialogButton("Show Custom Alert Dialog", onShowCustom)
            DialogButton("Show Full-Screen Dialog", onShowFullScreen)
        }
    }
}

@Composable
private fun DialogSectionTitle(name: String) {
    Text(
        text = "$name Examples",
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@Composable
private fun DialogButton(text: String, onClick: () -> Unit) {
    InteractiveButton(
        text = text,
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    )
}

@Composable
private fun SimpleAlertDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Simple Alert Dialog") },
        text = { Text("This is a simple alert dialog with a message.") },
        confirmButton = { DialogActionButton("Confirm", onDismiss) },
        dismissButton = { DialogActionButton("Cancel", onDismiss) }
    )
}

@Composable
private fun ListAlertDialog(
    selectedItem: String?,
    onSelect: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val items = List(10) { "Item ${it + 1}" }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select an Item") },
        text = {
            ScrollableListContent(items = items, selectedItem = selectedItem, onSelect = onSelect)
        },
        confirmButton = { DialogActionButton("Confirm", onConfirm) },
        dismissButton = { DialogActionButton("Cancel", onDismiss) }
    )
}

@Composable
private fun ScrollableListContent(
    items: List<String>,
    selectedItem: String?,
    onSelect: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .verticalScroll(rememberScrollState())
    ) {
        items.forEach { item ->
            ListItemRow(item = item, selected = selectedItem == item, onSelect = onSelect)
        }
    }
}

@Composable
private fun ListItemRow(item: String, selected: Boolean, onSelect: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect(item) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = { onSelect(item) })
        Text(
            text = item,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
private fun CustomAlertDialog(
    inputText: String,
    onInputChange: (String) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Custom Alert Dialog") },
        text = { CustomDialogContent(inputText = inputText, onInputChange = onInputChange) },
        confirmButton = {
            DialogActionButton("OK") {
                onDismiss()
            }
        },
        icon = { DialogIcon(Icons.Default.Save) },
        modifier = Modifier.width(400.dp)
    )
}

@Composable
private fun CustomDialogContent(inputText: String, onInputChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Text("This is a custom dialog with additional content.")
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = inputText,
            onValueChange = onInputChange,
            label = { Text("Enter your name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.droidcon),
            contentDescription = "Dialog Image",
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun FullScreenDialog(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        FullScreenDialogContent(onDismiss = onDismiss)
    }
}

@Composable
private fun FullScreenDialogContent(onDismiss: () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { FullScreenDialogTopBar(onDismiss = onDismiss) },
        content = { paddingValues ->
            ScrollableDialogContent(paddingValues = paddingValues)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FullScreenDialogTopBar(onDismiss: () -> Unit) {
    TopAppBar(
        title = { Text("Full-Screen Dialog", style = MaterialTheme.typography.titleLarge) },
        navigationIcon = {
            IconButton(onClick = onDismiss) {
                Icon(Icons.Default.Close, contentDescription = "Close")
            }
        },
        actions = { DialogActionButton("Save", onDismiss) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

@Composable
private fun ScrollableDialogContent(paddingValues: PaddingValues) {
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

@Composable
private fun DialogActionButton(text: String, onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(text = text, color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun DialogIcon(icon: ImageVector) {
    Icon(
        imageVector = icon,
        contentDescription = "Dialog Icon",
        tint = MaterialTheme.colorScheme.primary
    )
}