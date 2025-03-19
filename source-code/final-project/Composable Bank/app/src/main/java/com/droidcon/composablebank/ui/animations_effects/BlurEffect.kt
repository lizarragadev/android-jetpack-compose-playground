package com.droidcon.composablebank.ui.animations_effects

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.R

@Composable
internal fun BlurEffect(navController: NavController, name: String) {
    var blurRadius by remember { mutableFloatStateOf(0f) }
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
                InteractiveSwitch(
                    label = "Show Original Image",
                    checked = showOriginal,
                    onCheckedChange = { showOriginal = it }
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Blur Radius: ${blurRadius.toInt()} px",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Slider(
                    value = blurRadius,
                    onValueChange = { blurRadius = it },
                    valueRange = 0f..50f,
                    steps = 10,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .size(300.dp)
                        .then(
                            if (!showOriginal && blurRadius > 0) {
                                Modifier.blur(
                                    radiusX = blurRadius.dp,
                                    radiusY = blurRadius.dp,
                                    edgeTreatment = BlurredEdgeTreatment.Unbounded
                                )
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

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.5f))
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Progressive Blur",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { blurRadius = 0f }) {
                    Text("Reset Blur")
                }
            }
        }
    )
}