package com.droidcon.composablebank.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavController
import com.droidcon.composablebank.components.InteractiveRadioButtonGroup
import com.droidcon.composablebank.components.InteractiveSwitch
import androidx.compose.material.icons.automirrored.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(navController: NavController, name: String) {
    var showMenu by remember { mutableStateOf(false) }
    var appBarType by remember { mutableStateOf("centeraligned") }
    var useScrollBehavior by remember { mutableStateOf(true) }

    val scrollBehavior = when (appBarType) {
        "centeraligned" -> if (useScrollBehavior) {
            TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
        } else {
            TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
        }
        else -> if (useScrollBehavior) {
            TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
        } else {
            TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            when (appBarType) {
                "centeraligned" -> {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = "Center Aligned",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.Black
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {  }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.Black)
                            }
                        },
                        actions = {
                            IconButton(onClick = {  }) {
                                Icon(Icons.Default.PersonPin, contentDescription = "Profile", tint = Color.Black)
                            }
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color.LightGray,
                            actionIconContentColor = Color.Black,
                            navigationIconContentColor = Color.Black,
                            scrolledContainerColor = Color.LightGray,
                        ),
                        scrollBehavior = scrollBehavior
                    )
                }
                "small" -> {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Small TopAppBar",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                            }
                        },
                        actions = {
                            IconButton(onClick = {  }) {
                                Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                            }
                            IconButton(onClick = {  }) {
                                Icon(Icons.Default.Favorite, contentDescription = "Favorite", tint = Color.White)
                            }
                            IconButton(onClick = { showMenu = true }) {
                                Icon(Icons.Default.MoreVert, contentDescription = "More", tint = Color.White)
                            }
                            DropdownMenu(
                                expanded = showMenu,
                                onDismissRequest = { showMenu = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Action 1") },
                                    onClick = {  }
                                )
                                DropdownMenuItem(
                                    text = { Text("Action 2") },
                                    onClick = {  }
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Blue,
                            titleContentColor = Color.White,
                            actionIconContentColor = Color.White,
                            navigationIconContentColor = Color.White,
                            scrolledContainerColor = Color.Blue
                        ),
                        scrollBehavior = scrollBehavior,
                    )
                }
                "medium" -> {
                    MediumTopAppBar(
                        title = {
                            Text(
                                text = "Medium TopAppBar",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                            }
                        },
                        actions = {
                            IconButton(onClick = {  }) {
                                Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                            }
                            IconButton(onClick = {  }) {
                                Icon(Icons.Default.Favorite, contentDescription = "Favorite", tint = Color.White)
                            }
                            IconButton(onClick = { showMenu = true }) {
                                Icon(Icons.Default.MoreVert, contentDescription = "More", tint = Color.White)
                            }
                            DropdownMenu(
                                expanded = showMenu,
                                onDismissRequest = { showMenu = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Action 1") },
                                    onClick = {  }
                                )
                                DropdownMenuItem(
                                    text = { Text("Action 2") },
                                    onClick = {  }
                                )
                            }
                        },
                        colors = TopAppBarDefaults.mediumTopAppBarColors(
                            containerColor = Color.Green,
                            titleContentColor = Color.White,
                            actionIconContentColor = Color.White,
                            navigationIconContentColor = Color.White,
                            scrolledContainerColor = Color.Green
                        ),
                        scrollBehavior = scrollBehavior
                    )
                }
                "large" -> {
                    LargeTopAppBar(
                        title = {
                            Text(
                                text = "Large TopAppBar",
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.White
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                            }
                        },
                        actions = {
                            IconButton(onClick = {  }) {
                                Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                            }
                            IconButton(onClick = {  }) {
                                Icon(Icons.Default.Favorite, contentDescription = "Favorite", tint = Color.White)
                            }
                            IconButton(onClick = { showMenu = true }) {
                                Icon(Icons.Default.MoreVert, contentDescription = "More", tint = Color.White)
                            }
                            DropdownMenu(
                                expanded = showMenu,
                                onDismissRequest = { showMenu = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Action 1") },
                                    onClick = {  }
                                )
                                DropdownMenuItem(
                                    text = { Text("Action 2") },
                                    onClick = {  }
                                )
                            }
                        },
                        colors = TopAppBarDefaults.largeTopAppBarColors(
                            containerColor = Color.Red,
                            titleContentColor = Color.White,
                            actionIconContentColor = Color.White,
                            navigationIconContentColor = Color.White,
                            scrolledContainerColor = Color.Red
                        ),
                        scrollBehavior = scrollBehavior
                    )
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
                    text = "TopAppBar Examples",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                InteractiveRadioButtonGroup(
                    options = listOf("Center Aligned", "Small", "Medium", "Large"),
                    selectedOption = appBarType,
                    onOptionSelected = { newOption ->
                        appBarType = newOption.lowercase().replace(" ", "")
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))

                InteractiveSwitch(
                    label = "Use Scroll Behavior",
                    checked = useScrollBehavior,
                    onCheckedChange = { useScrollBehavior = it }
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Scroll to see the behavior of the TopAppBar",
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
}