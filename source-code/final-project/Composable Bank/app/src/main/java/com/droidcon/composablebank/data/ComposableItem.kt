package com.droidcon.composablebank.data

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

data class ComposableItem(
    val name: String,
    val description: String,
    val category: String,
    val commonUse: String,
    val properties: List<String>,
    val demo: @Composable (NavController, String) -> Unit
)