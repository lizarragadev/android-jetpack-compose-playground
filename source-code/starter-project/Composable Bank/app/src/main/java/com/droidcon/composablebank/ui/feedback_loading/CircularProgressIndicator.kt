package com.droidcon.composablebank.ui.feedback_loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.navigation.NavController
import com.droidcon.composablebank.utils.CustomTopAppBar

@Composable
fun CircularProgressIndicator(navController: NavController, name: String) {

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = name,
                navController = navController
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$name Examples",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )


                    Spacer(modifier = Modifier.height(8.dp))



                    Spacer(modifier = Modifier.height(8.dp))



                    Spacer(modifier = Modifier.height(8.dp))



                    Spacer(modifier = Modifier.height(16.dp))



                    Spacer(modifier = Modifier.height(30.dp))


                }
            }
        }
    )
}