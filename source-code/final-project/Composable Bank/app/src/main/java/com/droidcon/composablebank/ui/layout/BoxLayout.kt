package com.droidcon.composablebank.ui.layout

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.droidcon.composablebank.components.InteractiveColorPicker
import com.droidcon.composablebank.components.InteractiveRadioButtonGroup
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.R

@Composable
fun BoxLayout(navController: NavController, name: String) {
    var showSnackbar by remember { mutableStateOf(false) }

    var alignmentName by remember { mutableStateOf("topstart") }
    var showImage by remember { mutableStateOf(true) }
    var showButton by remember { mutableStateOf(true) }
    var boxColor by remember { mutableStateOf(Color.Cyan) }

    val alignments = mapOf(
        "topstart" to Alignment.TopStart,
        "topcenter" to Alignment.TopCenter,
        "topend" to Alignment.TopEnd,
        "centerstart" to Alignment.CenterStart,
        "center" to Alignment.Center,
        "centerend" to Alignment.CenterEnd,
        "bottomstart" to Alignment.BottomStart,
        "bottomcenter" to Alignment.BottomCenter,
        "bottomend" to Alignment.BottomEnd
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { CustomTopAppBar(title = name, navController = navController) },
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
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Content Alignment",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    InteractiveRadioButtonGroup(
                        options = alignments.keys.toList(),
                        selectedOption = alignmentName,
                        onOptionSelected = { alignmentName = it }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

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
                        label = "Box Background Color",
                        selectedColor = boxColor,
                        onColorSelected = { boxColor = it }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(boxColor)
                        .padding(16.dp),
                    contentAlignment = alignments[alignmentName] ?: Alignment.Center
                ) {
                    if (showImage) {
                        Image(
                            painter = painterResource(id = R.drawable.droidcon),
                            contentDescription = "Sample Image",
                            modifier = Modifier.size(100.dp)
                        )
                    }
                    Text(
                        text = "Hello from Box!",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    if (showButton) {
                        FloatingActionButton(
                            onClick = { showSnackbar = true },
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp),
                            containerColor = MaterialTheme.colorScheme.primary
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add")
                        }
                    }
                }
            }
        }
    )
}