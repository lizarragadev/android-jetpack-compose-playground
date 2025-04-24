package com.droidcon.composablebank.ui.basic_comp_layouts

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.droidcon.composablebank.components.InteractiveColorPicker
import com.droidcon.composablebank.components.InteractiveDropdown
import com.droidcon.composablebank.components.InteractiveSlider
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.utils.CustomTopAppBar
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun LazyGridContainer(navController: NavController, name: String) {
    var selectedExample by remember { mutableStateOf("Vertical Grid") }
    val examples = listOf("Vertical Grid", "Horizontal Grid", "Adaptive Grid", "Staggered Grid")

    Scaffold(
        topBar = { CustomTopAppBar(title = name, navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            InteractiveDropdown(
                options = examples,
                selectedOption = selectedExample,
                onOptionSelected = { selectedExample = it },
                label = "Select Grid Type"
            )

            Spacer(modifier = Modifier.height(16.dp))

            when(selectedExample) {
                "Vertical Grid" -> VerticalGridExample()
                "Horizontal Grid" -> HorizontalGridExample()
                "Adaptive Grid" -> AdaptiveGridExample()
                "Staggered Grid" -> StaggeredGridExample()
            }
        }
    }
}

@Composable
private fun VerticalGridExample() {
    var spanCount by remember { mutableFloatStateOf(2f) }
    var spacing by remember { mutableFloatStateOf(8f) }
    var selectedColor by remember { mutableStateOf(Color.Cyan) }

    Column {
        InteractiveSlider(
            label = "Columns: ${spanCount.toInt()}",
            value = spanCount,
            onValueChange = { spanCount = it },
            valueRange = 1f..4f
        )

        InteractiveSlider(
            label = "Spacing: ${spacing.toInt()}dp",
            value = spacing,
            onValueChange = { spacing = it },
            valueRange = 0f..16f
        )

        InteractiveColorPicker(
            label = "Item Color",
            selectedColor = selectedColor,
            onColorSelected = { selectedColor = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        val items = List(50) { "Item ${it + 1}" }
        val gridState = rememberLazyGridState()

        LazyVerticalGrid(
            columns = GridCells.Fixed(spanCount.toInt()),
            state = gridState,
            verticalArrangement = Arrangement.spacedBy(spacing.dp),
            horizontalArrangement = Arrangement.spacedBy(spacing.dp),
            modifier = Modifier.height(500.dp)
        ) {
            items(items.size) { index ->
                GridItem(
                    text = items[index],
                    color = selectedColor,
                    modifier = Modifier.padding(4.dp),
                    useAspectRatio = true
                )
            }
        }
    }
}

@Composable
private fun HorizontalGridExample() {
    var rowCount by remember { mutableFloatStateOf(2f) }
    var spacing by remember { mutableFloatStateOf(8f) }

    Column {
        InteractiveSlider(
            label = "Rows: ${rowCount.toInt()}",
            value = rowCount,
            onValueChange = { rowCount = it },
            valueRange = 1f..4f
        )

        InteractiveSlider(
            label = "Spacing: ${spacing.toInt()}dp",
            value = spacing,
            onValueChange = { spacing = it },
            valueRange = 0f..16f
        )

        Spacer(modifier = Modifier.height(8.dp))

        val items = List(50) { "Item ${it + 1}" }
        val gridState = rememberLazyGridState()

        LazyHorizontalGrid(
            rows = GridCells.Fixed(rowCount.toInt()),
            state = gridState,
            horizontalArrangement = Arrangement.spacedBy(spacing.dp),
            verticalArrangement = Arrangement.spacedBy(spacing.dp),
            modifier = Modifier.height(350.dp)
        ) {
            items(items.size) { index ->
                GridItem(
                    text = items[index],
                    modifier = Modifier
                        .padding(4.dp)
                        .height(100.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    useAspectRatio = true
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AdaptiveGridExample() {
    var minSize by remember { mutableFloatStateOf(100f) }
    var spacing by remember { mutableFloatStateOf(8f) }

    Column {
        InteractiveSlider(
            label = "Min Size: ${minSize.toInt()}dp",
            value = minSize,
            onValueChange = { minSize = it },
            valueRange = 50f..150f
        )

        InteractiveSlider(
            label = "Spacing: ${spacing.toInt()}dp",
            value = spacing,
            onValueChange = { spacing = it },
            valueRange = 0f..16f
        )

        Spacer(modifier = Modifier.height(8.dp))

        val items = List(50) { "Item ${it + 1}" }
        val gridState = rememberLazyGridState()

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize.dp),
            state = gridState,
            verticalArrangement = Arrangement.spacedBy(spacing.dp),
            horizontalArrangement = Arrangement.spacedBy(spacing.dp),
            modifier = Modifier.height(600.dp)
        ) {
            items(items.size) { index ->
                GridItem(
                    text = items[index],
                    color = Color(
                        red = (index * 9 % 255) / 255f,
                        green = (index * 7 % 255) / 255f,
                        blue = (index * 11 % 255) / 255f
                    ),
                    modifier = Modifier
                        .padding(4.dp)
                        .height(100.dp),
                    useAspectRatio = false
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun StaggeredGridExample() {
    var spanCount by remember { mutableFloatStateOf(2f) }
    var dynamicHeight by remember { mutableStateOf(true) }

    Column {
        InteractiveSlider(
            label = "Columns: ${spanCount.toInt()}",
            value = spanCount,
            onValueChange = { spanCount = it },
            valueRange = 1f..4f
        )

        InteractiveSwitch(
            label = "Dynamic Heights",
            checked = dynamicHeight,
            onCheckedChange = { dynamicHeight = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        val items = List(50) { "Item ${it + 1}" }

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(spanCount.toInt()),
            modifier = Modifier.height(600.dp),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items.size) { index ->
                val item = items[index]
                val height = if (dynamicHeight) (80 + (index % 5) * 40).dp else 120.dp

                GridItem(
                    text = item,
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    modifier = Modifier
                        .padding(4.dp)
                        .height(height),
                    useAspectRatio = false
                )
            }
        }
    }
}

@Composable
private fun GridItem(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    useAspectRatio: Boolean = false
) {
    Surface(
        color = color.takeIf { it != Color.Unspecified }
            ?: MaterialTheme.colorScheme.primaryContainer,
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (useAspectRatio) Modifier.aspectRatio(1f)
                else Modifier.border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.medium)
            ),
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}