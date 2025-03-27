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
            TabLayout(
                useScrollBehavior = useScrollBehavior,
                paddingValues = paddingValues,
                controlContent = {
                    ControlPanel(
                        useScrollBehavior = useScrollBehavior,
                        showIcons = showIcons,
                        showBadges = showBadges,
                        activeIndicatorColor = activeIndicatorColor,
                        onScrollBehaviorChange = { useScrollBehavior = it },
                        onShowIconsChange = { showIcons = it },
                        onShowBadgesChange = { showBadges = it },
                        onColorChange = { activeIndicatorColor = it }
                    )
                },
                tabContent = {
                    TabContent(
                        tabs = tabs,
                        selectedTabIndex = selectedTabIndex,
                        showIcons = showIcons,
                        showBadges = showBadges,
                        activeIndicatorColor = activeIndicatorColor,
                        onTabSelected = { selectedTabIndex = it },
                        screens = screens,
                        useScrollBehavior = useScrollBehavior
                    )
                }
            )
        }
    )
}

@Composable
private fun TabLayout(
    useScrollBehavior: Boolean,
    paddingValues: PaddingValues,
    controlContent: @Composable () -> Unit,
    tabContent: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        controlContent()

        if (useScrollBehavior) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                tabContent()
            }
        } else {
            tabContent()
        }
    }
}

@Composable
private fun ControlPanel(
    useScrollBehavior: Boolean,
    showIcons: Boolean,
    showBadges: Boolean,
    activeIndicatorColor: Color,
    onScrollBehaviorChange: (Boolean) -> Unit,
    onShowIconsChange: (Boolean) -> Unit,
    onShowBadgesChange: (Boolean) -> Unit,
    onColorChange: (Color) -> Unit
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
            onCheckedChange = onScrollBehaviorChange
        )
        Spacer(modifier = Modifier.height(8.dp))
        InteractiveSwitch(
            label = "Show Icons",
            checked = showIcons,
            onCheckedChange = onShowIconsChange
        )
        Spacer(modifier = Modifier.height(8.dp))
        InteractiveSwitch(
            label = "Show Badges",
            checked = showBadges,
            onCheckedChange = onShowBadgesChange
        )
        Spacer(modifier = Modifier.height(8.dp))
        InteractiveColorPicker(
            label = "Active Indicator Color",
            selectedColor = activeIndicatorColor,
            onColorSelected = onColorChange
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun TabContent(
    tabs: List<String>,
    selectedTabIndex: Int,
    showIcons: Boolean,
    showBadges: Boolean,
    activeIndicatorColor: Color,
    onTabSelected: (Int) -> Unit,
    screens: List<@Composable () -> Unit>,
    useScrollBehavior: Boolean
) {
    Column {
        CustomTabRow(
            tabs = tabs,
            selectedTabIndex = selectedTabIndex,
            activeIndicatorColor = activeIndicatorColor,
            showIcons = showIcons,
            showBadges = showBadges,
            onTabSelected = onTabSelected
        )
        Box(
            modifier = if (useScrollBehavior) {
                Modifier
            } else {
                Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            }
        ) {
            TabContentSection(
                selectedTabIndex = selectedTabIndex,
                screens = screens
            )
        }
    }
}

@Composable
private fun CustomTabRow(
    tabs: List<String>,
    selectedTabIndex: Int,
    activeIndicatorColor: Color,
    showIcons: Boolean,
    showBadges: Boolean,
    onTabSelected: (Int) -> Unit
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        indicator = @Composable { tabPositions ->
            CustomTabIndicator(
                tabPositions = tabPositions,
                selectedTabIndex = selectedTabIndex,
                color = activeIndicatorColor
            )
        }
    ) {
        tabs.forEachIndexed { index, tab ->
            TabItem(
                tab = tab,
                index = index,
                selected = selectedTabIndex == index,
                showIcon = showIcons,
                showBadge = showBadges,
                activeColor = activeIndicatorColor,
                onSelected = onTabSelected
            )
        }
    }
}

@Composable
private fun CustomTabIndicator(
    tabPositions: List<TabPosition>,
    selectedTabIndex: Int,
    color: Color
) {
    Box(
        modifier = Modifier
            .tabIndicatorOffset(tabPositions[selectedTabIndex])
            .height(3.dp)
            .background(
                color = color,
                shape = RoundedCornerShape(50)
            )
    )
}

@Composable
private fun TabItem(
    tab: String,
    index: Int,
    selected: Boolean,
    showIcon: Boolean,
    showBadge: Boolean,
    activeColor: Color,
    onSelected: (Int) -> Unit
) {
    Tab(
        selected = selected,
        onClick = { onSelected(index) },
        selectedContentColor = activeColor,
        unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        text = { Text(tab) },
        icon = {
            if (showIcon) {
                TabIcon(
                    tab = tab,
                    index = index,
                    showBadge = showBadge,
                    selected = selected,
                    activeColor = activeColor
                )
            }
        }
    )
}

@Composable
private fun TabIcon(
    tab: String,
    index: Int,
    showBadge: Boolean,
    selected: Boolean,
    activeColor: Color
) {
    val icon = when (tab) {
        "Home" -> Icons.Default.Home
        "Search" -> Icons.Default.Search
        "Favorites" -> Icons.Default.Favorite
        "Profile" -> Icons.Default.Person
        else -> Icons.Default.Home
    }

    val iconTint = if (selected) activeColor else MaterialTheme.colorScheme.onSurfaceVariant

    if (showBadge && index == 1) {
        BadgedBox(
            badge = {
                Badge(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError,
                    modifier = Modifier.size(24.dp)
                ) {
                    Text("99+")
                }
            }
        ) {
            Icon(icon, tab, tint = iconTint)
        }
    } else {
        Icon(icon, tab, tint = iconTint)
    }
}

@Composable
private fun TabContentSection(
    selectedTabIndex: Int,
    screens: List<@Composable () -> Unit>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        screens[selectedTabIndex]()
        Spacer(modifier = Modifier.height(16.dp))
        repeat(50) {
            Text("Item #$it",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp))
        }
    }
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