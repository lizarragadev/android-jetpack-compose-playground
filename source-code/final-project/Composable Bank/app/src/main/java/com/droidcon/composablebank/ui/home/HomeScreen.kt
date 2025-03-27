package com.droidcon.composablebank.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.droidcon.composablebank.data.DataProvider
import com.droidcon.composablebank.utils.CustomTopAppBar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(navController: NavController) {
    HomeContent(navController = navController)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun HomeContent(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ScreenHeader()
        ComposablesList(navController = navController)
    }
}

@Composable
private fun ScreenHeader() {
    CustomTopAppBar(title = "Composable Bank")
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ComposablesList(navController: NavController) {
    val groupedComposables = DataProvider.composables.groupBy { it.category }
    groupedComposables.forEach { (category, items) ->
        CategoryItem(
            categoryName = category,
            items = items,
            navController = navController
        )
    }
}