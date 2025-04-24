package com.droidcon.composablebank.ui.feedback_loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.droidcon.composablebank.components.InteractiveColorPicker
import com.droidcon.composablebank.components.InteractiveSlider
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.utils.CustomTopAppBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BadgeIndicator(navController: NavController, name: String) {
    var badgeVisible by remember { mutableStateOf(true) }
    var badgeColor by remember { mutableStateOf(Color.Red) }
    var badgeNumber by remember { mutableFloatStateOf(99f) }

    Scaffold(
        topBar = { CustomTopAppBar(title = name, navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ControlsSection(
                badgeVisible = badgeVisible,
                badgeColor = badgeColor,
                badgeNumber = badgeNumber,
                onBadgeVisibleChange = { badgeVisible = it },
                onColorChange = { badgeColor = it },
                onNumberChange = { badgeNumber = it }
            )

            BadgeSection(
                badgeVisible = badgeVisible,
                badgeColor = badgeColor,
                badgeNumber = badgeNumber.toInt()
            )
        }
    }
}

@Composable
private fun ControlsSection(
    badgeVisible: Boolean,
    badgeColor: Color,
    badgeNumber: Float,
    onBadgeVisibleChange: (Boolean) -> Unit,
    onColorChange: (Color) -> Unit,
    onNumberChange: (Float) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        InteractiveSwitch(
            label = "Show Badge",
            checked = badgeVisible,
            onCheckedChange = onBadgeVisibleChange
        )

        InteractiveColorPicker(
            label = "Badge Color",
            selectedColor = badgeColor,
            onColorSelected = onColorChange
        )

        InteractiveSlider(
            label = "Badge Number",
            value = badgeNumber,
            onValueChange = onNumberChange,
            valueRange = 0f..1100f
        )
    }
}

@Composable
private fun BadgeSection(
    badgeVisible: Boolean,
    badgeColor: Color,
    badgeNumber: Int
) {
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        BadgedBox(
            badgeVisible = badgeVisible,
            badgeColor = badgeColor,
            badgeNumber = badgeNumber
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notifications",
                modifier = Modifier.size(48.dp)
            )
        }

        Text("Size Examples", modifier = Modifier.padding(vertical = 30.dp),
            style = MaterialTheme.typography.headlineSmall,)

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Small Badge", modifier = Modifier.padding(end = 16.dp))
            SmallBadgedIcon(
                icon = Icons.Default.Notifications,
                description = "Notifications",
                isVisible = true
            )
        }

        Row(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Large Badges")

            BadgedBox(
                badgeVisible = true,
                badgeColor = Color.Red,
                badgeNumber = 0
            ) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email",
                    modifier = Modifier.size(48.dp)
                )
            }
            BadgedBox(
                badgeVisible = true,
                badgeColor = MaterialTheme.colorScheme.primary,
                badgeNumber = 1500
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    modifier = Modifier.size(48.dp)
                )
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            BadgedBox(
                badgeVisible = true,
                badgeColor = MaterialTheme.colorScheme.secondary,
                badgeNumber = 12
            ) {
                Text("Messages", style = MaterialTheme.typography.bodyLarge)
            }

            BadgedBox(
                badgeVisible = true,
                badgeColor = MaterialTheme.colorScheme.error,
                badgeNumber = 9
            ) {
                Text("Alerts", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BadgedBox(
    badgeVisible: Boolean,
    badgeColor: Color,
    badgeNumber: Int,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier.padding(24.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        content()

        if (badgeVisible) {
            Badge(
                modifier = Modifier.offset(x = 8.dp, y = (-8).dp),
                containerColor = badgeColor,
                contentColor = Color.White
            ) {
                val formattedNumber = when {
                    badgeNumber > 999 -> "999+"
                    badgeNumber == 100 -> "99+"
                    else -> badgeNumber.toString()
                }

                if (formattedNumber.isEmpty()) {
                    DotIndicator(size = 16.dp)
                } else {
                    Text(text = formattedNumber)
                }
            }
        }
    }
}

@Composable
private fun SmallBadgedIcon(
    icon: ImageVector,
    description: String,
    isVisible: Boolean
) {
    Box(modifier = Modifier.size(48.dp)) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            modifier = Modifier.align(Alignment.Center).size(43.dp)
        )

        if (isVisible) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-3).dp, y = 3.dp)
                    .size(12.dp)
                    .background(Color.Red, CircleShape)
            )
        }
    }
}

@Composable
private fun DotIndicator(size: Dp) {
    Canvas(modifier = Modifier.size(size)) {
        drawCircle(color = Color.White, radius = size.toPx() / 2)
    }
}