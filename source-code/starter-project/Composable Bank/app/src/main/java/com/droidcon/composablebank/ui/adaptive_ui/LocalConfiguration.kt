package com.droidcon.composablebank.ui.adaptive_ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.droidcon.composablebank.utils.CustomTopAppBar

@Composable
fun LocalConfiguration(navController: NavController, name: String) {

    MaterialTheme(
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



                        Spacer(modifier = Modifier.height(16.dp))



                        Spacer(modifier = Modifier.height(8.dp))



                        Spacer(modifier = Modifier.height(16.dp))


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