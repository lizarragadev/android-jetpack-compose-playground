package com.droidcon.composablebank.ui.detail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.droidcon.composablebank.components.InteractiveButton
import com.droidcon.composablebank.data.DataProvider
import com.droidcon.composablebank.navigation.Destinations
import com.droidcon.composablebank.utils.CustomTopAppBar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ComposableDetailScreen(composableName: String?, navController: NavController) {
    val composable = DataProvider.composables.find { it.name == composableName }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CustomTopAppBar(
            title = composable?.name ?: "Detail",
            navController = navController)

        Column(modifier = Modifier.padding(16.dp)) {
            if (composable != null) {
                Text(
                    text = composable.name,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.align(CenterHorizontally))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = composable.description, style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Common use:", style = MaterialTheme.typography.titleMedium)
                Text(text = composable.commonUse, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Properties:", style = MaterialTheme.typography.titleMedium)
                PropertyList(properties = composable.properties)

                Spacer(modifier = Modifier.height(30.dp))
                InteractiveButton(
                    text = "View Demo",
                    onClick = {
                        navController.navigate(Destinations.demoRoute(composable.name))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text(text = "Composable not found")
            }
        }
    }
}