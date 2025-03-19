package com.droidcon.composablebank.ui.layout

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import com.droidcon.composablebank.components.InteractiveRadioButtonGroup

@Composable
fun ColumnLayout(navController: NavController, name: String) {
    var verticalArrangementName by remember { mutableStateOf("top") }
    var horizontalAlignmentName by remember { mutableStateOf("start") }

    val verticalArrangements = mapOf(
        "top" to Arrangement.Top,
        "center" to Arrangement.Center,
        "bottom" to Arrangement.Bottom,
        "spacebetween" to Arrangement.SpaceBetween,
        "spacearound" to Arrangement.SpaceAround,
        "spaceevenly" to Arrangement.SpaceEvenly
    )

    val horizontalAlignments = mapOf(
        "start" to Alignment.Start,
        "center" to Alignment.CenterHorizontally,
        "end" to Alignment.End
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
                    Spacer(modifier = Modifier.height(16.dp))
                    InteractiveRadioButtonGroup(
                        options = verticalArrangements.keys.toList(),
                        selectedOption = verticalArrangementName,
                        onOptionSelected = { verticalArrangementName = it }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    InteractiveRadioButtonGroup(
                        options = horizontalAlignments.keys.toList(),
                        selectedOption = horizontalAlignmentName,
                        onOptionSelected = { horizontalAlignmentName = it }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                        .padding(16.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)),
                    verticalArrangement = verticalArrangements[verticalArrangementName] ?: Arrangement.Top,
                    horizontalAlignment = horizontalAlignments[horizontalAlignmentName] ?: Alignment.Start,
                ) {
                    repeat(5) { index ->
                        Box(
                            modifier = Modifier
                                .width(200.dp)
                                .height(80.dp)
                                .background(
                                    color = when (index) {
                                        0 -> MaterialTheme.colorScheme.primary
                                        1 -> MaterialTheme.colorScheme.secondary
                                        2 -> MaterialTheme.colorScheme.tertiary
                                        3 -> MaterialTheme.colorScheme.error
                                        4 -> MaterialTheme.colorScheme.primary
                                        else -> MaterialTheme.colorScheme.surface
                                    },
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Item ${index + 1}",
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    )

}