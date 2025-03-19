package com.droidcon.composablebank.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.droidcon.composablebank.utils.CustomTopAppBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.droidcon.composablebank.components.InteractiveSwitch
import kotlinx.coroutines.launch
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import com.droidcon.composablebank.components.InteractiveColorPicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationRail(navController: NavController, name: String) {
    var selectedItem by remember { mutableIntStateOf(0) }
    var showSnackbar by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var showBadges by remember { mutableStateOf(true) }
    var showLabels by remember { mutableStateOf(true) }
    var railColor by remember { mutableStateOf(Color.LightGray) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopAppBar(
                title = name,
                navController = navController
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { paddingValues ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalArrangement = Arrangement.Start
            ) {
                NavigationRail(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(railColor)
                        .padding(vertical = 16.dp),
                    containerColor = railColor,
                    header = {
                        FloatingActionButton(
                            onClick = {
                                showSnackbar = true
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Header FAB clicked")
                                }
                            },
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add")
                        }
                    },
                ) {
                    listOf("Home", "Search", "Favorites").forEachIndexed { index, label ->
                        NavigationRailItem(
                            selected = selectedItem == index,
                            onClick = { selectedItem = index },
                            icon = {
                                if (showBadges && index == 1) {
                                    BadgedBox(
                                        badge = {
                                            Badge(
                                                containerColor = MaterialTheme.colorScheme.error,
                                                contentColor = MaterialTheme.colorScheme.onError,
                                            ) {
                                                Text(text = if (index == 1) "99+" else "")
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = when (label) {
                                                "Home" -> Icons.Default.Home
                                                "Search" -> Icons.Default.Search
                                                "Favorites" -> Icons.Default.Favorite
                                                else -> Icons.Default.Home
                                            },
                                            contentDescription = label
                                        )
                                    }
                                } else if (showBadges && index == 2) {
                                    BadgedBox(
                                        badge = {
                                            Badge(
                                                containerColor = MaterialTheme.colorScheme.error,
                                                contentColor = MaterialTheme.colorScheme.onError,
                                            )
                                        }
                                    ) {
                                        Icon(
                                            imageVector = when (label) {
                                                "Home" -> Icons.Default.Home
                                                "Search" -> Icons.Default.Search
                                                "Favorites" -> Icons.Default.Favorite
                                                else -> Icons.Default.Home
                                            },
                                            contentDescription = label
                                        )
                                    }
                                } else {
                                    Icon(
                                        imageVector = when (label) {
                                            "Home" -> Icons.Default.Home
                                            "Search" -> Icons.Default.Search
                                            "Favorites" -> Icons.Default.Favorite
                                            else -> Icons.Default.Home
                                        },
                                        contentDescription = label
                                    )
                                }
                            },
                            label = if (showLabels) {
                                { Text(label) }
                            } else {
                                null
                            }
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (selectedItem) {
                        0 -> HomeRailScreen()
                        1 -> SearchRailScreen()
                        2 -> FavoritesRailScreen()
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    InteractiveSwitch(
                        label = "Show Badges",
                        checked = showBadges,
                        onCheckedChange = { showBadges = it }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    InteractiveSwitch(
                        label = "Show Labels",
                        checked = showLabels,
                        onCheckedChange = { showLabels = it }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    InteractiveColorPicker(
                        label = "Navigation Rail Color",
                        selectedColor = railColor,
                        onColorSelected = { railColor = it }
                    )
                }

                VerticalDivider(
                    color = MaterialTheme.colorScheme.primary,
                    thickness = 5.dp,
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    )

    if (showSnackbar) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar("Snackbar from Navigation Rail")
            showSnackbar = false
        }
    }
}

@Composable
fun HomeRailScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Home Screen",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Welcome to the Home section!",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun SearchRailScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Search Screen",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Search for your favorite items here.",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun FavoritesRailScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Favorites Screen",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "View your favorite items here.",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}