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
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.droidcon.composablebank.components.InteractiveRadioButtonGroup
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.R

@Composable
fun Clip(navController: NavController, name: String) {
    var clipShape by remember { mutableStateOf("circle") }
    var cornerRadius by remember { mutableStateOf(16.dp) }
    var showOriginal by remember { mutableStateOf(false) }

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
                    options = listOf("Circle", "Rounded Rectangle", "Custom Shape"),
                    selectedOption = clipShape,
                    onOptionSelected = { clipShape = it.lowercase() }
                )
                Spacer(modifier = Modifier.height(16.dp))

                if (clipShape == "rounded rectangle") {
                    Text(
                        text = "Corner Radius: ${cornerRadius.value.toInt()} dp",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Slider(
                        value = cornerRadius.value,
                        onValueChange = { cornerRadius = it.dp },
                        valueRange = 0f..50f,
                        steps = 50
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                InteractiveSwitch(
                    label = "Show Original Image",
                    checked = showOriginal,
                    onCheckedChange = { showOriginal = it }
                )
                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .background(Color.DarkGray)
                        .padding(16.dp)
                        .then(
                            if (!showOriginal) {
                                when (clipShape) {
                                    "circle" -> Modifier.clip(CircleShape)
                                    "roundedrectangle" -> Modifier.clip(RoundedCornerShape(cornerRadius))
                                    else -> Modifier.clip(TriangleShape)
                                }
                            } else {
                                Modifier
                            }
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.droidcon),
                        contentDescription = "Sample Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    clipShape = "circle"
                    cornerRadius = 16.dp
                    showOriginal = false
                }) {
                    Text("Reset Clip")
                }
            }
        }
    )
}

private val TriangleShape = GenericShape { size, _ ->
    moveTo(size.width / 2f, 0f)
    lineTo(size.width, size.height)
    lineTo(0f, size.height)
    close()
}