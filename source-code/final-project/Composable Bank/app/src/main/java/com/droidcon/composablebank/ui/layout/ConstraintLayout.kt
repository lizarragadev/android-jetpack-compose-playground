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

    var alignmentName by remember { mutableStateOf("center") }
    var showImage by remember { mutableStateOf(true) }
    var showButton by remember { mutableStateOf(true) }
    var boxColor by remember { mutableStateOf(Color.Transparent) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { CustomTopAppBar(title = name, navController = navController) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    InteractiveRadioButtonGroup(
                        options = listOf("topstart", "topcenter", "topend", "centerstart", "center", "centerend", "bottomstart", "bottomcenter", "bottomend"),
                        selectedOption = alignmentName,
                        onOptionSelected = { alignmentName = it }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    InteractiveSwitch(
                        label = "Show Image",
                        checked = showImage,
                        onCheckedChange = { showImage = it }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    InteractiveSwitch(
                        label = "Show Button",
                        checked = showButton,
                        onCheckedChange = { showButton = it }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    InteractiveColorPicker(
                        label = "Container Color",
                        selectedColor = boxColor,
                        onColorSelected = { boxColor = it }
                    )
                }

                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .background(boxColor)
                        .padding(16.dp),
                    constraintSet = ConstraintSet {
                        val box1 = createRefFor("box1")
                        val image = createRefFor("image")
                        val button = createRefFor("button")

                        when (alignmentName) {
                            "topstart" -> {
                                constrain(box1) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                    width = Dimension.value(80.dp)
                                    height = Dimension.value(80.dp)
                                }
                            }
                            "topcenter" -> {
                                constrain(box1) {
                                    top.linkTo(parent.top)
                                    centerHorizontallyTo(parent)
                                    width = Dimension.value(80.dp)
                                    height = Dimension.value(80.dp)
                                }
                            }
                            "topend" -> {
                                constrain(box1) {
                                    top.linkTo(parent.top)
                                    end.linkTo(parent.end)
                                    width = Dimension.value(80.dp)
                                    height = Dimension.value(80.dp)
                                }
                            }
                            "centerstart" -> {
                                constrain(box1) {
                                    start.linkTo(parent.start)
                                    centerVerticallyTo(parent)
                                    width = Dimension.value(80.dp)
                                    height = Dimension.value(80.dp)
                                }
                            }
                            "center" -> {
                                constrain(box1) {
                                    centerTo(parent)
                                    width = Dimension.value(80.dp)
                                    height = Dimension.value(80.dp)
                                }
                            }
                            "centerend" -> {
                                constrain(box1) {
                                    end.linkTo(parent.end)
                                    centerVerticallyTo(parent)
                                    width = Dimension.value(80.dp)
                                    height = Dimension.value(80.dp)
                                }
                            }
                            "bottomstart" -> {
                                constrain(box1) {
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.start)
                                    width = Dimension.value(80.dp)
                                    height = Dimension.value(80.dp)
                                }
                            }
                            "bottomcenter" -> {
                                constrain(box1) {
                                    bottom.linkTo(parent.bottom)
                                    centerHorizontallyTo(parent)
                                    width = Dimension.value(80.dp)
                                    height = Dimension.value(80.dp)
                                }
                            }
                            "bottomend" -> {
                                constrain(box1) {
                                    bottom.linkTo(parent.bottom)
                                    end.linkTo(parent.end)
                                    width = Dimension.value(80.dp)
                                    height = Dimension.value(80.dp)
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
                ) {
                    Box(
                        modifier = Modifier
                            .layoutId("box1")
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
                            onClick = { showSnackbar = true },
                            modifier = Modifier
                                .layoutId("button")
                                .padding(16.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add")
                        }
                    }
                }
            }
        }
    )

    if (showSnackbar) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar("ConstraintLayout updated")
            showSnackbar = false
        }
    }
}