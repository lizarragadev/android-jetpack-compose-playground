package com.droidcon.composablebank.ui.basic_comp_layouts

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.droidcon.composablebank.components.*
import com.droidcon.composablebank.utils.CustomTopAppBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ScaffoldContainer(navController: NavController, name: String) {
    var selectedExample by remember { mutableStateOf("Basic Scaffold") }
    val examples = listOf(
        "Basic Scaffold",
        "Dynamic Scaffold",
        "Drawer Scaffold",
        "Advanced Scaffold"
    )

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { CustomTopAppBar(title = name, navController = navController) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        )
                {
                    InteractiveDropdown(
                        options = examples,
                        selectedOption = selectedExample,
                        onOptionSelected = { selectedExample = it },
                        label = "Select Scaffold Type"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    when(selectedExample) {
                        "Basic Scaffold" -> BasicScaffoldExample(context, snackbarHostState, scope)
                        "Dynamic Scaffold" -> DynamicScaffoldExample()
                        "Drawer Scaffold" -> DrawerScaffoldExample(
                            scope = scope
                        )
                        "Advanced Scaffold" -> AdvancedScaffoldExample(
                            snackbarHostState = snackbarHostState,
                            scope = scope
                        )
                    }
                }

    }
}

@Composable
private fun BasicScaffoldExample(
    context: Context,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Home", "Profile", "Settings")

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("FAB Clicked!")
                    }
                },
                icon = { Icon(Icons.Default.Add, "FAB") },
                text = { Text("Action") }
            )
        },
        bottomBar = {
            NavigationBar {
                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        icon = {
                            when(index) {
                                0 -> Icon(Icons.Default.Home, title)
                                1 -> Icon(Icons.Default.Person, title)
                                2 -> Icon(Icons.Default.Settings, title)
                            }
                        },
                        label = { Text(title) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                InteractiveButton(
                    text = "Show Toast",
                    onClick = { InteractiveToast(context, "Basic Scaffold Demo") }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DynamicScaffoldExample() {
    var fabPosition by remember { mutableStateOf(FabPosition.End) }
    var isDarkTheme by remember { mutableStateOf(false) }
    var appBarColor by remember { mutableStateOf(Color.Blue) }
    var bottomBarHeight by remember { mutableFloatStateOf(56f) }

    val colorScheme = if(isDarkTheme) darkColorScheme() else lightColorScheme()

    Scaffold(
        containerColor = colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Dynamic Scaffold") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = appBarColor
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                containerColor = colorScheme.primary
            ) {
                Icon(Icons.Default.Edit, "Dynamic FAB")
            }
        },
        floatingActionButtonPosition = fabPosition,
        bottomBar = {
            Surface(
                modifier = Modifier.height(bottomBarHeight.dp),
                color = colorScheme.surfaceVariant
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text("Custom Bottom Bar", modifier = Modifier.padding(16.dp))
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            InteractiveRadioButtonGroup(
                options = listOf("Start", "Center", "End"),
                selectedOption = fabPosition.toString().split(".").last(),
                onOptionSelected = {
                    fabPosition = when(it) {
                        "Start" -> FabPosition.Start
                        "Center" -> FabPosition.Center
                        else -> FabPosition.End
                    }
                }
            )

            InteractiveSwitch(
                label = "Dark Theme",
                checked = isDarkTheme,
                onCheckedChange = { isDarkTheme = it }
            )

            InteractiveSlider(
                label = "Bottom Bar Height: ${bottomBarHeight.toInt()}dp",
                value = bottomBarHeight,
                onValueChange = { bottomBarHeight = it },
                valueRange = 48f..450f
            )

            InteractiveColorPicker(
                label = "AppBar Color",
                selectedColor = appBarColor,
                onColorSelected = { appBarColor = it }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawerScaffoldExample(scope: CoroutineScope) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var selectedItem by remember { mutableStateOf("Home") }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Navigation Menu", modifier = Modifier.padding(16.dp))
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text("Home") },
                    selected = selectedItem == "Home",
                    onClick = {
                        selectedItem = "Home"
                        scope.launch { drawerState.close() }
                              },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) }
                )
                NavigationDrawerItem(
                    label = { Text("Settings") },
                    selected = selectedItem == "Settings",
                    onClick = {
                        selectedItem = "Settings"
                        scope.launch { drawerState.close() }
                              },
                    icon = { Icon(Icons.Default.Settings, contentDescription = null) }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Drawer Example") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, "Open Drawer")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Current Selection: $selectedItem")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AdvancedScaffoldExample(
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    var selectedItem by remember { mutableStateOf("Home") }
    var isFabExpanded by remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.width(240.dp)) {
                Column {
                    Text("Advanced Menu",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(16.dp))

                    listOf("Home", "Settings", "Profile", "Admin").forEach { item ->
                        NavigationDrawerItem(
                            label = { Text(item) },
                            selected = selectedItem == item,
                            onClick = {
                                selectedItem = item
                                scope.launch { drawerState.close() }
                            },
                            icon = {
                                when(item) {
                                    "Home" -> Icon(Icons.Default.Home, null)
                                    "Settings" -> Icon(Icons.Default.Settings, null)
                                    "Profile" -> Icon(Icons.Default.Person, null)
                                    "Admin" -> Icon(Icons.Default.Security, null)
                                }
                            },
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    InteractiveSwitch(
                        label = "Advanced Mode",
                        checked = isFabExpanded,
                        onCheckedChange = { isFabExpanded = it },
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopAppBar(
                    title = { Text("Advanced Demo") },
                    navigationIcon = {
                        IconButton(
                            onClick = { scope.launch { drawerState.apply {
                                if (isClosed) open() else close()
                            } } }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Navigation Menu"
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    AnimatedVisibility(
                        visible = isFabExpanded,
                        enter = fadeIn() + expandVertically(expandFrom = Alignment.Top),
                        exit = fadeOut() + shrinkVertically(shrinkTowards = Alignment.Top)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(bottom = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            FloatingActionButton(
                                onClick = {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Shared!")
                                    }
                                },
                                modifier = Modifier.size(48.dp)
                            ) {
                                Icon(Icons.Default.Share, "Share")
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            FloatingActionButton(
                                onClick = {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Added to favorites!")
                                    }
                                },
                                modifier = Modifier.size(48.dp)
                            ) {
                                Icon(Icons.Default.Favorite, "Favorite")
                            }
                        }
                    }

                    FloatingActionButton(
                        onClick = { isFabExpanded = !isFabExpanded },
                        modifier = Modifier
                            .graphicsLayer {
                                rotationZ = if (isFabExpanded) 180f else 0f
                                transformOrigin = TransformOrigin.Center
                            }
                    ) {
                        Icon(
                            imageVector = if (isFabExpanded) Icons.Default.Close else Icons.Default.ArrowDropUp,
                            contentDescription = "Main Action"
                        )
                    }
                }
            },
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, "Home") },
                        label = { Text("Home") },
                        selected = selectedItem == "Home",
                        onClick = { selectedItem = "Home" }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Settings, "Settings") },
                        label = { Text("Settings") },
                        selected = selectedItem == "Settings",
                        onClick = { selectedItem = "Settings" }
                    )
                }
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(20) { index ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = MaterialTheme.shapes.medium,
                            tonalElevation = 4.dp
                        ) {
                            ListItem(
                                headlineContent = { Text("Item ${index + 1}") },
                                leadingContent = {
                                    Icon(Icons.Default.Favorite, "Favorite")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}