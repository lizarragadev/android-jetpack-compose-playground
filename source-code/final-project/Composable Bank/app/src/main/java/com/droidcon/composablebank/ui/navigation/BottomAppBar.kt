package com.droidcon.composablebank.ui.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.utils.CustomTopAppBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomAppBar(navController: NavController, name: String) {
    var showSnackbar by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var showFab by remember { mutableStateOf(true) }
    var selectedItem by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { CustomTopAppBar(title = name, navController = navController) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            BottomBarContent(
                selectedItem = selectedItem,
                onActionSelected = { selectedItem = null },
                showFab = showFab,
                coroutineScope = coroutineScope,
                snackbarHostState = snackbarHostState
            )
        },
        content = { padding ->
            MainContent(
                padding = padding,
                selectedItem = selectedItem,
                onItemSelected = { selectedItem = it },
                showFab = showFab,
                onShowFabChange = { newValue -> showFab = newValue }
            )
        }
    )
    HandleSnackbar(showSnackbar, snackbarHostState) { showSnackbar = false }
}

@Composable
private fun BottomBarContent(
    selectedItem: Int?,
    onActionSelected: () -> Unit,
    showFab: Boolean,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    val containerColor = if (selectedItem != null) MaterialTheme.colorScheme.primary
    else MaterialTheme.colorScheme.surface
    val contentColor = if (selectedItem != null) MaterialTheme.colorScheme.onPrimary
    else MaterialTheme.colorScheme.onSurface

    Box(modifier = Modifier.fillMaxWidth()) {
        BottomAppBar(
            containerColor = containerColor,
            contentColor = contentColor,
            tonalElevation = 8.dp
        ) {
            if (selectedItem == null) {
                DefaultBottomBarIcons()
            } else {
                ActionBottomBarIcons(selectedItem, onActionSelected)
            }
        }

        if (showFab) {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("FAB clicked")
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    }
}

@Composable
private fun HeaderContent(
    showFab: Boolean,
    onShowFabChange: (Boolean) -> Unit
) {
    Text("Bottom App Bar Examples", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(top = 16.dp))
    Spacer(Modifier.height(16.dp))
    InteractiveSwitch(
        label = "Show FAB",
        checked = showFab,
        onCheckedChange = { newValue -> onShowFabChange(newValue) }
    )
    Text("Select an item to change the Bottom App Bar", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(16.dp))
}

@Composable
private fun MainContent(
    padding: PaddingValues,
    selectedItem: Int?,
    onItemSelected: (Int) -> Unit,
    showFab: Boolean,
    onShowFabChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderContent(showFab, onShowFabChange)
        ItemsList(selectedItem, onItemSelected)
    }
}

@Composable
private fun DefaultBottomBarIcons() {
    listOf(Icons.Default.Home, Icons.Default.Search, Icons.Default.Favorite).forEach { icon ->
        IconButton(onClick = {}) {
            Icon(icon, contentDescription = null)
        }
    }
}

@Composable
private fun ActionBottomBarIcons(selectedItem: Int, onActionSelected: () -> Unit) {
    val actions = listOf(
        Icons.Default.Delete to "Delete",
        Icons.Default.Edit to "Update",
        Icons.Default.Close to "Cancel"
    )

    actions.forEach { (icon, description) ->
        IconButton(onClick = {
            println("$description clicked for item #$selectedItem")
            onActionSelected()
        }) {
            Icon(icon, contentDescription = description)
        }
    }
}

@Composable
private fun ItemsList(selectedItem: Int?, onItemSelected: (Int) -> Unit) {
    repeat(50) { index ->
        ListItem(index, selectedItem == index) { onItemSelected(index) }
    }
}

@Composable
private fun ListItem(index: Int, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            Modifier
                .padding(16.dp)
                .heightIn(min = 48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Item #$index",
                color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.weight(1f))
            if (isSelected) Icon(
                Icons.Default.Check,
                "Selected",
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun HandleSnackbar(show: Boolean, hostState: SnackbarHostState, onDismiss: () -> Unit) {
    if (show) {
        LaunchedEffect(hostState) {
            hostState.showSnackbar("Snackbar from Bottom App Bar")
            onDismiss()
        }
    }
}