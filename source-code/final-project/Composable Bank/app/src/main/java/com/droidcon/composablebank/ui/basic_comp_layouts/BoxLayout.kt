package com.droidcon.composablebank.ui.basic_comp_layouts

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
    var alignmentName by remember { mutableStateOf("TopStart") }
    var showImage by remember { mutableStateOf(true) }
    var showButton by remember { mutableStateOf(true) }
    var boxColor by remember { mutableStateOf(Color.Cyan) }

    val alignments = remember {
        mapOf(
            "TopStart" to Alignment.TopStart,
            "TopCenter" to Alignment.TopCenter,
            "TopEnd" to Alignment.TopEnd,
            "CenterStart" to Alignment.CenterStart,
            "Center" to Alignment.Center,
            "CenterEnd" to Alignment.CenterEnd,
            "BottomStart" to Alignment.BottomStart,
            "BottomCenter" to Alignment.BottomCenter,
            "BottomEnd" to Alignment.BottomEnd
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { CustomTopAppBar(title = name, navController = navController) },
        content = { paddingValues ->
            MainContent(
                paddingValues = paddingValues,
                alignmentName = alignmentName,
                showImage = showImage,
                showButton = showButton,
                boxColor = boxColor,
                alignments = alignments,
                onAlignmentChange = { alignmentName = it },
                onShowImageChange = { showImage = it },
                onShowButtonChange = { showButton = it },
                onBoxColorChange = { boxColor = it },
                onShowSnackbar = { showSnackbar = true }
            )
        }
    )
}

@Composable
private fun MainContent(
    paddingValues: PaddingValues,
    alignmentName: String,
    showImage: Boolean,
    showButton: Boolean,
    boxColor: Color,
    alignments: Map<String, Alignment>,
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
        BoxConfigurationPanel(
            alignmentName = alignmentName,
            showImage = showImage,
            showButton = showButton,
            boxColor = boxColor,
            alignmentOptions = alignments.keys.toList(),
            onAlignmentChange = onAlignmentChange,
            onShowImageChange = onShowImageChange,
            onShowButtonChange = onShowButtonChange,
            onBoxColorChange = onBoxColorChange
        )

        BoxContent(
            alignment = alignments[alignmentName] ?: Alignment.Center,
            showImage = showImage,
            showButton = showButton,
            boxColor = boxColor,
            onButtonClick = onShowSnackbar
        )
    }
}

@Composable
private fun BoxConfigurationPanel(
    alignmentName: String,
    showImage: Boolean,
    showButton: Boolean,
    boxColor: Color,
    alignmentOptions: List<String>,
    onAlignmentChange: (String) -> Unit,
    onShowImageChange: (Boolean) -> Unit,
    onShowButtonChange: (Boolean) -> Unit,
    onBoxColorChange: (Color) -> Unit
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
            style = MaterialTheme.typography.titleLarge
        )

        InteractiveRadioButtonGroup(
            options = alignmentOptions,
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
            label = "Box Background Color",
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
private fun BoxContent(
    alignment: Alignment,
    showImage: Boolean,
    showButton: Boolean,
    boxColor: Color,
    onButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(boxColor)
            .padding(16.dp)
    ) {
        if (showImage) {
            Image(
                painter = painterResource(id = R.drawable.droidcon),
                contentDescription = "Sample Image",
                modifier = Modifier
                    .size(100.dp)
                    .align(alignment)
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
                onClick = onButtonClick,
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