package com.droidcon.composablebank.ui.basic_comp_layouts

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.droidcon.composablebank.components.InteractiveColorPicker
import com.droidcon.composablebank.components.InteractiveDropdown
import com.droidcon.composablebank.components.InteractiveSlider
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.utils.CustomTopAppBar
import kotlin.random.Random

@Composable
internal fun LazyRowContainer(navController: NavController, name: String) {
    var selectedExample by remember { mutableStateOf("Basic List") }
    val examples = listOf("Basic List", "Reversed Layout", "Color Theme", "Sticky Items")

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
                label = "Select Example Type"
            )

            Spacer(modifier = Modifier.height(16.dp))

            when(selectedExample) {
                "Basic List" -> BasicHorizontalListExample()
                "Reversed Layout" -> ReversedLayoutExample()
                "Color Theme" -> ColorThemedListExample()
                "Sticky Items" -> StickyItemsExample()
            }
        }
    }
}

@Composable
private fun BasicHorizontalListExample() {
    var itemCount by remember { mutableFloatStateOf(15f) }
    var itemSpacing by remember { mutableFloatStateOf(8f) }

    Column {
        InteractiveSlider(
            label = "Item count: ${itemCount.toInt()}",
            value = itemCount,
            onValueChange = { itemCount = it },
            valueRange = 5f..30f
        )

        InteractiveSlider(
            label = "Spacing: ${itemSpacing.toInt()}dp",
            value = itemSpacing,
            onValueChange = { itemSpacing = it },
            valueRange = 0f..16f
        )

        Spacer(modifier = Modifier.height(8.dp))

        val items = List(itemCount.toInt()) { "Item ${it + 1}" }
        val listState = rememberLazyListState()

        Box(modifier = Modifier.fillMaxWidth()) {
            LazyRow(
                state = listState,
                horizontalArrangement = Arrangement.spacedBy(itemSpacing.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                itemsIndexed(items) { index, item ->
                    HorizontalListItem(text = item, index = index)
                }
            }

            ScrollPositionIndicator(listState = listState)
        }
    }
}

@Composable
private fun ReversedLayoutExample() {
    var reversed by remember { mutableStateOf(false) }
    val items = List(20) { "Item ${it + 1}" }
    val listState = rememberLazyListState()

    Column {
        InteractiveSwitch(
            label = "Reverse Layout",
            checked = reversed,
            onCheckedChange = { reversed = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            state = listState,
            reverseLayout = reversed,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(items) { index, item ->
                HorizontalListItem(text = item, index = index)
            }
        }
    }
}

@Composable
private fun ColorThemedListExample() {
    var selectedColor by remember { mutableStateOf(Color.Blue) }
    var useRandomColors by remember { mutableStateOf(false) }
    val items = List(15) { "Color ${it + 1}" }

    Column {
        InteractiveColorPicker(
            label = "Select Base Color",
            selectedColor = selectedColor,
            onColorSelected = { selectedColor = it }
        )

        InteractiveSwitch(
            label = "Random Colors",
            checked = useRandomColors,
            onCheckedChange = { useRandomColors = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(items) { item ->
                val color = if(useRandomColors) {
                    Color(
                        red = Random.nextFloat(),
                        green = Random.nextFloat(),
                        blue = Random.nextFloat()
                    )
                } else {
                    selectedColor.copy(alpha = 0.2f + 0.8f * (items.indexOf(item).toFloat() / items.size))
                }

                Surface(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(4.dp),
                    shape = CircleShape,
                    color = color
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = item,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun StickyItemsExample() {
    data class Section(val title: String, val items: List<String>)

    val sections = remember {
        listOf(
            Section("One", List(8) { "Item A${it + 1}" }),
            Section("Two", List(5) { "Item B${it + 1}" }),
            Section("Three", List(6) { "Item C${it + 1}" })
        )
    }

    var stickyEnabled by remember { mutableStateOf(true) }

    Column {
        InteractiveSwitch(
            label = "Enable Sticky Headers",
            checked = stickyEnabled,
            onCheckedChange = { stickyEnabled = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            sections.forEach { section ->
                if(stickyEnabled) {
                    stickyHeader {
                        StickyHeaderItem(text = section.title)
                    }
                } else {
                    item {
                        StickyHeaderItem(text = section.title)
                    }
                }

                items(section.items) { item ->
                    HorizontalListItem(
                        text = item,
                        index = section.items.indexOf(item),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }

                item {
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
    }
}

@Composable
private fun HorizontalListItem(text: String, index: Int, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .size(100.dp)
            .padding(4.dp),
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primaryContainer,
        tonalElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "#${index + 1}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun StickyHeaderItem(text: String) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        modifier = Modifier
            .width(48.dp)
            .height(100.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(Color.Cyan),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                modifier = Modifier
                    .rotate(270f)
                    .padding(4.dp),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun ScrollPositionIndicator(listState: LazyListState) {
    val firstVisible by remember { derivedStateOf { listState.firstVisibleItemIndex } }

    Box(modifier = Modifier.fillMaxWidth()) {
        Surface(
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        ) {
            Text(
                text = "Position: $firstVisible",
                modifier = Modifier.padding(8.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}