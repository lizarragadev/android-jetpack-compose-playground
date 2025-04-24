package com.droidcon.composablebank.ui.basic_comp_layouts

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
import androidx.compose.ui.graphics.Color
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.components.InteractiveRadioButtonGroup

@Composable
fun RowLayout(navController: NavController, name: String) {
    var horizontalArrangementName by remember { mutableStateOf("Start") }
    var verticalAlignmentName by remember { mutableStateOf("Top") }
    var isScrollable by remember { mutableStateOf(false) }

    val horizontalArrangements = remember {
        mapOf(
            "Start" to Arrangement.Start,
            "Center" to Arrangement.Center,
            "End" to Arrangement.End,
            "SpaceBetween" to Arrangement.SpaceBetween,
            "SpaceAround" to Arrangement.SpaceAround,
            "SpaceEvenly" to Arrangement.SpaceEvenly
        )
    }

    val verticalAlignments = remember {
        mapOf(
            "Top" to Alignment.Top,
            "Center" to Alignment.CenterVertically,
            "Bottom" to Alignment.Bottom
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { CustomTopAppBar(title = name, navController = navController) },
        content = { paddingValues ->
            MainContent(
                paddingValues = paddingValues,
                horizontalArrangementName = horizontalArrangementName,
                verticalAlignmentName = verticalAlignmentName,
                isScrollable = isScrollable,
                horizontalArrangements = horizontalArrangements,
                verticalAlignments = verticalAlignments,
                onHorizontalArrangementChange = { horizontalArrangementName = it },
                onVerticalAlignmentChange = { verticalAlignmentName = it },
                onScrollableChange = { isScrollable = it }
            )
        }
    )
}

@Composable
private fun MainContent(
    paddingValues: PaddingValues,
    horizontalArrangementName: String,
    verticalAlignmentName: String,
    isScrollable: Boolean,
    horizontalArrangements: Map<String, Arrangement.Horizontal>,
    verticalAlignments: Map<String, Alignment.Vertical>,
    onHorizontalArrangementChange: (String) -> Unit,
    onVerticalAlignmentChange: (String) -> Unit,
    onScrollableChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RowConfigPanel(
            horizontalArrangementName = horizontalArrangementName,
            verticalAlignmentName = verticalAlignmentName,
            isScrollable = isScrollable,
            horizontalArrangements = horizontalArrangements.keys.toList(),
            verticalAlignments = verticalAlignments.keys.toList(),
            onHorizontalArrangementChange = onHorizontalArrangementChange,
            onVerticalAlignmentChange = onVerticalAlignmentChange,
            onScrollableChange = onScrollableChange
        )

        DemoRow(
            horizontalArrangement = horizontalArrangements[horizontalArrangementName] ?: Arrangement.Start,
            verticalAlignment = verticalAlignments[verticalAlignmentName] ?: Alignment.Top,
            isScrollable = isScrollable,
            itemCount = 5
        )

        Spacer(modifier = Modifier.height(8.dp))

        ScrollableSwitch(
            isScrollable = isScrollable,
            onScrollableChange = onScrollableChange
        )

        DemoRow(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            isScrollable = isScrollable,
            itemCount = 10
        )
    }
}

@Composable
private fun RowConfigPanel(
    horizontalArrangementName: String,
    verticalAlignmentName: String,
    isScrollable: Boolean,
    horizontalArrangements: List<String>,
    verticalAlignments: List<String>,
    onHorizontalArrangementChange: (String) -> Unit,
    onVerticalAlignmentChange: (String) -> Unit,
    onScrollableChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .height(300.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ArrangementSelection(
                options = horizontalArrangements,
                selectedOption = horizontalArrangementName,
                label = "Horizontal Arrangement",
                onOptionSelected = onHorizontalArrangementChange
            )

            AlignmentSelection(
                options = verticalAlignments,
                selectedOption = verticalAlignmentName,
                label = "Vertical Alignment",
                onOptionSelected = onVerticalAlignmentChange
            )
        }
    }
}

@Composable
private fun ArrangementSelection(
    options: List<String>,
    selectedOption: String,
    label: String,
    onOptionSelected: (String) -> Unit
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
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
    label: String,
    onOptionSelected: (String) -> Unit
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
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
private fun DemoRow(
    horizontalArrangement: Arrangement.Horizontal,
    verticalAlignment: Alignment.Vertical,
    isScrollable: Boolean,
    itemCount: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))
            .horizontalScrollIf(isScrollable),
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment
    ) {
        repeat(itemCount) { index ->
            ColoredBoxItem(index = index)
        }
    }
}

@Composable
private fun ScrollableSwitch(
    isScrollable: Boolean,
    onScrollableChange: (Boolean) -> Unit
) {
    InteractiveSwitch(
        label = "Scrollable Row",
        checked = isScrollable,
        onCheckedChange = onScrollableChange
    )
}

@Composable
private fun ColoredBoxItem(index: Int) {
    Box(
        modifier = Modifier
            .width(70.dp)
            .height(65.dp)
            .background(
                color = getColorForIndex(index),
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

@Composable
private fun getColorForIndex(index: Int): Color {
    return when (index % 4) {
        0 -> MaterialTheme.colorScheme.primary
        1 -> MaterialTheme.colorScheme.secondary
        2 -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.error
    }
}

@Composable
private fun Modifier.horizontalScrollIf(enabled: Boolean) =
    if (enabled) this.horizontalScroll(rememberScrollState()) else this