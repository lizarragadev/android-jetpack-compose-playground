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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import com.droidcon.composablebank.components.InteractiveColorPicker
import com.droidcon.composablebank.R

@Composable
fun Shadow(navController: NavController, name: String) {
    var elevation by remember { mutableStateOf(8.dp) }
    var shadowColor by remember { mutableStateOf(Color.Black) }
    var offsetX by remember { mutableStateOf(0.dp) }
    var offsetY by remember { mutableStateOf(0.dp) }

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
                InteractiveColorPicker(
                    label = "Shadow Color",
                    selectedColor = shadowColor,
                    onColorSelected = { shadowColor = it }
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Elevation: ${elevation.value.toInt()} dp",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Slider(
                    value = elevation.value,
                    onValueChange = { elevation = it.dp },
                    valueRange = 0f..30f,
                    steps = 30
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Offset X: ${offsetX.value.toInt()} dp",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Slider(
                    value = offsetX.value,
                    onValueChange = { offsetX = it.dp },
                    valueRange = -20f..20f,
                    steps = 40
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Offset Y: ${offsetY.value.toInt()} dp",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Slider(
                    value = offsetY.value,
                    onValueChange = { offsetY = it.dp },
                    valueRange = -20f..20f,
                    steps = 40
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .shadow(
                            elevation = elevation,
                            shape = RoundedCornerShape(16.dp),
                            clip = true,
                            ambientColor = shadowColor.copy(alpha = 0.5f),
                            spotColor = shadowColor
                        )
                        .offset { IntOffset(offsetX.roundToPx(), offsetY.roundToPx()) }
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
                    elevation = 8.dp
                    shadowColor = Color.Black
                    offsetX = 0.dp
                    offsetY = 0.dp
                }) {
                    Text("Reset Shadow")
                }
            }
        }
    )
}