package com.droidcon.composablebank.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.droidcon.composablebank.components.DemoContainer
import com.droidcon.composablebank.ui.detail.ComposableDetailScreen
import com.droidcon.composablebank.ui.home.HomeScreen
import com.droidcon.composablebank.utils.Constants

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Constants.HOME_ROUTE) {

    }
}