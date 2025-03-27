package com.droidcon.composablebank

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.droidcon.composablebank.navigation.NavGraph
import com.droidcon.composablebank.theme.ComposableBankTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupComposableContent()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupComposableContent() {
        setContent {
            ComposableBankApp()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ComposableBankApp() {
    ComposableBankTheme {
        AppSurfaceContent()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun AppSurfaceContent() {
    Surface(color = MaterialTheme.colorScheme.background) {
        val navController = rememberNavController()
        NavGraph(navController = navController)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PreviewComposableBankApp()
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun PreviewComposableBankApp() {
    ComposableBankTheme {
        PreviewSurfaceContent()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun PreviewSurfaceContent() {
    Surface(color = MaterialTheme.colorScheme.background) {
        val navController = rememberNavController()
        NavGraph(navController = navController)
    }
}