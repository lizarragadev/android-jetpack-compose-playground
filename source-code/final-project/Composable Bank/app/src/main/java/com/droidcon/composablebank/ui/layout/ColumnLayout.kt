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
    var verticalArrangementName by remember { mutableStateOf("Top") }
    var horizontalAlignmentName by remember { mutableStateOf("Start") }

    val verticalArrangements = remember {
        mapOf(
            "Top" to Arrangement.Top,
            "Center" to Arrangement.Center,
            "Bottom" to Arrangement.Bottom,
            "SpaceBetween" to Arrangement.SpaceBetween,
            "SpaceAround" to Arrangement.SpaceAround,
            "SpaceEvenly" to Arrangement.SpaceEvenly
        )
    }

    val horizontalAlignments = remember {
        mapOf(
            "Start" to Alignment.Start,
            "Center" to Alignment.CenterHorizontally,
            "End" to Alignment.End
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { CustomTopAppBar(title = name, navController = navController) },
        content = { paddingValues ->
            MainContent(
                paddingValues = paddingValues,
                verticalArrangementName = verticalArrangementName,
                horizontalAlignmentName = horizontalAlignmentName,
                verticalArrangements = verticalArrangements,
                horizontalAlignments = horizontalAlignments,
                onVerticalArrangementChange = { verticalArrangementName = it },
                onHorizontalAlignmentChange = { horizontalAlignmentName = it }
            )
        }
    )
}

@Composable
private fun MainContent(
    paddingValues: PaddingValues,
    verticalArrangementName: String,
    horizontalAlignmentName: String,
    verticalArrangements: Map<String, Arrangement.Vertical>,
    horizontalAlignments: Map<String, Alignment.Horizontal>,
    onVerticalArrangementChange: (String) -> Unit,
    onHorizontalAlignmentChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ColumnConfigurationPanel(
            verticalArrangementName = verticalArrangementName,
            horizontalAlignmentName = horizontalAlignmentName,
            verticalArrangements = verticalArrangements.keys.toList(),
            horizontalAlignments = horizontalAlignments.keys.toList(),
            onVerticalArrangementChange = onVerticalArrangementChange,
            onHorizontalAlignmentChange = onHorizontalAlignmentChange
        )

        DemoColumn(
            verticalArrangement = verticalArrangements[verticalArrangementName] ?: Arrangement.Top,
            horizontalAlignment = horizontalAlignments[horizontalAlignmentName] ?: Alignment.Start
        )
    }
}

@Composable
private fun ColumnConfigurationPanel(
    verticalArrangementName: String,
    horizontalAlignmentName: String,
    verticalArrangements: List<String>,
    horizontalAlignments: List<String>,
    onVerticalArrangementChange: (String) -> Unit,
    onHorizontalAlignmentChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        ArrangementSelection(
            options = verticalArrangements,
            selectedOption = verticalArrangementName,
            onOptionSelected = onVerticalArrangementChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        AlignmentSelection(
            options = horizontalAlignments,
            selectedOption = horizontalAlignmentName,
            onOptionSelected = onHorizontalAlignmentChange
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun ArrangementSelection(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Column {
        Text(
            text = "Vertical Arrangement",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        InteractiveRadioButtonGroup(
            options = options,
            selectedOption = selectedOption,
            onOptionSelected = onOptionSelected
        )
    }
}

@Composable
private fun AlignmentSelection(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Column {
        Text(
            text = "Horizontal Alignment",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        InteractiveRadioButtonGroup(
            options = options,
            selectedOption = selectedOption,
            onOptionSelected = onOptionSelected
        )
    }
}

@Composable
private fun DemoColumn(
    verticalArrangement: Arrangement.Vertical,
    horizontalAlignment: Alignment.Horizontal
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {
        repeat(5) { index ->
            ColoredBoxItem(index = index)
        }
    }
}

@Composable
private fun ColoredBoxItem(index: Int) {
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
                    else -> MaterialTheme.colorScheme.primary
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