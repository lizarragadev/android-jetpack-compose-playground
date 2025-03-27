@file:OptIn(ExperimentalMaterial3Api::class)

package com.droidcon.composablebank.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String,
    navController: NavController? = null,
    onBackClick: () -> Unit = { navController?.navigateUp() }
) {
    val showBackButton = navController?.previousBackStackEntry != null

    TopAppBar(
        title = { AppBarTitle(title) },
        navigationIcon = { NavigationBackButton(showBackButton, onBackClick) },
        colors = topAppBarColors()
    )
}

@Composable
private fun AppBarTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
private fun NavigationBackButton(
    visible: Boolean,
    onClick: () -> Unit
) {
    if (visible) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun topAppBarColors() = TopAppBarDefaults.topAppBarColors(
    containerColor = MaterialTheme.colorScheme.primary,
    titleContentColor = MaterialTheme.colorScheme.onPrimary,
    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
)