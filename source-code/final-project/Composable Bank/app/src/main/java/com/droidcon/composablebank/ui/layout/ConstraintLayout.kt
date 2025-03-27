package com.droidcon.composablebank.ui.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.droidcon.composablebank.utils.CustomTopAppBar
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.droidcon.composablebank.components.InteractiveColorPicker
import com.droidcon.composablebank.components.InteractiveRadioButtonGroup
import com.droidcon.composablebank.components.InteractiveSwitch
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.droidcon.composablebank.R

@Composable
fun ConstraintLayout(navController: NavController, name: String) {
    var showSnackbar by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    var alignmentName by remember { mutableStateOf("Center") }
    var showImage by remember { mutableStateOf(true) }
    var showButton by remember { mutableStateOf(true) }
    var boxColor by remember { mutableStateOf(Color.Cyan) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { CustomTopAppBar(title = name, navController = navController) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { paddingValues ->
            MainContent(
                paddingValues = paddingValues,
                alignmentName = alignmentName,
                showImage = showImage,
                showButton = showButton,
                boxColor = boxColor,
                onAlignmentChange = { alignmentName = it },
                onShowImageChange = { showImage = it },
                onShowButtonChange = { showButton = it },
                onBoxColorChange = { boxColor = it },
                onShowSnackbar = { showSnackbar = true }
            )
        }
    )
    HandleSnackbar(showSnackbar, snackbarHostState) { showSnackbar = false }
}

@Composable
private fun MainContent(
    paddingValues: PaddingValues,
    alignmentName: String,
    showImage: Boolean,
    showButton: Boolean,
    boxColor: Color,
    onAlignmentChange: (String) -> Unit,
    onShowImageChange: (Boolean) -> Unit,
    onShowButtonChange: (Boolean) -> Unit,
    onBoxColorChange: (Color) -> Unit,
    onShowSnackbar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConstraintConfigPanel(
            alignmentName = alignmentName,
            showImage = showImage,
            showButton = showButton,
            boxColor = boxColor,
            onAlignmentChange = onAlignmentChange,
            onShowImageChange = onShowImageChange,
            onShowButtonChange = onShowButtonChange,
            onBoxColorChange = onBoxColorChange
        )

        DemoConstraintLayout(
            alignmentName = alignmentName,
            showImage = showImage,
            showButton = showButton,
            boxColor = boxColor,
            onButtonClick = onShowSnackbar
        )
    }
}

@Composable
private fun ConstraintConfigPanel(
    alignmentName: String,
    showImage: Boolean,
    showButton: Boolean,
    boxColor: Color,
    onAlignmentChange: (String) -> Unit,
    onShowImageChange: (Boolean) -> Unit,
    onShowButtonChange: (Boolean) -> Unit,
    onBoxColorChange: (Color) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        InteractiveRadioButtonGroup(
            options = listOf(
                "TopStart", "TopCenter", "TopEnd",
                "CenterStart", "Center", "CenterEnd",
                "BottomStart", "BottomCenter", "BottomEnd"
            ),
            selectedOption = alignmentName,
            onOptionSelected = onAlignmentChange
        )

        SwitchGroup(
            showImage = showImage,
            showButton = showButton,
            onShowImageChange = onShowImageChange,
            onShowButtonChange = onShowButtonChange
        )

        InteractiveColorPicker(
            label = "Container Color",
            selectedColor = boxColor,
            onColorSelected = onBoxColorChange
        )
    }
}

@Composable
private fun SwitchGroup(
    showImage: Boolean,
    showButton: Boolean,
    onShowImageChange: (Boolean) -> Unit,
    onShowButtonChange: (Boolean) -> Unit
) {
    Column {
        InteractiveSwitch(
            label = "Show Image",
            checked = showImage,
            onCheckedChange = onShowImageChange
        )
        Spacer(modifier = Modifier.height(8.dp))
        InteractiveSwitch(
            label = "Show Button",
            checked = showButton,
            onCheckedChange = onShowButtonChange
        )
    }
}

@Composable
private fun DemoConstraintLayout(
    alignmentName: String,
    showImage: Boolean,
    showButton: Boolean,
    boxColor: Color,
    onButtonClick: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(boxColor)
            .padding(16.dp),
        constraintSet = getConstraintSet(alignmentName, showImage, showButton)
    ) {
        LayoutComponents(
            showImage = showImage,
            showButton = showButton,
            onButtonClick = onButtonClick
        )
    }
}

@Composable
private fun LayoutComponents(
    showImage: Boolean,
    showButton: Boolean,
    onButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .layoutId("box1")
            .size(80.dp)
            .background(MaterialTheme.colorScheme.primary)
    )

    if (showImage) {
        Image(
            painter = painterResource(R.drawable.droidcon),
            contentDescription = "Sample Image",
            modifier = Modifier
                .layoutId("image")
                .size(100.dp)
        )
    }

    if (showButton) {
        FloatingActionButton(
            onClick = onButtonClick,
            modifier = Modifier.layoutId("button")
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
}

private fun getConstraintSet(
    alignmentName: String,
    showImage: Boolean,
    showButton: Boolean
): ConstraintSet = ConstraintSet {
    val box1 = createRefFor("box1")
    val image = createRefFor("image")
    val button = createRefFor("button")

    with(constrain(box1) {
        width = Dimension.value(80.dp)
        height = Dimension.value(80.dp)
    }) {
        when (alignmentName) {
            "TopStart" -> {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
            "TopCenter" -> {
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)
            }
            "TopEnd" -> {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
            "CenterStart" -> {
                start.linkTo(parent.start)
                centerVerticallyTo(parent)
            }
            "Center" -> centerTo(parent)
            "CenterEnd" -> {
                end.linkTo(parent.end)
                centerVerticallyTo(parent)
            }
            "BottomStart" -> {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            }
            "BottomCenter" -> {
                bottom.linkTo(parent.bottom)
                centerHorizontallyTo(parent)
            }
            "BottomEnd" -> {
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            }
        }
    }

    if (showImage) {
        constrain(image) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)
        }
    }

    if (showButton) {
        constrain(button) {
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
            width = Dimension.wrapContent
            height = Dimension.wrapContent
        }
    }
}

@Composable
private fun HandleSnackbar(
    showSnackbar: Boolean,
    snackbarHostState: SnackbarHostState,
    onDismiss: () -> Unit
) {
    if (showSnackbar) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar("ConstraintLayout updated")
            onDismiss()
        }
    }
}