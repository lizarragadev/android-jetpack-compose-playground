package com.droidcon.composablebank.ui.dev_tools

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.droidcon.composablebank.components.InteractiveColorPicker
import com.droidcon.composablebank.components.InteractiveDropdown
import com.droidcon.composablebank.components.InteractiveRadioButtonGroup
import com.droidcon.composablebank.components.InteractiveSlider
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.utils.CustomTopAppBar

@Composable
internal fun ComposePreviewTool(navController: NavController, name: String) {
    var previewBackground by remember { mutableStateOf(Color.Cyan) }
    var showSystemUI by remember { mutableStateOf(false) }
    var deviceType by remember { mutableStateOf("Phone") }
    var fontScale by remember { mutableFloatStateOf(1f) }
    var locale by remember { mutableStateOf("en") }
    var darkTheme by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { CustomTopAppBar(title = name, navController = navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            PreviewConfigurationSection(
                previewBackground = previewBackground,
                showSystemUI = showSystemUI,
                deviceType = deviceType,
                fontScale = fontScale,
                locale = locale,
                darkTheme = darkTheme,
                onBackgroundChange = { previewBackground = it },
                onSystemUIChange = { showSystemUI = it },
                onDeviceTypeChange = { deviceType = it },
                onFontScaleChange = { fontScale = it },
                onLocaleChange = { locale = it },
                onDarkThemeChange = { darkTheme = it }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) {
                PreviewDemoArea(
                    previewBackground = previewBackground,
                    showSystemUI = showSystemUI,
                    deviceType = deviceType,
                    fontScale = fontScale,
                    locale = locale,
                    darkTheme = darkTheme
                )
            }

            CodeGenerationSection(
                previewBackground = previewBackground,
                showSystemUI = showSystemUI,
                deviceType = deviceType,
                fontScale = fontScale,
                locale = locale,
                darkTheme = darkTheme
            )
        }
    }
}

@Composable
private fun PreviewConfigurationSection(
    previewBackground: Color,
    showSystemUI: Boolean,
    deviceType: String,
    fontScale: Float,
    locale: String,
    darkTheme: Boolean,
    onBackgroundChange: (Color) -> Unit,
    onSystemUIChange: (Boolean) -> Unit,
    onDeviceTypeChange: (String) -> Unit,
    onFontScaleChange: (Float) -> Unit,
    onLocaleChange: (String) -> Unit,
    onDarkThemeChange: (Boolean) -> Unit
) {
    Surface(
        modifier = Modifier.padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Preview Configuration", style = MaterialTheme.typography.headlineSmall)

            InteractiveColorPicker(
                label = "Preview Background",
                selectedColor = previewBackground,
                onColorSelected = onBackgroundChange
            )

            InteractiveSwitch(
                label = "Show System UI",
                checked = showSystemUI,
                onCheckedChange = onSystemUIChange
            )

            InteractiveDropdown(
                options = listOf("Phone", "Tablet", "Foldable", "Desktop"),
                selectedOption = deviceType,
                onOptionSelected = onDeviceTypeChange,
                label = "Device Type"
            )

            InteractiveSlider(
                label = "Font Scale: ${fontScale}x",
                value = fontScale,
                onValueChange = onFontScaleChange,
                valueRange = 0.5f..2f
            )

            InteractiveRadioButtonGroup(
                options = listOf("en", "es", "fr", "ar"),
                selectedOption = locale,
                onOptionSelected = onLocaleChange
            )

            InteractiveSwitch(
                label = "Dark Theme",
                checked = darkTheme,
                onCheckedChange = onDarkThemeChange
            )
        }
    }
}

@Composable
private fun PreviewDemoArea(
    previewBackground: Color,
    showSystemUI: Boolean,
    deviceType: String,
    fontScale: Float,
    locale: String,
    darkTheme: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(previewBackground)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Preview Demo",
                style = MaterialTheme.typography.headlineMedium,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize * fontScale
            )
            Text("Device: $deviceType")
            Text("Locale: $locale")
            Text("Theme: ${if (darkTheme) "Dark" else "Light"}")
            if (showSystemUI) {
                Text("System UI: Visible")
            }
        }
    }
}

@Composable
private fun CodeGenerationSection(
    previewBackground: Color,
    showSystemUI: Boolean,
    deviceType: String,
    fontScale: Float,
    locale: String,
    darkTheme: Boolean
) {
    Surface(
        modifier = Modifier.padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Preview Annotation Code", style = MaterialTheme.typography.headlineSmall)

            val code = buildString {
                appendLine("@Preview(")
                appendLine("    name = \"$deviceType Preview\",")
                appendLine("    showBackground = true,")
                appendLine("    backgroundColor = ${previewBackground.value.toLong()},")
                appendLine("    showSystemUi = $showSystemUI,")
                appendLine("    device = \"${getDeviceSpec(deviceType)}\",")
                appendLine("    fontScale = ${fontScale}f,")
                appendLine("    locale = \"$locale\",")
                appendLine("    uiMode = ${if (darkTheme) "Configuration.UI_MODE_NIGHT_YES" else "Configuration.UI_MODE_NIGHT_NO"}")
                appendLine(")")
            }

            Text(
                text = code,
                style = MaterialTheme.typography.bodySmall,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f))
                    .padding(8.dp)
            )
        }
    }
}

private fun getDeviceSpec(deviceType: String): String {
    return when (deviceType) {
        "Phone" -> "spec:width=411dp,height=891dp"
        "Tablet" -> "spec:width=1280dp,height=800dp"
        "Foldable" -> "spec:width=673dp,height=841dp"
        "Desktop" -> "spec:width=1920dp,height=1080dp"
        else -> ""
    }
}

@Preview(
    name = "Compose-Preview",
    group = "Compose",
    widthDp = 411,
    heightDp = 1300,
    locale = "en",
    fontScale = 1f,
    showSystemUi = false,
    showBackground = true,
    backgroundColor = 0xFFABCDEF,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_5,
    wallpaper = Wallpapers.NONE
)
@Composable
private fun ComposePreviewToolPreview() {
    MaterialTheme {
        ComposePreviewTool(
            navController = rememberNavController(),
            name = "Preview Tool"
        )
    }
}