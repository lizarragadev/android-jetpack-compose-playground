package com.droidcon.composablebank.ui.animations_effects

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.droidcon.composablebank.utils.CustomTopAppBar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.*
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.res.painterResource
import com.droidcon.composablebank.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContent(navController: NavController, name: String) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopAppBar(title = name, navController = navController)
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))



                Spacer(modifier = Modifier.height(24.dp))



                Spacer(modifier = Modifier.height(16.dp))


            }
        }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun FadeTransition(targetState: String) {

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SlideTransition(targetState: String) {

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun ScaleTransition(targetState: String) {

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun ExpandShrinkTransition(targetState: String) {

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SizeTransformTransition(targetState: String) {

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun CustomTransition(targetState: String) {

}

@Composable
private fun Content(state: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.droidcon),
            contentDescription = "Sample Image",
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Current State: $state",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}