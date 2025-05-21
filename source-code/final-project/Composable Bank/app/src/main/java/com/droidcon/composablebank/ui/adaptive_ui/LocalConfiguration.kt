package com.droidcon.composablebank.ui.adaptive_ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
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
    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    val configurationState = rememberConfigurationState(configuration, context)

    MaterialTheme(
        colorScheme = if (configurationState.currentDarkMode) darkColorScheme() else lightColorScheme()
    ) {
        AppScaffold(
            title = name,
            navController = navController,
            configuration = configuration,
            state = configurationState
        )
    }
}

@Composable
private fun rememberConfigurationState(
    configuration: Configuration,
    context: Context
): ConfigurationState {
    val isSystemInDarkTheme = isSystemInDarkTheme()

    var currentDarkMode by rememberSaveable { mutableStateOf(isSystemInDarkTheme) }
    var orientation by remember { mutableIntStateOf(configuration.orientation) }

    return ConfigurationState(
        currentDarkMode = currentDarkMode,
        onDarkModeChange = { newValue -> currentDarkMode = newValue },
        orientation = orientation,
        onOrientationChange = { newOrientation ->
            orientation = newOrientation
            (context as? Activity)?.requestedOrientation = when (newOrientation) {
                Configuration.ORIENTATION_LANDSCAPE -> ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                else -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }
    )
}

private class ConfigurationState(
    var currentDarkMode: Boolean,
    val onDarkModeChange: (Boolean) -> Unit,
    var orientation: Int,
    val onOrientationChange: (Int) -> Unit,
)

@Composable
private fun AppScaffold(
    title: String,
    navController: NavController,
    configuration: Configuration,
    state: ConfigurationState
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { CustomTopAppBar(title = title, navController = navController) },
        content = { padding ->
            ConfigurationContent(
                padding = padding,
                state = state,
                configuration = configuration
            )
        }
    )
}

@Composable
private fun ConfigurationContent(
    padding: PaddingValues,
    state: ConfigurationState,
    configuration: Configuration
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConfigurationHeader()
        ConfigurationDetails(configuration, state)
        ConfigurationControls(state)
        OrientationLayout(state.orientation)
    }
}

@Composable
private fun ConfigurationControls(state: ConfigurationState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InteractiveSwitch(
            label = "Dark Mode",
            checked = state.currentDarkMode,
            onCheckedChange = state.onDarkModeChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        OrientationToggleButton(state)
    }
}

@Composable
private fun OrientationToggleButton(state: ConfigurationState) {
    Button(onClick = {
        val newOrientation = if (state.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Configuration.ORIENTATION_LANDSCAPE
        } else {
            Configuration.ORIENTATION_PORTRAIT
        }
        state.onOrientationChange(newOrientation)
    }) {
        Text("Toggle Orientation")
    }
}

@Composable
private fun OrientationLayout(orientation: Int) {
    when (orientation) {
        Configuration.ORIENTATION_PORTRAIT -> PortraitLayout()
        Configuration.ORIENTATION_LANDSCAPE -> LandscapeLayout()
    }
}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
private fun ConfigurationDetails(configuration: Configuration, state: ConfigurationState) {
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val density = LocalDensity.current.density

    Column {
        ConfigurationItem("Screen Width", screenWidth.toString())
        ConfigurationItem("Screen Height", screenHeight.toString())
        ConfigurationItem("Orientation", if (state.orientation == Configuration.ORIENTATION_LANDSCAPE) "Landscape" else "Portrait")
        ConfigurationItem("Night Mode", if (state.currentDarkMode) "Enabled" else "Disabled")
        ConfigurationItem("Screen Density", "${density}x")
    }
}

@Composable
private fun ConfigurationItem(label: String, value: String) {
    Text(
        text = "$label: $value",
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
private fun ConfigurationHeader() {
    Text(
        text = "Device Configuration",
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(vertical = 16.dp)
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