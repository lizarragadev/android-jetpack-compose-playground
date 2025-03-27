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
import kotlinx.coroutines.CoroutineScope

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
        topBar = { CustomTopAppBar(title = name, navController = navController) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { paddingValues ->
            NavigationRailLayout(
                paddingValues = paddingValues,
                railColor = railColor,
                selectedItem = selectedItem,
                showBadges = showBadges,
                showLabels = showLabels,
                onItemSelected = { selectedItem = it },
                coroutineScope = coroutineScope,
                snackbarHostState = snackbarHostState,
                showSnackbar = { showSnackbar = true },
                controlContent = {
                    RailControls(
                        showBadges = showBadges,
                        showLabels = showLabels,
                        railColor = railColor,
                        onBadgesChange = { showBadges = it },
                        onLabelsChange = { showLabels = it },
                        onColorChange = { railColor = it }
                    )
                }
            )
        }
    )

    HandleSnackbar(showSnackbar, snackbarHostState)
}

@Composable
private fun NavigationRailLayout(
    paddingValues: PaddingValues,
    railColor: Color,
    selectedItem: Int,
    showBadges: Boolean,
    showLabels: Boolean,
    onItemSelected: (Int) -> Unit,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    showSnackbar: () -> Unit,
    controlContent: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalArrangement = Arrangement.Start
    ) {
        RailNavigation(
            railColor = railColor,
            selectedItem = selectedItem,
            showBadges = showBadges,
            showLabels = showLabels,
            onItemSelected = onItemSelected,
            coroutineScope = coroutineScope,
            snackbarHostState = snackbarHostState,
            showSnackbar = showSnackbar
        )

        VerticalDivider(
            color = MaterialTheme.colorScheme.primary,
            thickness = 2.dp,
            modifier = Modifier.fillMaxHeight()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RailScreenContent(selectedItem = selectedItem)
            controlContent()
        }
    }
}

@Composable
private fun RailNavigation(
    railColor: Color,
    selectedItem: Int,
    showBadges: Boolean,
    showLabels: Boolean,
    onItemSelected: (Int) -> Unit,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    showSnackbar: () -> Unit
) {
    NavigationRail(
        modifier = Modifier
            .fillMaxHeight()
            .background(railColor)
            .padding(vertical = 16.dp),
        containerColor = railColor,
        header = { RailHeader(coroutineScope, snackbarHostState, showSnackbar) }
    ) {
        listOf("Home", "Search", "Favorites").forEachIndexed { index, label ->
            RailNavigationItem(
                label = label,
                index = index,
                selected = selectedItem == index,
                showBadge = showBadges && (index == 1 || index == 2),
                showLabel = showLabels,
                onSelected = onItemSelected
            )
        }
    }
}

@Composable
private fun RailHeader(
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    showSnackbar: () -> Unit
) {
    FloatingActionButton(
        onClick = {
            showSnackbar()
            coroutineScope.launch { snackbarHostState.showSnackbar("Header FAB clicked") }
        },
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Icon(Icons.Default.Add, contentDescription = "Add")
    }
}

@Composable
private fun RailNavigationItem(
    label: String,
    index: Int,
    selected: Boolean,
    showBadge: Boolean,
    showLabel: Boolean,
    onSelected: (Int) -> Unit
) {
    NavigationRailItem(
        selected = selected,
        onClick = { onSelected(index) },
        icon = { RailIconWithBadge(label = label, index = index, showBadge = showBadge) },
        label = if (showLabel) { { Text(label) } } else null
    )
}

@Composable
private fun RailIconWithBadge(label: String, index: Int, showBadge: Boolean) {
    val icon = when (label) {
        "Home" -> Icons.Default.Home
        "Search" -> Icons.Default.Search
        "Favorites" -> Icons.Default.Favorite
        else -> Icons.Default.Home
    }

    if (showBadge) {
        BadgedBox(
            badge = {
                RailBadgeContent(index = index)
            }
        ) {
            Icon(icon, contentDescription = label)
        }
    } else {
        Icon(icon, contentDescription = label)
    }
}

@Composable
private fun RailBadgeContent(index: Int) {
    Badge(
        containerColor = MaterialTheme.colorScheme.error,
        contentColor = MaterialTheme.colorScheme.onError
    ) {
        if (index == 1) Text("99+")
    }
}

@Composable
private fun RailScreenContent(selectedItem: Int) {
    when (selectedItem) {
        0 -> HomeRailScreen()
        1 -> SearchRailScreen()
        2 -> FavoritesRailScreen()
    }
}

@Composable
private fun RailControls(
    showBadges: Boolean,
    showLabels: Boolean,
    railColor: Color,
    onBadgesChange: (Boolean) -> Unit,
    onLabelsChange: (Boolean) -> Unit,
    onColorChange: (Color) -> Unit
) {
    Spacer(modifier = Modifier.height(16.dp))
    InteractiveSwitch(
        label = "Show Badges",
        checked = showBadges,
        onCheckedChange = onBadgesChange
    )
    Spacer(modifier = Modifier.height(8.dp))
    InteractiveSwitch(
        label = "Show Labels",
        checked = showLabels,
        onCheckedChange = onLabelsChange
    )
    Spacer(modifier = Modifier.height(8.dp))
    InteractiveColorPicker(
        label = "Navigation Rail Color",
        selectedColor = railColor,
        onColorSelected = onColorChange
    )
}

@Composable
private fun HandleSnackbar(showSnackbar: Boolean, snackbarHostState: SnackbarHostState) {
    if (showSnackbar) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar("Snackbar from Navigation Rail")
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