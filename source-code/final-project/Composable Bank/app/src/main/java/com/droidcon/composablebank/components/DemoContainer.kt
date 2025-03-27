package com.droidcon.composablebank.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.droidcon.composablebank.data.ComposableItem
import com.droidcon.composablebank.data.DataProvider

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DemoContainer(composableName: String?, navController: NavController) {
    val composable = DataProvider.composables.find { it.name == composableName }

    ContainerBackground {
        when {
            composable != null -> ShowComposableDemo(composable, navController)
            else -> ShowErrorMessage()
        }
    }
}

@Composable
private fun ContainerBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.error)
    ) {
        content()
    }
}

@Composable
private fun ShowComposableDemo(
    composable: ComposableItem,
    navController: NavController
) {
    composable.demo(navController, composable.name)
}

@Composable
private fun ShowErrorMessage() {
    Text(
        text = "Demo not found",
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}