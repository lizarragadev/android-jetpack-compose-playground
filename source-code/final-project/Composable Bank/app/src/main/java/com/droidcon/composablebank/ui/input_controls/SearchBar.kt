package com.droidcon.composablebank.ui.input_controls

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.droidcon.composablebank.components.InteractiveSwitch
import androidx.compose.material.icons.automirrored.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBars(navController: NavController, name: String) {
    var query by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var showLeadingIcon by remember { mutableStateOf(true) }
    var isEnabled by remember { mutableStateOf(true) }

    val fakeSuggestions = listOf(
        "Jetpack Compose", "Material Design", "Android Development",
        "Kotlin Programming", "UI/UX Design", "Flutter Framework",
        "Google Developers", "Jetpack Compose Desktop", "Compose for Web",
        "Compose for Wear OS", "Compose for Android TV", "Compose for Chrome OS",
        "Compose for iOS", "Compose for macOS", "Compose for Windows",
        "Compose for Linux", "Compose for Raspberry Pi", "Compose for Arduino"
    )

    val filteredSuggestions = remember(query) {
        if (query.isNotEmpty()) {
            fakeSuggestions.filter { it.contains(query, ignoreCase = true) }
        } else {
            fakeSuggestions
        }
    }

    Scaffold(
        topBar = {
            SearchBar(
                expanded = expanded,
                onExpandedChange = { isActive ->
                    if (isEnabled) expanded = isActive
                },
                modifier = if (!expanded) {
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                } else {
                    Modifier.fillMaxWidth()
                },
                inputField = {
                    SearchBarDefaults.InputField(
                        query = query,
                        onQueryChange = { newValue ->
                            if (isEnabled) query = newValue
                        },
                        onSearch = {
                            if (isEnabled) println("Searching for: $query")
                        },
                        expanded = expanded,
                        onExpandedChange = { isActive ->
                            if (isEnabled) expanded = isActive
                        },
                        enabled = isEnabled,
                        placeholder = { Text("Search here...") },
                        leadingIcon = if (showLeadingIcon) {
                            {
                                IconButton(onClick = {
                                    if (expanded) expanded = false
                                }) {
                                    Icon(
                                        imageVector = if (expanded) Icons.AutoMirrored.Filled.ArrowBack else Icons.Default.Search,
                                        contentDescription = if (expanded) "Back" else "Search"
                                    )
                                }
                            }
                        } else null,
                        trailingIcon = if (query.isNotEmpty()) {
                            {
                                IconButton(onClick = { query = "" }) {
                                    Icon(Icons.Default.Close, "Clear")
                                }
                            }
                        } else null,
                    )
                },
                shape = SearchBarDefaults.inputFieldShape,
                colors = SearchBarDefaults.colors(),
                tonalElevation = SearchBarDefaults.TonalElevation,
                shadowElevation = SearchBarDefaults.ShadowElevation,
                windowInsets = SearchBarDefaults.windowInsets
            ) {
                Column(Modifier.fillMaxWidth()) {
                    if (filteredSuggestions.isEmpty() && query.isNotEmpty()) {
                        Text(
                            "No suggestions found",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(12.dp)
                        )
                    } else {
                        filteredSuggestions.forEach { suggestion ->
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        query = suggestion
                                        expanded = false
                                    }
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Spacer(Modifier.width(12.dp))
                                Icon(Icons.Default.History, "Suggestion")
                                Spacer(Modifier.width(12.dp))
                                Text(suggestion, style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    }
                }
            }
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
                    InteractiveSwitch(
                        label = "Show Leading Icon",
                        checked = showLeadingIcon,
                        onCheckedChange = { showLeadingIcon = it }
                    )
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