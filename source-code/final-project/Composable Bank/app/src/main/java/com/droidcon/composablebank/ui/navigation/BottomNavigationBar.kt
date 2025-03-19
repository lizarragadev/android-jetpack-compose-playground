package com.droidcon.composablebank.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import com.droidcon.composablebank.components.InteractiveColorPicker
import com.droidcon.composablebank.utils.CustomTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(navController: NavController, name: String) {
    var showSnackbar by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var selectedItem by remember { mutableIntStateOf(0) }
    var useScrollBehavior by remember { mutableStateOf(true) }
    var showFab by remember { mutableStateOf(true) }
    var showBadges by remember { mutableStateOf(true) }
    var navigationBarColor by remember { mutableStateOf(Color.Gray) }

    var isNavigationBarVisible by remember { mutableStateOf(true) }

    val scrollState = rememberScrollState()

    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.value }.collect { scrollValue ->
            isNavigationBarVisible = scrollValue == 0 || !useScrollBehavior
        }
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
            AnimatedVisibility(
                visible = isNavigationBarVisible,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                NavigationBar(
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = navigationBarColor,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    tonalElevation = 8.dp
                ) {
                    listOf("Home", "Search", "Favorites").forEachIndexed { index, label ->
                        NavigationBarItem(
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                println("Selected item: $label")
                            },
                            icon = {
                                if (showBadges && index == 1) {
                                    BadgedBox(
                                        badge = {
                                            Badge(
                                                containerColor = MaterialTheme.colorScheme.error,
                                                contentColor = MaterialTheme.colorScheme.onError
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
                            label = { Text(label) }
                        )
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
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (selectedItem) {
                    0 -> HomeScreen()
                    1 -> SearchScreen()
                    2 -> FavoritesScreen()
                }

                Spacer(modifier = Modifier.height(16.dp))

                InteractiveSwitch(
                    label = "Enable Scroll Behavior",
                    checked = useScrollBehavior,
                    onCheckedChange = { useScrollBehavior = it }
                )
                Spacer(modifier = Modifier.height(8.dp))

                InteractiveSwitch(
                    label = "Show Floating Action Button",
                    checked = showFab,
                    onCheckedChange = { showFab = it }
                )
                Spacer(modifier = Modifier.height(8.dp))

                InteractiveSwitch(
                    label = "Show Badges",
                    checked = showBadges,
                    onCheckedChange = { showBadges = it }
                )
                Spacer(modifier = Modifier.height(8.dp))

                InteractiveColorPicker(
                    label = "Navigation Bar Color",
                    selectedColor = navigationBarColor,
                    onColorSelected = { navigationBarColor = it }
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Scroll to see the behavior of the Bottom Navigation Bar",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                repeat(50) {
                    Text(
                        text = "Item #$it",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    )

    if (showSnackbar) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar("Snackbar from Bottom Navigation Bar")
            showSnackbar = false
        }
    }
}
@Composable
fun HomeScreen() {
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
fun SearchScreen() {
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
fun FavoritesScreen() {
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