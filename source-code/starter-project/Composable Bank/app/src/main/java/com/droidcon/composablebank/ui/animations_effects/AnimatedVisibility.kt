package com.droidcon.composablebank.ui.animations_effects

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.droidcon.composablebank.utils.CustomTopAppBar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.animation.ExperimentalAnimationApi

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedVisibility(navController: NavController, name: String) {

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
private fun FadeAnimation(isVisible: Boolean) {

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SlideHorizontalAnimation(isVisible: Boolean) {

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SlideVerticalAnimation(isVisible: Boolean) {

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun ScaleAnimation(isVisible: Boolean) {

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun ExpandShrinkAnimation(isVisible: Boolean) {

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun CustomTransitionAnimation(isVisible: Boolean) {

}