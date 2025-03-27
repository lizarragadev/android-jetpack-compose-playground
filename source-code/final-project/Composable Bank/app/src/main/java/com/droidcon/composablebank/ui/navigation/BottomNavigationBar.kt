package com.droidcon.composablebank.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ScrollState
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
import kotlinx.coroutines.CoroutineScope
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem

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
        topBar = { CustomTopAppBar(title = name, navController = navController) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            NavigationBarSection(
                selectedItem,
                navigationBarColor,
                isNavigationBarVisible,
                showBadges,
                onSelectedItemChange = { newIndex -> selectedItem = newIndex }
            ) },
        floatingActionButton = { FabSection(showFab, coroutineScope, snackbarHostState) },
        content = { paddingValues ->
            MainContentSection(
                paddingValues = paddingValues,
                scrollState = scrollState,
                selectedItem = selectedItem,
                useScrollBehavior = useScrollBehavior,
                showFab = showFab,
                showBadges = showBadges,
                navigationBarColor = navigationBarColor,
                onSelectedItemChange = { selectedItem = it },
                onUseScrollBehaviorChange = { useScrollBehavior = it },
                onShowFabChange = { showFab = it },
                onShowBadgesChange = { showBadges = it },
                onColorChange = { navigationBarColor = it }
            )
        }
    )

    HandleSnackbar(showSnackbar, snackbarHostState)
}

@Composable
private fun NavigationBarSection(
    selectedItem: Int,
    navigationBarColor: Color,
    isVisible: Boolean,
    showBadges: Boolean,
    onSelectedItemChange: (Int) -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
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
                NavigationItem(
                    label = label,
                    index = index,
                    selected = selectedItem == index,
                    showBadge = showBadges && index == 1,
                    onSelectedItemChange = onSelectedItemChange
                )
            }
        }
    }
}

@Composable
private fun RowScope.NavigationItem(
    label: String,
    index: Int,
    selected: Boolean,
    showBadge: Boolean,
    onSelectedItemChange: (Int) -> Unit
) {
    NavigationBarItem(
        selected = selected,
        onClick = { onSelectedItemChange(index) },
        icon = {
            if (showBadge) {
                BadgedBox(badge = { NotificationBadge() }) {
                    NavigationIcon(label)
                }
            } else {
                NavigationIcon(label)
            }
        },
        label = { Text(label) }
    )
}

@Composable
private fun NavigationIcon(label: String) {
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

@Composable
private fun NotificationBadge() {
    Badge(
        containerColor = MaterialTheme.colorScheme.error,
        contentColor = MaterialTheme.colorScheme.onError
    ) {
        Text("99+")
    }
}

@Composable
private fun FabSection(
    showFab: Boolean,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    if (showFab) {
        FloatingActionButton(
            onClick = { coroutineScope.launch { snackbarHostState.showSnackbar("Floating Action Button clicked") } },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ) {
            Icon(Icons.Default.Add, "Add")
        }
    }
}

@Composable
private fun MainContentSection(
    paddingValues: PaddingValues,
    scrollState: ScrollState,
    selectedItem: Int,
    useScrollBehavior: Boolean,
    showFab: Boolean,
    showBadges: Boolean,
    navigationBarColor: Color,
    onSelectedItemChange: (Int) -> Unit,
    onUseScrollBehaviorChange: (Boolean) -> Unit,
    onShowFabChange: (Boolean) -> Unit,
    onShowBadgesChange: (Boolean) -> Unit,
    onColorChange: (Color) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenContent(selectedItem)
        ControlPanel(
            useScrollBehavior = useScrollBehavior,
            showFab = showFab,
            showBadges = showBadges,
            navigationBarColor = navigationBarColor,
            onUseScrollBehaviorChange = onUseScrollBehaviorChange,
            onShowFabChange = onShowFabChange,
            onShowBadgesChange = onShowBadgesChange,
            onColorChange = onColorChange
        )
        ScrollIndicator()
    }
}

@Composable
private fun ScreenContent(selectedItem: Int) {
    when (selectedItem) {
        0 -> HomeScreen()
        1 -> SearchScreen()
        2 -> FavoritesScreen()
    }
}

@Composable
private fun ControlPanel(
    useScrollBehavior: Boolean,
    showFab: Boolean,
    showBadges: Boolean,
    navigationBarColor: Color,
    onUseScrollBehaviorChange: (Boolean) -> Unit,
    onShowFabChange: (Boolean) -> Unit,
    onShowBadgesChange: (Boolean) -> Unit,
    onColorChange: (Color) -> Unit
) {
    Spacer(modifier = Modifier.height(16.dp))
    InteractiveSwitch(
        label = "Enable Scroll Behavior",
        checked = useScrollBehavior,
        onCheckedChange = onUseScrollBehaviorChange
    )
    Spacer(modifier = Modifier.height(8.dp))
    InteractiveSwitch(
        label = "Show Floating Action Button",
        checked = showFab,
        onCheckedChange = onShowFabChange
    )
    Spacer(modifier = Modifier.height(8.dp))
    InteractiveSwitch(
        label = "Show Badges",
        checked = showBadges,
        onCheckedChange = onShowBadgesChange
    )
    Spacer(modifier = Modifier.height(8.dp))
    InteractiveColorPicker(
        label = "Navigation Bar Color",
        selectedColor = navigationBarColor,
        onColorSelected = onColorChange
    )
}

@Composable
private fun ScrollIndicator() {
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

@Composable
private fun HandleSnackbar(showSnackbar: Boolean, snackbarHostState: SnackbarHostState) {
    if (showSnackbar) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar("Snackbar from Bottom Navigation Bar")
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