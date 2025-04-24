package com.droidcon.composablebank.ui.basic_comp_layouts

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.droidcon.composablebank.components.InteractiveColorPicker
import com.droidcon.composablebank.components.InteractiveDropdown
import com.droidcon.composablebank.components.InteractiveSlider
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.utils.CustomTopAppBar
import kotlinx.coroutines.launch

@Composable
internal fun LazyColumnContainer(navController: NavController, name: String) {
    var selectedExample by remember { mutableStateOf("Dynamic List") }
    val examples = listOf("Dynamic List", "Reverse Layout", "Color Theme", "Sticky Headers", "Advanced Controls")

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

            when (selectedExample) {
                "Dynamic List" -> DynamicListExample()
                "Reverse Layout" -> ReverseLayoutExample()
                "Color Theme" -> ColorThemedListExample()
                "Sticky Headers" -> StickyHeadersExample()
                "Advanced Controls" -> AdvancedControlsExample()
            }
        }
    }
}

@Composable
private fun DynamicListExample() {
    var itemCount by remember { mutableFloatStateOf(10f) }
    var showDividers by remember { mutableStateOf(true) }
    var listPadding by remember { mutableFloatStateOf(8f) }

    Column(modifier = Modifier.fillMaxHeight()) {
        InteractiveSlider(
            label = "Item count: ${itemCount.toInt()}",
            value = itemCount,
            onValueChange = { itemCount = it },
            valueRange = 5f..100f
        )

        InteractiveSwitch(
            label = "Show dividers",
            checked = showDividers,
            onCheckedChange = { showDividers = it }
        )

        InteractiveSlider(
            label = "List padding: ${listPadding.toInt()}dp",
            value = listPadding,
            onValueChange = { listPadding = it },
            valueRange = 0f..16f
        )

        Spacer(modifier = Modifier.height(16.dp))

        val items = List(itemCount.toInt()) { "Item ${it + 1}" }

        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(listPadding.dp)
            ) {
                itemsIndexed(items) { index, item ->
                    Column {
                        CustomListItem(
                            headlineContent = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "#${index + 1}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(
                                        text = item,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            },
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        if (showDividers && index < items.lastIndex) {
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.outline,
                                thickness = 1.dp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ReverseLayoutExample() {
    var reversed by remember { mutableStateOf(false) }
    val items = listOf("First", "Second", "Third", "Fourth", "Fifth", "Sixth", "Seventh", "Eighth", "Ninth", "Tenth")

    Column(modifier = Modifier.fillMaxHeight()) {
        InteractiveSwitch(
            label = "Reverse Layout",
            checked = reversed,
            onCheckedChange = { reversed = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier.weight(0.2f)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                reverseLayout = reversed
            ) {
                itemsIndexed(items) { index, item ->
                    CustomListItem(
                        headlineContent = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "#${index + 1}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = item,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        },
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ColorThemedListExample() {
    var selectedColor by remember { mutableStateOf(Color.Red) }
    val items = List(10) { "Colored Item ${it + 1}" }

    Column(modifier = Modifier.fillMaxHeight()) {
        InteractiveColorPicker(
            label = "Select Text Color",
            selectedColor = selectedColor,
            onColorSelected = { selectedColor = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier.weight(0.2f)) {
            LazyColumn(Modifier.fillMaxSize()) {
                itemsIndexed(items) { index, item ->
                    CustomListItem(
                        headlineContent = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "#${index + 1}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = item,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = selectedColor
                                )
                            }
                        },
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun StickyHeadersExample() {
    data class Section(val title: String, val items: List<String>)

    val sections = remember {
        listOf(
            Section("Fruits", listOf("Apple", "Banana", "Orange", "Mango", "Peach", "Cherry")),
            Section("Vegetables", listOf("Carrot", "Broccoli", "Spinach", "Potato", "Tomato")),
            Section("Drinks", listOf("Water", "Juice", "Coffee", "Tea", "Soda", "Milk", "Beer")),
        )
    }

    Box(modifier = Modifier.fillMaxHeight()) {
        LazyColumn(Modifier.fillMaxSize()) {
            sections.forEach { section ->
                stickyHeader {
                    HeaderItem(headerText = section.title)
                }

                itemsIndexed(section.items) { index, item ->
                    CustomListItem(
                        headlineContent = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "#${index + 1}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = item,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@SuppressLint("FrequentlyChangedStateReadInComposition")
@Composable
private fun AdvancedControlsExample() {
    var scrollEnabled by remember { mutableStateOf(true) }
    var contentPadding by remember { mutableFloatStateOf(16f) }
    var alignmentType by remember { mutableStateOf("Start") }
    var reverseLayout by remember { mutableStateOf(false) }

    val alignments = listOf("Start", "Center", "End")

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val items = List(50) { "Advanced Item ${it + 1}" }

    Column(modifier = Modifier.fillMaxHeight()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            InteractiveSwitch(
                label = "Scroll Enabled",
                checked = scrollEnabled,
                onCheckedChange = { scrollEnabled = it },
                modifier = Modifier.weight(1f)
            )
            InteractiveSwitch(
                label = "Reverse Layout",
                checked = reverseLayout,
                onCheckedChange = { reverseLayout = it },
                modifier = Modifier.weight(1f)
            )
        }

        InteractiveSlider(
            label = "Content Padding: ${contentPadding.toInt()}dp",
            value = contentPadding,
            onValueChange = { contentPadding = it },
            valueRange = 0f..32f
        )

        InteractiveDropdown(
            options = alignments,
            selectedOption = alignmentType,
            onOptionSelected = { alignmentType = it },
            label = "Horizontal Alignment",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(contentPadding.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = when (alignmentType) {
                    "Start" -> Alignment.Start
                    "Center" -> Alignment.CenterHorizontally
                    "End" -> Alignment.End
                    else -> Alignment.Start
                },
                reverseLayout = reverseLayout,
                flingBehavior = ScrollableDefaults.flingBehavior(),
                userScrollEnabled = scrollEnabled
            ) {
                itemsIndexed(items) { index, item ->
                    CustomListItem(
                        headlineContent = {
                            Text(
                                text = item,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                textAlign = when (alignmentType) {
                                    "Start" -> TextAlign.Start
                                    "Center" -> TextAlign.Center
                                    "End" -> TextAlign.End
                                    else -> TextAlign.Start
                                }
                            )
                        }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(contentPadding.dp))
                }
            }

            if (listState.firstVisibleItemIndex > 0 || reverseLayout) {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            val targetIndex = if (reverseLayout) items.lastIndex else 0
                            listState.animateScrollToItem(targetIndex)
                        }
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomEnd),
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = if (reverseLayout) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
                        contentDescription = if (reverseLayout) "Scroll to Bottom" else "Scroll to Top"
                    )
                }
            }
        }
    }
}

@Composable
private fun HeaderItem(headerText: String) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = headerText,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
private fun CustomListItem(
    headlineContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    supportingContent: @Composable (() -> Unit)? = null,
    leadingContent: @Composable (() -> Unit)? = null
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 4.dp,
        shadowElevation = 4.dp
    ) {
        ListItem(
            headlineContent = headlineContent,
            supportingContent = supportingContent,
            leadingContent = leadingContent,
            modifier = Modifier.padding(16.dp)
        )
    }
}