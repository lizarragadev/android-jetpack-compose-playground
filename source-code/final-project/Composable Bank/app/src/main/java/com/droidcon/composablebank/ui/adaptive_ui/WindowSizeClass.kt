package com.droidcon.composablebank.ui.adaptive_ui

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.windowsizeclass.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DesktopWindows
import androidx.compose.material.icons.filled.TabletAndroid
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import com.droidcon.composablebank.utils.CustomTopAppBar
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass

@Composable
fun WindowSizeClass(navController: NavController, name: String) {
    val windowSizeClass = rememberWindowSizeClass()

    MaterialTheme(colorScheme = dynamicColorScheme()) {
        WindowSizeScaffold(
            title = name,
            navController = navController,
            windowSizeClass = windowSizeClass
        )
    }
}

@Composable
private fun WindowSizeScaffold(
    title: String,
    navController: NavController,
    windowSizeClass: WindowSizeClass
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { CustomTopAppBar(title = title, navController = navController) },
        content = { padding ->
            AdaptiveLayoutContainer(
                padding = padding,
                windowSizeClass = windowSizeClass
            )
        }
    )
}

@Composable
private fun AdaptiveLayoutContainer(
    padding: PaddingValues,
    windowSizeClass: WindowSizeClass
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        when (windowSizeClass.widthSizeClass) {
            WindowWidthSizeClass.Compact -> CompactLayout()
            WindowWidthSizeClass.Medium -> MediumLayout()
            WindowWidthSizeClass.Expanded -> ExpandedLayout()
        }
    }
}

@Composable
private fun CompactLayout() {
    LayoutContainer(backgroundColor = Color.Red) {
        ColumnLayout(
            title = "Compact Layout",
            description = "Suitable for small phones."
        )
    }
}

@Composable
private fun MediumLayout() {
    LayoutContainer(backgroundColor = Color.Green) {
        TwoPaneLayout(
            title = "Medium Layout",
            description = "Suitable for tablets or medium devices.",
            icon = Icons.Default.TabletAndroid
        )
    }
}

@Composable
private fun ExpandedLayout() {
    LayoutContainer(backgroundColor = Color.Blue) {
        TwoPaneLayout(
            title = "Expanded Layout",
            description = "Suitable for large screens such as desktops.",
            icon = Icons.Default.DesktopWindows,
            reverseOrder = true
        )
    }
}

@Composable
private fun LayoutContainer(
    backgroundColor: Color,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
private fun ColumnLayout(title: String, description: String) {
    Column(
        modifier = Modifier.padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TitleText(text = title)
        Spacer(modifier = Modifier.height(8.dp))
        DescriptionText(text = description)
    }
}

@Composable
private fun TwoPaneLayout(
    title: String,
    description: String,
    icon: ImageVector,
    reverseOrder: Boolean = false
) {
    Row(
        modifier = Modifier.padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (reverseOrder) {
            IconLayout(icon = icon)
            ColumnLayout(title, description)
        } else {
            ColumnLayout(title, description)
            IconLayout(icon = icon)
        }
    }
}

@Composable
private fun TitleText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineMedium,
        color = Color.White
    )
}

@Composable
private fun DescriptionText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = Color.White
    )
}

@Composable
private fun IconLayout(icon: ImageVector) {
    Icon(
        imageVector = icon,
        contentDescription = null,
        tint = Color.White,
        modifier = Modifier.size(100.dp)
    )
}

@Composable
@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
private fun rememberWindowSizeClass(): WindowSizeClass {
    val activity = LocalContext.current as Activity
    return calculateWindowSizeClass(activity)
}

@Composable
private fun dynamicColorScheme(): ColorScheme {
    return if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
}