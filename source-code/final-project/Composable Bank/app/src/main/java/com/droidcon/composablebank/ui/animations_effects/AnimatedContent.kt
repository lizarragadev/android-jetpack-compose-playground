package com.droidcon.composablebank.ui.animations_effects

import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import com.droidcon.composablebank.components.InteractiveRadioButtonGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.res.painterResource
import com.droidcon.composablebank.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContent(navController: NavController, name: String) {
    var targetState by remember { mutableStateOf("State1") }
    var animationType by remember { mutableStateOf("Fade") }

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
                InteractiveRadioButtonGroup(
                    options = listOf(
                        "Fade",
                        "Slide",
                        "Scale",
                        "Expand/Shrink",
                        "Size Transform",
                        "Custom Transition"
                    ),
                    selectedOption = animationType,
                    onOptionSelected = { animationType = it }
                )
                Spacer(modifier = Modifier.height(24.dp))

                Button(onClick = {
                    targetState = if (targetState == "State1") "State2" else "State1"
                }) {
                    Text(text = "Toggle State")
                }
                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    when (animationType) {
                        "fade" -> FadeTransition(targetState)
                        "slide" -> SlideTransition(targetState)
                        "scale" -> ScaleTransition(targetState)
                        "expand/shrink" -> ExpandShrinkTransition(targetState)
                        "sizetransform" -> SizeTransformTransition(targetState)
                        "customtransition" -> CustomTransition(targetState)
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun FadeTransition(targetState: String) {
    AnimatedContent(
        targetState = targetState,
        transitionSpec = {
            fadeIn(animationSpec = tween(500)) togetherWith fadeOut(animationSpec = tween(500))
        },
        label = "FadeTransition"
    ) { currentState ->
        Content(currentState)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SlideTransition(targetState: String) {
    AnimatedContent(
        targetState = targetState,
        transitionSpec = {
            slideInHorizontally(animationSpec = tween(500)) { fullWidth -> -fullWidth } togetherWith
                    slideOutHorizontally(animationSpec = tween(500)) { fullWidth -> fullWidth }
        },
        label = "SlideTransition"
    ) { currentState ->
        Content(currentState)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun ScaleTransition(targetState: String) {
    AnimatedContent(
        targetState = targetState,
        transitionSpec = {
            scaleIn(initialScale = 0.8f, animationSpec = tween(500)) togetherWith
                    scaleOut(targetScale = 0.8f, animationSpec = tween(500))
        },
        label = "ScaleTransition"
    ) { currentState ->
        Content(currentState)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun ExpandShrinkTransition(targetState: String) {
    AnimatedContent(
        targetState = targetState,
        transitionSpec = {
            expandIn(expandFrom = Alignment.Center, animationSpec = tween(500)) togetherWith
                    shrinkOut(shrinkTowards = Alignment.Center, animationSpec = tween(500))
        },
        label = "ExpandShrinkTransition"
    ) { currentState ->
        Content(currentState)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SizeTransformTransition(targetState: String) {
    AnimatedContent(
        targetState = targetState,

        label = "SizeTransformTransition"
    ) { currentState ->
        Content(currentState)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun CustomTransition(targetState: String) {
    AnimatedContent(
        targetState = targetState,
        transitionSpec = {
            fadeIn(animationSpec = tween(500)) + scaleIn(initialScale = 0.5f) togetherWith
                    fadeOut(animationSpec = tween(500)) + scaleOut(targetScale = 0.9f)
        },
        label = "CustomTransition"
    ) { currentState ->
        Content(currentState)
    }
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