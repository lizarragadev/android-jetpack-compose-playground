package com.droidcon.composablebank.ui.navigation

import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.droidcon.composablebank.components.InteractiveSwitch
import androidx.compose.material3.Icon
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.ui.graphics.Color
import com.droidcon.composablebank.components.InteractiveColorPicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tabs(navController: NavController, name: String) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    var useScrollBehavior by remember { mutableStateOf(true) }
    var showIcons by remember { mutableStateOf(true) }
    var showBadges by remember { mutableStateOf(true) }
    var activeIndicatorColor by remember { mutableStateOf(Color.Blue) }

    val tabs = listOf("Home", "Search", "Favorites", "Profile")
    val screens = listOf<@Composable () -> Unit>(
        { HomeTabScreen() },
        { SearchTabScreen() },
        { FavoritesTabScreen() },
        { ProfileTabScreen() }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { CustomTopAppBar(title = name, navController = navController) },
        content = { paddingValues ->
            if (useScrollBehavior) {
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
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        InteractiveSwitch(
                            label = "Full Screen Scroll",
                            checked = useScrollBehavior,
                            onCheckedChange = { useScrollBehavior = it }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        InteractiveSwitch(
                            label = "Show Icons",
                            checked = showIcons,
                            onCheckedChange = { showIcons = it }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        InteractiveSwitch(
                            label = "Show Badges",
                            checked = showBadges,
                            onCheckedChange = { showBadges = it }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        InteractiveColorPicker(
                            label = "Active Indicator Color",
                            selectedColor = activeIndicatorColor,
                            onColorSelected = { activeIndicatorColor = it }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        indicator = @Composable { tabPositions ->
                            Box(
                                modifier = Modifier
                                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                                    .height(3.dp)
                                    .background(
                                        color = activeIndicatorColor,
                                        shape = RoundedCornerShape(50)
                                    )
                            )
                        }
                    ) {
                        tabs.forEachIndexed { index, tab ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                selectedContentColor = activeIndicatorColor,
                                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                text = { Text(tab) },
                                icon = if (showIcons) {
                                    @Composable {
                                        if (showBadges && index == 1) {
                                            BadgedBox(
                                                badge = {
                                                    Badge(
                                                        containerColor = MaterialTheme.colorScheme.error,
                                                        contentColor = MaterialTheme.colorScheme.onError,
                                                        modifier = Modifier.size(24.dp)
                                                    ) {
                                                        Text(text = if (index == 1) "99+" else "")
                                                    }
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = when (tab) {
                                                        "Home" -> Icons.Default.Home
                                                        "Search" -> Icons.Default.Search
                                                        "Favorites" -> Icons.Default.Favorite
                                                        "Profile" -> Icons.Default.Person
                                                        else -> Icons.Default.Home
                                                    },
                                                    contentDescription = tab,
                                                    tint = if (selectedTabIndex == index) activeIndicatorColor else MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                            }
                                        } else {
                                            Icon(
                                                imageVector = when (tab) {
                                                    "Home" -> Icons.Default.Home
                                                    "Search" -> Icons.Default.Search
                                                    "Favorites" -> Icons.Default.Favorite
                                                    "Profile" -> Icons.Default.Person
                                                    else -> Icons.Default.Home
                                                },
                                                contentDescription = tab,
                                                tint = if (selectedTabIndex == index) activeIndicatorColor else MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                } else {
                                    null
                                }
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        screens[selectedTabIndex]()
                        Spacer(modifier = Modifier.height(16.dp))
                        repeat(50) {
                            Text(
                                text = "Item #$it",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        InteractiveSwitch(
                            label = "Full Screen Scroll",
                            checked = useScrollBehavior,
                            onCheckedChange = { useScrollBehavior = it }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        InteractiveSwitch(
                            label = "Show Icons",
                            checked = showIcons,
                            onCheckedChange = { showIcons = it }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        InteractiveSwitch(
                            label = "Show Badges",
                            checked = showBadges,
                            onCheckedChange = { showBadges = it }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        InteractiveColorPicker(
                            label = "Active Indicator Color",
                            selectedColor = activeIndicatorColor,
                            onColorSelected = { activeIndicatorColor = it }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        indicator = @Composable { tabPositions ->
                            Box(
                                modifier = Modifier
                                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                                    .height(3.dp)
                                    .background(
                                        color = activeIndicatorColor,
                                        shape = RoundedCornerShape(50)
                                    )
                            )
                        }
                    ) {
                        tabs.forEachIndexed { index, tab ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                text = { Text(tab) },
                                icon = if (showIcons) {
                                    @Composable {
                                        if (showBadges && index == 1) {
                                            BadgedBox(
                                                badge = {
                                                    Badge(
                                                        containerColor = MaterialTheme.colorScheme.error,
                                                        contentColor = MaterialTheme.colorScheme.onError,
                                                        modifier = Modifier.size(24.dp)
                                                    ) {
                                                        Text(text = if (index == 1) "99+" else "")
                                                    }
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = when (tab) {
                                                        "Home" -> Icons.Default.Home
                                                        "Search" -> Icons.Default.Search
                                                        "Favorites" -> Icons.Default.Favorite
                                                        "Profile" -> Icons.Default.Person
                                                        else -> Icons.Default.Home
                                                    },
                                                    contentDescription = tab,
                                                    tint = if (selectedTabIndex == index) activeIndicatorColor else MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                            }
                                        } else {
                                            Icon(
                                                imageVector = when (tab) {
                                                    "Home" -> Icons.Default.Home
                                                    "Search" -> Icons.Default.Search
                                                    "Favorites" -> Icons.Default.Favorite
                                                    "Profile" -> Icons.Default.Person
                                                    else -> Icons.Default.Home
                                                },
                                                contentDescription = tab,
                                                tint = if (selectedTabIndex == index) activeIndicatorColor else MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                } else {
                                    null
                                }
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            screens[selectedTabIndex]()
                            Spacer(modifier = Modifier.height(16.dp))
                            repeat(50) {
                                Text(
                                    text = "Item #$it",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun HomeTabScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Home Screen", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Welcome to Home", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun SearchTabScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Search Screen", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Search here", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun FavoritesTabScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Favorites Screen", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Your favorites", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun ProfileTabScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Profile Screen", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Manage profile", style = MaterialTheme.typography.bodyLarge)
    }
}