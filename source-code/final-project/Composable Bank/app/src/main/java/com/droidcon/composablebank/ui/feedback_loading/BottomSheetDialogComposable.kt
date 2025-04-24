package com.droidcon.composablebank.ui.feedback_loading

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Backup
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DataUsage
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NetworkWifi
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.droidcon.composablebank.components.InteractiveButton
import com.droidcon.composablebank.components.InteractiveColorPicker
import com.droidcon.composablebank.components.InteractiveDropdown
import com.droidcon.composablebank.components.InteractiveRadioButtonGroup
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.components.InteractiveTextField
import com.droidcon.composablebank.utils.CustomTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BottomSheetDialogComposable(navController: NavController, name: String) {
    var showBasicSheet by remember { mutableStateOf(false) }
    var showFormSheet by remember { mutableStateOf(false) }
    var showCustomSheet by remember { mutableStateOf(false) }

    var sheetBackgroundColor by remember { mutableStateOf(Color.White) }

    val basicSheetState = rememberModalBottomSheetState()
    val formSheetState = rememberModalBottomSheetState()
    val customSheetState = rememberModalBottomSheetState()

    Scaffold(
        topBar = { CustomTopAppBar(title = name, navController = navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InteractiveColorPicker(
                label = "Sheet Background",
                selectedColor = sheetBackgroundColor,
                onColorSelected = { sheetBackgroundColor = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            InteractiveButton(
                text = "Show Basic Sheet",
                onClick = { showBasicSheet = true }
            )
            Spacer(modifier = Modifier.height(24.dp))
            InteractiveButton(
                text = "Show Form Sheet",
                onClick = { showFormSheet = true }
            )
            Spacer(modifier = Modifier.height(24.dp))
            InteractiveButton(
                text = "Show Custom Sheet",
                onClick = { showCustomSheet = true }
            )
        }
    }

    if (showBasicSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBasicSheet = false },
            sheetState = basicSheetState,
            containerColor = sheetBackgroundColor
        ) {
            BasicSheetContent(
                onDismiss = { showBasicSheet = false }
            )
        }
    }

    if (showFormSheet) {
        ModalBottomSheet(
            onDismissRequest = { showFormSheet = false },
            sheetState = formSheetState,
            containerColor = sheetBackgroundColor
        ) {
            FormSheetContent(
                sheetState = formSheetState,
                onDismiss = { showFormSheet = false }
            )
        }
    }

    if (showCustomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showCustomSheet = false },
            sheetState = customSheetState,
            containerColor = sheetBackgroundColor,
            modifier = Modifier.fillMaxHeight()
        ) {
            CustomSheetContent(
                onDismiss = { showCustomSheet = false }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BasicSheetContent(
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text("Basic Settings", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        InteractiveSwitch(
            label = "Dark Mode",
            checked = false,
            onCheckedChange = {}
        )

        InteractiveColorPicker(
            label = "Theme Color",
            selectedColor = MaterialTheme.colorScheme.primary,
            onColorSelected = {}
        )

        Button(
            onClick = onDismiss,
            modifier = Modifier.fillMaxWidth().padding(top = 30.dp)
        ) {
            Text("Close")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FormSheetContent(
    sheetState: SheetState,
    onDismiss: () -> Unit
) {
    var textFieldValue by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf("Option 1") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("Contact Form",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 30.dp))

            InteractiveTextField(
                value = textFieldValue,
                onValueChange = { textFieldValue = it },
                label = "Name"
            )

            InteractiveRadioButtonGroup(
                options = listOf("Option 1", "Option 2", "Option 3"),
                selectedOption = selectedOption,
                onOptionSelected = { selectedOption = it }
            )

            InteractiveDropdown(
                options = listOf("High", "Medium", "Low"),
                selectedOption = "Medium",
                onOptionSelected = {},
                label = "Priority"
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 30.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel")
                }

                Button(
                    onClick = {  },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Submit")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomSheetContent(
    onDismiss: () -> Unit
) {
    val settingsItems = remember {
        listOf(
            SettingItem(Icons.Default.Notifications, "Notifications", "Manage notifications"),
            SettingItem(Icons.Default.DarkMode, "Dark Mode", "Enable dark theme"),
            SettingItem(Icons.Default.Palette, "Theme Color", "Select app color scheme"),
            SettingItem(Icons.Default.Security, "Security", "Two-factor authentication"),
            SettingItem(Icons.Default.Storage, "Storage", "Manage storage usage"),
            SettingItem(Icons.Default.Language, "Language", "App language settings"),
            SettingItem(Icons.Default.Info, "About", "App version and info"),
            SettingItem(Icons.Default.Wallpaper, "Appearance", "Change wallpaper and theme"),
            SettingItem(Icons.Default.AccountCircle, "Account", "Manage profile and security"),
            SettingItem(Icons.Default.NotificationsActive, "Alerts", "Configure alerts and sounds"),
            SettingItem(Icons.Default.DataUsage, "Data Usage", "Monitor data consumption"),
            SettingItem(Icons.Default.PrivacyTip, "Privacy", "Privacy settings and controls"),
            SettingItem(Icons.Default.Backup, "Backup", "Cloud backup options"),
            SettingItem(Icons.Default.Sync, "Sync", "Synchronization settings"),
            SettingItem(Icons.Default.Accessibility, "Accessibility", "Display and interaction options"),
            SettingItem(Icons.Default.BatteryFull, "Battery", "Power saving modes"),
            SettingItem(Icons.Default.NetworkWifi, "Network", "Wi-Fi and connection settings"),
            SettingItem(Icons.Default.Bluetooth, "Bluetooth", "Manage Bluetooth devices"),
            SettingItem(Icons.Default.LocationOn, "Location", "Location services settings")
        )
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(
            text = "Application Settings",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(settingsItems.size) { index ->
                SettingListItem(settingsItems[index])
                if (index < settingsItems.lastIndex) {
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.outlineVariant,
                        thickness = 0.8.dp
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        InteractiveButton(
            text = "Close Settings",
            onClick = onDismiss,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun SettingListItem(item: SettingItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {  }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

data class SettingItem(
    val icon: ImageVector,
    val title: String,
    val description: String
)
