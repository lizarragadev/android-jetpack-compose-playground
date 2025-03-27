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

    AnimatedVisibilityScreen(
        navController = navController,
        name = name,
        isVisible = isVisible,
        animationType = animationType,
        onVisibilityChange = { isVisible = it },
        onAnimationTypeChange = { animationType = it }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun AnimatedVisibilityScreen(
    navController: NavController,
    name: String,
    isVisible: Boolean,
    animationType: String,
    onVisibilityChange: (Boolean) -> Unit,
    onAnimationTypeChange: (String) -> Unit
) {
    Scaffold(
        topBar = { CustomTopAppBar(title = name, navController = navController) },
        content = { paddingValues ->
            MainContent(
                paddingValues = paddingValues,
                isVisible = isVisible,
                animationType = animationType,
                onVisibilityChange = onVisibilityChange,
                onAnimationTypeChange = onAnimationTypeChange
            )
        }
    )
}

@Composable
private fun MainContent(
    paddingValues: PaddingValues,
    isVisible: Boolean,
    animationType: String,
    onVisibilityChange: (Boolean) -> Unit,
    onAnimationTypeChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimationControls(
            animationType = animationType,
            isVisible = isVisible,
            onAnimationTypeChange = onAnimationTypeChange,
            onVisibilityChange = onVisibilityChange
        )
        AnimationContainer(animationType = animationType, isVisible = isVisible)
    }
}

@Composable
private fun AnimationControls(
    animationType: String,
    isVisible: Boolean,
    onAnimationTypeChange: (String) -> Unit,
    onVisibilityChange: (Boolean) -> Unit
) {
    Spacer(modifier = Modifier.height(32.dp))
    AnimationTypeSelector(
        selectedType = animationType,
        onTypeSelected = onAnimationTypeChange
    )
    Spacer(modifier = Modifier.height(24.dp))
    VisibilityToggleButton(isVisible = isVisible, onToggle = onVisibilityChange)
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
            "Slide Horizontal",
            "Slide Vertical",
            "Scale",
            "Expand/Shrink",
            "Custom Transition"
        ),
        selectedOption = selectedType,
        onOptionSelected = onTypeSelected
    )
}

@Composable
private fun VisibilityToggleButton(isVisible: Boolean, onToggle: (Boolean) -> Unit) {
    Button(onClick = { onToggle(!isVisible) }) {
        Text(text = if (isVisible) "Hide Content" else "Show Content")
    }
}

@Composable
private fun AnimationContainer(animationType: String, isVisible: Boolean) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        when (animationType) {
            "Fade" -> FadeAnimation(isVisible)
            "Slide Horizontal" -> SlideHorizontalAnimation(isVisible)
            "Slide Vertical" -> SlideVerticalAnimation(isVisible)
            "Scale" -> ScaleAnimation(isVisible)
            "Expand/Shrink" -> ExpandShrinkAnimation(isVisible)
            "Custom Transition" -> CustomTransitionAnimation(isVisible)
        }
    }
}

@Composable
@OptIn(ExperimentalAnimationApi::class)
private fun FadeAnimation(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(500)),
        exit = fadeOut(animationSpec = tween(500))
    ) {
        ContentWithImage(title = "Fade Animation")
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SlideHorizontalAnimation(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally(animationSpec = tween(1500)) { -it },
        exit = slideOutHorizontally(animationSpec = tween(1500)) { it }
    ) {
        ContentWithImage(title = "Slide Horizontal")
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SlideVerticalAnimation(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(animationSpec = tween(1500)) { -it },
        exit = slideOutVertically(animationSpec = tween(1500)) { it }
    ) {
        ContentWithImage(title = "Slide Vertical")
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun ScaleAnimation(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = scaleIn(animationSpec = tween(1500), initialScale = 0.5f),
        exit = scaleOut(animationSpec = tween(1500), targetScale = 0.5f)
    ) {
        ContentWithImage(title = "Scale Animation")
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun ExpandShrinkAnimation(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = expandIn(
            animationSpec = tween(1500),
            expandFrom = Alignment.Center
        ),
        exit = shrinkOut(
            animationSpec = tween(1500),
            shrinkTowards = Alignment.Center
        )
    ) {
        ContentWithImage(title = "Expand/Shrink")
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun CustomTransitionAnimation(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(1500)) +
                slideInVertically(animationSpec = tween(1500)) { -it },
        exit = fadeOut(animationSpec = tween(1500)) +
                scaleOut(animationSpec = tween(1500), targetScale = 0.5f)
    ) {
        ContentWithImage(title = "Custom Transition")
    }
}

@Composable
private fun ContentWithImage(title: String) {
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
        Text(text = title, style = MaterialTheme.typography.bodyLarge)
    }
}

