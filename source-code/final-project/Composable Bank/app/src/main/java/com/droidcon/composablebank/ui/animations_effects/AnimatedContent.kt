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

    AnimatedContentScaffold(
        navController = navController,
        screenName = name,
        targetState = targetState,
        animationType = animationType,
        onStateChange = { targetState = it },
        onAnimationTypeChange = { animationType = it }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun AnimatedContentScaffold(
    navController: NavController,
    screenName: String,
    targetState: String,
    animationType: String,
    onStateChange: (String) -> Unit,
    onAnimationTypeChange: (String) -> Unit
) {
    Scaffold(
        topBar = { CustomTopAppBar(title = screenName, navController = navController) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimationSelectionSection(
                    animationType = animationType,
                    onTypeSelected = onAnimationTypeChange,
                    currentState = targetState,
                    onToggleState = onStateChange
                )

                AnimationPreviewSection(
                    targetState = targetState,
                    animationType = animationType
                )
            }
        }
    )
}

@Composable
private fun AnimationSelectionSection(
    animationType: String,
    onTypeSelected: (String) -> Unit,
    currentState: String,
    onToggleState: (String) -> Unit
) {
    Spacer(modifier = Modifier.height(32.dp))
    AnimationTypeSelector(
        selectedType = animationType,
        onTypeSelected = onTypeSelected
    )
    Spacer(modifier = Modifier.height(24.dp))
    ToggleStateButton(
        currentState = currentState,
        onToggle = onToggleState
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun AnimationTypeSelector(
    selectedType: String,
    onTypeSelected: (String) -> Unit
) {
    InteractiveRadioButtonGroup(
        options = listOf(
            "Fade",
            "Slide",
            "Scale",
            "Expand/Shrink",
            "Size Transform",
            "Custom Transition"
        ),
        selectedOption = selectedType,
        onOptionSelected = onTypeSelected
    )
}

@Composable
private fun ToggleStateButton(
    currentState: String,
    onToggle: (String) -> Unit
) {
    Button(onClick = { onToggle(if (currentState == "State1") "State2" else "State1") }) {
        Text(text = "Toggle State")
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun AnimationPreviewSection(
    targetState: String,
    animationType: String
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        when (animationType) {
            "Fade" -> FadeTransition(targetState)
            "Slide" -> SlideTransition(targetState)
            "Scale" -> ScaleTransition(targetState)
            "Expand/Shrink" -> ExpandShrinkTransition(targetState)
            "Size Transform" -> SizeTransformTransition(targetState)
            "Custom Transition" -> CustomTransition(targetState)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun FadeTransition(targetState: String) {
    AnimatedContent(
        targetState = targetState,
        transitionSpec = {
            fadeIn(animationSpec = tween(1500)) togetherWith
                    fadeOut(animationSpec = tween(1500))
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
            slideInHorizontally(animationSpec = tween(1500)) { -it } togetherWith
                    slideOutHorizontally(animationSpec = tween(1500)) { it }
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
            scaleIn(initialScale = 0.8f, animationSpec = tween(1500)) togetherWith
                    scaleOut(targetScale = 0.8f, animationSpec = tween(1500))
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
            expandIn(
                expandFrom = Alignment.Center,
                animationSpec = tween(1500)
            ) togetherWith
                    shrinkOut(
                        shrinkTowards = Alignment.Center,
                        animationSpec = tween(1500)
                    )
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
        transitionSpec = {
            scaleIn(
                initialScale = 0.5f,
                animationSpec = tween(durationMillis = 1500, easing = LinearOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(1000)) togetherWith
                    scaleOut(
                        targetScale = 1.5f,
                        animationSpec = tween(durationMillis = 1500, easing = FastOutLinearInEasing)
                    ) + fadeOut(animationSpec = tween(1000)) using
                    SizeTransform(clip = false)
        },
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
            (fadeIn(animationSpec = tween(1500)) +
                    scaleIn(initialScale = 0.5f, animationSpec = tween(1500))) togetherWith
                    (fadeOut(animationSpec = tween(1500)) +
                            scaleOut(targetScale = 0.9f, animationSpec = tween(1500)))
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