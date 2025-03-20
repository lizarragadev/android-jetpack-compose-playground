package com.droidcon.composablebank.ui.input_controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.droidcon.composablebank.components.InteractiveSwitch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBars(navController: NavController, name: String) {
    var isEnabled by remember { mutableStateOf(true) }

    val fakeSuggestions = listOf(
        "Jetpack Compose", "Material Design", "Android Development",
        "Kotlin Programming", "UI/UX Design", "Flutter Framework",
        "Google Developers", "Jetpack Compose Desktop", "Compose for Web",
        "Compose for Wear OS", "Compose for Android TV", "Compose for Chrome OS",
        "Compose for iOS", "Compose for macOS", "Compose for Windows",
        "Compose for Linux", "Compose for Raspberry Pi", "Compose for Arduino"
    )


    Scaffold(
        topBar = {


        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {



                    Spacer(Modifier.height(8.dp))

                    InteractiveSwitch(
                        label = "Enabled",
                        checked = isEnabled,
                        onCheckedChange = { isEnabled = it }
                    )
                }

                Spacer(Modifier.height(24.dp))

                Text(
                    "Main Content",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    )
}