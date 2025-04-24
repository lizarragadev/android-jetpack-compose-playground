package com.droidcon.composablebank.ui.navigation

import com.droidcon.composablebank.components.InteractiveColorPicker
import com.droidcon.composablebank.components.InteractiveSwitch
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.droidcon.composablebank.utils.CustomTopAppBar
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.Help
import kotlinx.coroutines.CoroutineScope
import androidx.compose.material3.ModalDrawerSheet
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
internal fun NavDrawer(navController: NavController, name: String) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf("Home") }
    var showBadges by remember { mutableStateOf(false) }
    var activeIndicatorColor by remember { mutableStateOf(Color.Green) }
    var scrimVisible by remember { mutableStateOf(true) }

    NavigationDrawerContainer(
        drawerState = drawerState,
        scope = scope,
        scrimVisible = scrimVisible,
        activeIndicatorColor = activeIndicatorColor,
        selectedItem = selectedItem,
        showBadges = showBadges,
        onItemSelected = { item ->
            selectedItem = item
            scope.launch { drawerState.close() }
        },
        controlPanel = {
            ControlPanel(
                showBadges = showBadges,
                activeIndicatorColor = activeIndicatorColor,
                scrimVisible = scrimVisible,
                onBadgesChanged = { showBadges = it },
                onColorChanged = { activeIndicatorColor = it },
                onScrimChanged = { scrimVisible = it }
            )
        }
    )
}

@Composable
private fun NavigationDrawerContainer(
    drawerState: DrawerState,
    scope: CoroutineScope,
    scrimVisible: Boolean,
    activeIndicatorColor: Color,
    selectedItem: String,
    showBadges: Boolean,
    onItemSelected: (String) -> Unit,
    controlPanel: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(300.dp)
                    .statusBarsPadding(),
                drawerContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh
            ) {
                DrawerContent(
                    selectedItem = selectedItem,
                    showBadges = showBadges,
                    activeIndicatorColor = activeIndicatorColor,
                    onItemSelected = onItemSelected
                )
            }
        },
        scrimColor = if (scrimVisible) MaterialTheme.colorScheme.scrim.copy(alpha = 0.32f) else Color.Transparent
    ) {
        MainContent(
            scope = scope,
            controlPanel = controlPanel,
            drawerState = drawerState,
            selectedItem = selectedItem
        )
    }
}

@Composable
private fun DrawerContent(
    selectedItem: String,
    showBadges: Boolean,
    activeIndicatorColor: Color,
    onItemSelected: (String) -> Unit
) {
    val badgeValues = listOf("3", "99+", null)
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 12.dp)
    ) {
        NavigationDrawerHeader {
            Column(Modifier.padding(16.dp)) {
                Text("Composable Bank", style = MaterialTheme.typography.titleLarge)
                Text("user@composablebank.com", style = MaterialTheme.typography.bodyMedium)
            }
        }

        NavigationDrawerSection(
            items = listOf("Home", "Accounts", "Transfers"),
            selectedItem = selectedItem,
            showBadges = showBadges,
            activeIndicatorColor = activeIndicatorColor,
            badgeValues = badgeValues,
            onItemSelected = onItemSelected
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        NavigationDrawerSection(
            items = listOf("Settings", "Help"),
            selectedItem = selectedItem,
            showBadges = false,
            activeIndicatorColor = activeIndicatorColor,
            badgeValues = badgeValues,
            onItemSelected = onItemSelected
        )
    }
}

@Composable
private fun MainContent(
    scope: CoroutineScope,
    controlPanel: @Composable () -> Unit,
    drawerState: DrawerState,
    selectedItem: String
) {
    val screenNavController = rememberNavController()

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            CustomTopAppBar(
                title = selectedItem,
                navigationIcon = {
                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Open drawer")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            NavHost(navController = screenNavController, startDestination = "Home") {
                composable("Home") { controlPanel() }
                composable("Accounts") { AccountScreen() }
                composable("Transfers") { TransferScreen() }
                composable("Settings") { SettingsScreen() }
                composable("Help") { HelpScreen() }
            }
        }
    }
}

@Composable
private fun ControlPanel(
    showBadges: Boolean,
    activeIndicatorColor: Color,
    scrimVisible: Boolean,
    onBadgesChanged: (Boolean) -> Unit,
    onColorChanged: (Color) -> Unit,
    onScrimChanged: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        InteractiveSwitch(
            label = "Show Badges",
            checked = showBadges,
            onCheckedChange = onBadgesChanged
        )

        InteractiveColorPicker(
            label = "Active Indicator Color",
            selectedColor = activeIndicatorColor,
            onColorSelected = onColorChanged
        )

        InteractiveSwitch(
            label = "Show Scrim",
            checked = scrimVisible,
            onCheckedChange = onScrimChanged
        )
    }
}

@Composable
private fun NavigationDrawerHeader(content: @Composable ColumnScope.() -> Unit) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        modifier = Modifier
            .fillMaxWidth()
            .height(152.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            content()
        }
    }
}

@Composable
private fun NavigationDrawerSection(
    items: List<String>,
    selectedItem: String,
    showBadges: Boolean,
    activeIndicatorColor: Color,
    badgeValues: List<String?>,
    onItemSelected: (String) -> Unit
) {
    items.forEachIndexed { index, item ->
        NavigationDrawerItem(
            label = { Text(item) },
            selected = selectedItem == item,
            icon = { Icon(getIconForItem(item), contentDescription = null) },
            badge = if (showBadges) {
                badgeValues.getOrNull(index)?.let { { Text(it) } }
            } else null,
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = activeIndicatorColor.copy(alpha = 0.2f),
                unselectedContainerColor = Color.Transparent
            ),
            modifier = Modifier.padding(horizontal = 8.dp),
            onClick = { onItemSelected(item) }
        )
    }
}

private fun getIconForItem(item: String): ImageVector {
    return when (item) {
        "Home" -> Icons.Filled.Home
        "Accounts" -> Icons.Filled.AccountBalance
        "Transfers" -> Icons.Filled.SwapHoriz
        "Settings" -> Icons.Filled.Settings
        "Help" -> Icons.AutoMirrored.Filled.Help
        else -> Icons.Filled.Info
    }
}

@Composable
private fun AccountScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Accounts Screen", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
private fun TransferScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Transfers Screen", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
private fun SettingsScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Settings Screen", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
private fun HelpScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Help Screen", style = MaterialTheme.typography.headlineMedium)
    }
}