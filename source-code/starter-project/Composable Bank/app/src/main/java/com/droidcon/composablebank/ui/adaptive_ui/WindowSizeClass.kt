package com.droidcon.composablebank.ui.adaptive_ui

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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.DesktopWindows
import androidx.compose.material.icons.filled.TabletAndroid
import com.droidcon.composablebank.utils.CustomTopAppBar

@Composable
fun WindowSizeClass(navController: NavController, name: String) {

    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme(),
        content = {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    CustomTopAppBar(title = name, navController = navController)
                },
                content = { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {


                    }
                }
            )
        }
    )
}


@Composable
private fun CompactLayout() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red.copy(alpha = 0.5f))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Compact Layout",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Suitable for small phones.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
    }
}

@Composable
private fun MediumLayout() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Green.copy(alpha = 0.5f))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Medium Layout",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Suitable for tablets or medium devices.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }
        Icon(
            imageVector = Icons.Default.TabletAndroid,
            contentDescription = "Tablet",
            tint = Color.White,
            modifier = Modifier.size(100.dp)
        )
    }
}

@Composable
private fun ExpandedLayout() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue.copy(alpha = 0.5f))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.DesktopWindows,
            contentDescription = "Desktop",
            tint = Color.White,
            modifier = Modifier.size(150.dp)
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Expanded Layout",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Suitable for large screens such as desktops.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }
    }
}