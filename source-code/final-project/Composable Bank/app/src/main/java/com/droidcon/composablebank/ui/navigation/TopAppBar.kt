@file:OptIn(ExperimentalMaterial3Api::class)

package com.droidcon.composablebank.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(navController: NavController, name: String) {
    var showMenu by remember { mutableStateOf(false) }
    var appBarType by remember { mutableStateOf("Center Aligned") }
    var useScrollBehavior by remember { mutableStateOf(true) }

    val scrollBehavior = rememberAppBarScrollBehavior(appBarType, useScrollBehavior)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { DynamicTopAppBar(appBarType, scrollBehavior, showMenu, navController) },
        content = { paddingValues ->
            MainContent(
                paddingValues = paddingValues,
                appBarType = appBarType,
                useScrollBehavior = useScrollBehavior,
                onAppBarTypeChange = { newType -> appBarType = newType },
                onScrollBehaviorChange = { newValue -> useScrollBehavior = newValue }
            ) }
    )
}

@Composable
private fun rememberAppBarScrollBehavior(
    appBarType: String,
    useScrollBehavior: Boolean
): TopAppBarScrollBehavior {
    return when {
        appBarType == "Center Aligned" && useScrollBehavior ->
            TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

        appBarType == "Center Aligned" && !useScrollBehavior ->
            TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

        useScrollBehavior ->
            TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

        else ->
            TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    }
}

@Composable
private fun DynamicTopAppBar(
    appBarType: String,
    scrollBehavior: TopAppBarScrollBehavior,
    showMenu: Boolean,
    navController: NavController
) {
    when (appBarType) {
        "Center Aligned" -> CenterAlignedTopBar(scrollBehavior)
        "Small" -> SmallTopBar(scrollBehavior, navController, showMenu)
        "Medium" -> MediumTopBar(scrollBehavior, navController, showMenu)
        "Large" -> LargeTopBar(scrollBehavior, navController, showMenu)
    }
}

@Composable
private fun CenterAlignedTopBar(scrollBehavior: TopAppBarScrollBehavior) {
    CenterAlignedTopAppBar(
        title = { AppBarTitle("Center Aligned", Color.Black) },
        navigationIcon = { NavigationIcon(Icons.Default.Menu) },
        actions = { TopAppBarActions(type = "Center Aligned") },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.LightGray,
            actionIconContentColor = Color.Black,
            navigationIconContentColor = Color.Black,
            scrolledContainerColor = Color.LightGray,
        ),
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun SmallTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavController,
    showMenu: Boolean
) {
    TopAppBar(
        title = { AppBarTitle("Small TopAppBar", Color.White) },
        navigationIcon = { BackNavigationIcon(navController) },
        actions = { TopAppBarActions(type = "Small", showMenu = showMenu) },
        colors = TopAppBarColors(type = "Small"),
        scrollBehavior = scrollBehavior,
    )
}

@Composable
private fun MediumTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavController,
    showMenu: Boolean
) {
    MediumTopAppBar(
        title = { AppBarTitle("Medium TopAppBar", Color.White) },
        navigationIcon = { BackNavigationIcon(navController) },
        actions = { TopAppBarActions(type = "Medium", showMenu = showMenu) },
        colors = TopAppBarColors(type = "Medium"),
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun LargeTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavController,
    showMenu: Boolean
) {
    LargeTopAppBar(
        title = { AppBarTitle("Large TopAppBar", Color.White) },
        navigationIcon = { BackNavigationIcon(navController) },
        actions = { TopAppBarActions(type = "Large", showMenu = showMenu) },
        colors = TopAppBarColors(type = "Large"),
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun TopAppBarActions(type: String, showMenu: Boolean = false) {
    val iconColor = when (type) {
        "Center Aligned" -> Color.Black
        else -> Color.White
    }

    IconButton(onClick = {  }) {
        Icon(Icons.Default.Search, "Search", tint = iconColor)
    }
    IconButton(onClick = { }) {
        Icon(Icons.Default.Favorite, "Favorite", tint = iconColor)
    }
    DropdownMenuController(showMenu, iconColor)
}

@Composable
private fun DropdownMenuController(showMenu: Boolean, iconColor: Color) {
    var currentMenuState by remember { mutableStateOf(showMenu) }

    IconButton(onClick = { currentMenuState = true }) {
        Icon(Icons.Default.MoreVert, "More", tint = iconColor)
    }
    DropdownMenu(
        expanded = currentMenuState,
        onDismissRequest = { currentMenuState = false }
    ) {
        DropdownMenuItem(text = { Text("Action 1") }, onClick = {})
        DropdownMenuItem(text = { Text("Action 2") }, onClick = {})
    }
}

@Composable
private fun AppBarTitle(text: String, color: Color) {
    Text(
        text = text,
        style = when (text) {
            "Large TopAppBar" -> MaterialTheme.typography.headlineMedium
            "Medium TopAppBar" -> MaterialTheme.typography.titleLarge
            else -> MaterialTheme.typography.titleMedium
        },
        color = color
    )
}

@Composable
private fun NavigationIcon(icon: ImageVector) {
    IconButton(onClick = {}) {
        Icon(icon, "Menu", tint = Color.Black)
    }
}

@Composable
private fun BackNavigationIcon(navController: NavController) {
    IconButton(onClick = { navController.popBackStack() }) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color.White)
    }
}

@SuppressLint("ComposableNaming")
@Composable
private fun TopAppBarColors(type: String): TopAppBarColors {
    return when (type) {
        "Small" -> TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Blue,
            titleContentColor = Color.White,
            scrolledContainerColor = Color.Blue
        )
        "Medium" -> TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Green,
            titleContentColor = Color.White,
            scrolledContainerColor = Color.Green
        )
        "Large" -> TopAppBarDefaults.largeTopAppBarColors(
            containerColor = Color.Red,
            titleContentColor = Color.White,
            scrolledContainerColor = Color.Red
        )
        else -> TopAppBarDefaults.topAppBarColors()
    }
}

@Composable
private fun MainContent(
    paddingValues: PaddingValues,
    appBarType: String,
    useScrollBehavior: Boolean,
    onAppBarTypeChange: (String) -> Unit,
    onScrollBehaviorChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBarControls(
            appBarType = appBarType,
            useScrollBehavior = useScrollBehavior,
            onAppBarTypeChange = onAppBarTypeChange,
            onScrollBehaviorChange = onScrollBehaviorChange
        )
        ScrollContent()
    }
}

@Composable
private fun TopAppBarControls(
    appBarType: String,
    useScrollBehavior: Boolean,
    onAppBarTypeChange: (String) -> Unit,
    onScrollBehaviorChange: (Boolean) -> Unit
) {
    Text(
        text = "TopAppBar Examples",
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(top = 16.dp)
    )
    Spacer(Modifier.height(16.dp))

    InteractiveRadioButtonGroup(
        options = listOf("Center Aligned", "Small", "Medium", "Large"),
        selectedOption = appBarType,
        onOptionSelected = { newType -> onAppBarTypeChange(newType) }
    )

    Spacer(Modifier.height(8.dp))
    InteractiveSwitch(
        label = "Use Scroll Behavior",
        checked = useScrollBehavior,
        onCheckedChange = { newValue -> onScrollBehaviorChange(newValue) }
    )
}

@Composable
private fun ScrollContent() {
    Text("Scroll to see the behavior...", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(bottom = 16.dp))
    repeat(50) {
        Text("Item #$it", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(8.dp))
    }
}