package com.droidcon.composablebank.ui.adaptive_ui

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.navigation.NavController
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.utils.CustomTopAppBar

@Composable
fun LocalConfiguration(navController: NavController, name: String) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val isSystemInDarkTheme = isSystemInDarkTheme()
    var currentDarkMode by rememberSaveable { mutableStateOf(isSystemInDarkTheme) }
    var orientation by remember { mutableIntStateOf(configuration.orientation) }

    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val density = LocalDensity.current.density
    val fontScale = LocalDensity.current.fontScale
    val touchscreen = when (configuration.touchscreen) {
        Configuration.TOUCHSCREEN_FINGER -> "Touchscreen (Finger)"
        else -> "No Touchscreen"
    }
    val keyboard = when (configuration.keyboard) {
        Configuration.KEYBOARD_QWERTY -> "Physical QWERTY Keyboard"
        Configuration.KEYBOARD_12KEY -> "12-Key Keyboard"
        else -> "No Physical Keyboard"
    }
    val navigation = when (configuration.navigation) {
        Configuration.NAVIGATION_DPAD -> "DPad Navigation"
        Configuration.NAVIGATION_TRACKBALL -> "Trackball Navigation"
        Configuration.NAVIGATION_WHEEL -> "Wheel Navigation"
        else -> "No Special Navigation"
    }

    MaterialTheme(
        colorScheme = if (currentDarkMode) darkColorScheme() else lightColorScheme(),
        content = {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    CustomTopAppBar(title = name, navController = navController)
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
                            text = "Device Configuration",
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Screen Width: $screenWidth",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Screen Height: $screenHeight",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Orientation: ${if (orientation == Configuration.ORIENTATION_LANDSCAPE) "Landscape" else "Portrait"}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Night Mode: ${if (currentDarkMode) "Enabled" else "Disabled"}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Screen Density: ${density}x",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Font Scale: $fontScale",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Touchscreen: $touchscreen",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Keyboard: $keyboard",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Navigation: $navigation",
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        InteractiveSwitch(
                            label = "Dark Mode",
                            checked = currentDarkMode,
                            onCheckedChange = { isChecked ->
                                currentDarkMode = isChecked
                            }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(onClick = {
                            val newOrientation = if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                            } else {
                                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                            }
                            (context as? Activity)?.requestedOrientation = newOrientation
                            orientation = if (newOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                                Configuration.ORIENTATION_LANDSCAPE
                            } else {
                                Configuration.ORIENTATION_PORTRAIT
                            }
                        }) {
                            Text("Toggle Orientation")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        when (orientation) {
                            Configuration.ORIENTATION_PORTRAIT -> PortraitLayout()
                            Configuration.ORIENTATION_LANDSCAPE -> LandscapeLayout()
                        }
                    }
                }
            )
        }
    )
}

@Composable
private fun PortraitLayout() {
    Box(
        modifier = Modifier
            .size(300.dp)
            .background(Color.Red.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Portrait Layout",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
    }
}

@Composable
private fun LandscapeLayout() {
    Box(
        modifier = Modifier
            .size(400.dp)
            .background(Color.Green.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Landscape Layout",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
    }
}