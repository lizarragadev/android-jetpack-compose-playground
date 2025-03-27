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
import com.droidcon.composablebank.data.ComposableItem
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
            navController = navController
        )

        DetailContent(composable, navController)
    }
}

@Composable
private fun DetailContent(composable: ComposableItem?, navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        if (composable != null) {
            ComposableHeader(composable)

            Spacer(modifier = Modifier.height(16.dp))
            SectionTitle("Common use:")
            SectionText(composable.commonUse)

            Spacer(modifier = Modifier.height(16.dp))
            SectionTitle("Properties:")
            PropertyList(properties = composable.properties)

            Spacer(modifier = Modifier.height(30.dp))
            ViewDemoButton(composable.name, navController)
        } else {
            Text(text = "Composable not found")
        }
    }
}

@Composable
private fun ComposableHeader(composable: ComposableItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            text = composable.name,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        SectionText(composable.description)
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
private fun SectionText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
private fun ViewDemoButton(composableName: String, navController: NavController) {
    InteractiveButton(
        text = "View Demo",
        onClick = { navController.navigate(Destinations.demoRoute(composableName)) },
        modifier = Modifier.fillMaxWidth()
    )
}