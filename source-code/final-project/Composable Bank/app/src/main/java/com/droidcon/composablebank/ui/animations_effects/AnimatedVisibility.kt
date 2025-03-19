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
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import com.droidcon.composablebank.components.InteractiveRadioButtonGroup
import com.droidcon.composablebank.R
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedVisibility(navController: NavController, name: String) {
    var isVisible by remember { mutableStateOf(true) }
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
                        "Slide Horizontal",
                        "Slide Vertical",
                        "Scale",
                        "Expand/Shrink",
                        "Custom Transition"
                    ),
                    selectedOption = animationType,
                    onOptionSelected = { animationType = it }
                )
                Spacer(modifier = Modifier.height(24.dp))

                Button(onClick = { isVisible = !isVisible }) {
                    Text(text = if (isVisible) "Hide Content" else "Show Content")
                }
                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    when (animationType) {
                        "fade" -> FadeAnimation(isVisible)
                        "slidehorizontal" -> SlideHorizontalAnimation(isVisible)
                        "slidevertical" -> SlideVerticalAnimation(isVisible)
                        "scale" -> ScaleAnimation(isVisible)
                        "expand/shrink" -> ExpandShrinkAnimation(isVisible)
                        "customtransition" -> CustomTransitionAnimation(isVisible)
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun FadeAnimation(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(500)),
        exit = fadeOut(animationSpec = tween(500))
    ) {
        Column(
            modifier = Modifier.size(200.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.droidcon),
                contentDescription = "Sample Image",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Fade Animation", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SlideHorizontalAnimation(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally(animationSpec = tween(500)) { fullWidth -> -fullWidth },
        exit = slideOutHorizontally(animationSpec = tween(500)) { fullWidth -> fullWidth }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.droidcon),
                contentDescription = "Sample Image",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Slide Horizontal", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SlideVerticalAnimation(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(animationSpec = tween(500)) { fullHeight -> -fullHeight },
        exit = slideOutVertically(animationSpec = tween(500)) { fullHeight -> fullHeight }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.droidcon),
                contentDescription = "Sample Image",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Slide Vertical", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun ScaleAnimation(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = scaleIn(animationSpec = tween(500), initialScale = 0.5f),
        exit = scaleOut(animationSpec = tween(500), targetScale = 0.5f)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.droidcon),
                contentDescription = "Sample Image",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Scale Animation", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun ExpandShrinkAnimation(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = expandIn(
            animationSpec = tween(500),
            expandFrom = Alignment.Center
        ),
        exit = shrinkOut(
            animationSpec = tween(500),
            shrinkTowards = Alignment.Center
        )
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.droidcon),
                contentDescription = "Sample Image",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Expand/Shrink", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun CustomTransitionAnimation(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(500)) + slideInVertically(animationSpec = tween(500)) { fullHeight -> -fullHeight },
        exit = fadeOut(animationSpec = tween(500)) + scaleOut(animationSpec = tween(500), targetScale = 0.5f)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.droidcon),
                contentDescription = "Sample Image",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Custom Transition", style = MaterialTheme.typography.bodyLarge)
        }
    }
}