package com.droidcon.composablebank.ui.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.droidcon.composablebank.utils.CustomTopAppBar
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.components.InteractiveRadioButtonGroup

@Composable
fun RowLayout(navController: NavController, name: String) {
    var horizontalArrangementName by remember { mutableStateOf("start") }
    var verticalAlignmentName by remember { mutableStateOf("top") }
    var isScrollable by remember { mutableStateOf(false) }

    val horizontalArrangements = mapOf(
        "start" to Arrangement.Start,
        "center" to Arrangement.Center,
        "end" to Arrangement.End,
        "spacebetween" to Arrangement.SpaceBetween,
        "spacearound" to Arrangement.SpaceAround,
        "spaceevenly" to Arrangement.SpaceEvenly
    )

    val verticalAlignments = mapOf(
        "top" to Alignment.Top,
        "center" to Alignment.CenterVertically,
        "bottom" to Alignment.Bottom
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
                    InteractiveRadioButtonGroup(
                        options = horizontalArrangements.keys.toList(),
                        selectedOption = horizontalArrangementName,
                        onOptionSelected = { selected ->
                            horizontalArrangementName = selected
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    InteractiveRadioButtonGroup(
                        options = verticalAlignments.keys.toList(),
                        selectedOption = verticalAlignmentName,
                        onOptionSelected = { selected ->
                            verticalAlignmentName = selected
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(250.dp)
                        .padding(16.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))
                        .then(
                            if (isScrollable) {
                                Modifier.horizontalScroll(rememberScrollState())
                            } else {
                                Modifier
                            }
                        ),
                    horizontalArrangement = horizontalArrangements[horizontalArrangementName] ?: Arrangement.Start,
                    verticalAlignment = verticalAlignments[verticalAlignmentName] ?: Alignment.Top,
                ) {
                    repeat(5) { index ->
                        Box(
                            modifier = Modifier
                                .width(70.dp)
                                .height(65.dp)
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
                                modifier = Modifier.align(Alignment.Center),
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
                InteractiveSwitch(
                    label = "Scrollable Row",
                    checked = isScrollable,
                    onCheckedChange = { isScrollable = it }
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))
                        .then(
                            if (isScrollable) {
                                Modifier.horizontalScroll(rememberScrollState())
                            } else {
                                Modifier
                            }
                        ),
                ) {
                    repeat(10) { index ->
                        Box(
                            modifier = Modifier
                                .width(70.dp)
                                .height(65.dp)
                                .background(
                                    color = when (index) {
                                        0 -> MaterialTheme.colorScheme.primary
                                        1 -> MaterialTheme.colorScheme.secondary
                                        2 -> MaterialTheme.colorScheme.tertiary
                                        3 -> MaterialTheme.colorScheme.error
                                        4 -> MaterialTheme.colorScheme.primary
                                        5 -> MaterialTheme.colorScheme.secondary
                                        6 -> MaterialTheme.colorScheme.tertiary
                                        7 -> MaterialTheme.colorScheme.error
                                        8 -> MaterialTheme.colorScheme.primary
                                        9 -> MaterialTheme.colorScheme.secondary
                                        else -> MaterialTheme.colorScheme.secondaryContainer
                                    },
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Item ${index + 1}",
                                modifier = Modifier.align(Alignment.Center),
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    )
}