package com.droidcon.composablebank.ui.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.droidcon.composablebank.components.InteractiveSwitch
import kotlinx.coroutines.launch
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.platform.LocalDensity
import com.droidcon.composablebank.utils.CustomTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomAppBar(navController: NavController, name: String) {
    var showSnackbar by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var showFab by remember { mutableStateOf(true) }
    var selectedItem by remember { mutableStateOf<Int?>(null) }

    val bottomBarColor by animateColorAsState(
        targetValue = if (selectedItem != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        label = "BottomAppBarColorAnimation"
    )

    val bottomAppBarHeight = 110.dp
    val fabHeight = 56.dp

    val offsetY = with(LocalDensity.current) {
        ((bottomAppBarHeight.toPx() / 2) - (fabHeight.toPx() / 2)).dp
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopAppBar(
                title = name,
                navController = navController
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = bottomBarColor,
                contentColor = if (selectedItem != null) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                tonalElevation = 8.dp,
            ) {
                if (selectedItem == null) {
                    IconButton(onClick = {  }) {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                    }
                    IconButton(onClick = {  }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = {  }) {
                        Icon(Icons.Default.Favorite, contentDescription = "Favorite")
                    }
                } else {
                    IconButton(onClick = {
                        println("Delete clicked for item #$selectedItem")
                        selectedItem = null
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                    IconButton(onClick = {
                        println("Update clicked for item #$selectedItem")
                        selectedItem = null
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Update")
                    }
                    IconButton(onClick = {
                        println("Cancel clicked")
                        selectedItem = null
                    }) {
                        Icon(Icons.Default.Close, contentDescription = "Cancel")
                    }
                }
            }
        },
        floatingActionButton = {
            if (showFab) {
                FloatingActionButton(
                    onClick = {
                        showSnackbar = true
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Floating Action Button clicked")
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .offset(y = offsetY)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Bottom App Bar Examples",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                InteractiveSwitch(
                    label = "Show Floating Action Button",
                    checked = showFab,
                    onCheckedChange = { showFab = it }
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Select an item to change the Bottom App Bar",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                repeat(50) { index ->
                    if (index % 5 == 0) {
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outlineVariant,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .clickable {
                                selectedItem = index
                            },
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedItem == index) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Item #$index",
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (selectedItem == index) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            if (selectedItem == index) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }
                }
            }
        }
    )

    if (showSnackbar) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar("Snackbar from Bottom App Bar")
            showSnackbar = false
        }
    }
}