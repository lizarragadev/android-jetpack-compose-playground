package com.droidcon.composablebank.ui.basic_comp_layouts

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import com.droidcon.composablebank.components.InteractiveColorPicker
import com.droidcon.composablebank.components.InteractiveSlider
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.utils.CustomTopAppBar
import com.droidcon.composablebank.R
import com.droidcon.composablebank.components.InteractiveDropdown
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Composable
internal fun CardComposable(navController: NavController, name: String) {
    var cardColor by remember { mutableStateOf(Color.White) }
    var cardElevation by remember { mutableFloatStateOf(4f) }
    var selectedCardType by remember { mutableStateOf("Elevated") }
    var showImage by remember { mutableStateOf(true) }
    var swipeEnabled by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { CustomTopAppBar(title = name, navController = navController) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    InteractiveColorPicker(
                        label = "Card Color",
                        selectedColor = cardColor,
                        onColorSelected = { cardColor = it }
                    )

                    InteractiveDropdown(
                        options = listOf("Elevated", "Filled", "Outlined"),
                        selectedOption = selectedCardType,
                        onOptionSelected = { selectedCardType = it },
                        label = "Card Type"
                    )

                    if(selectedCardType == "Elevated") {
                        InteractiveSlider(
                            label = "Elevation",
                            value = cardElevation,
                            onValueChange = { cardElevation = it },
                            valueRange = 1f..20f
                        )
                    }
                    InteractiveSwitch(
                        label = "Show Image",
                        checked = showImage,
                        onCheckedChange = { showImage = it }
                    )
                    InteractiveSwitch(
                        label = "Enable Swipe",
                        checked = swipeEnabled,
                        onCheckedChange = { swipeEnabled = it }
                    )
                }
            }
            item {
                when (selectedCardType) {
                    "Elevated" -> ElevatedCardExample(
                        cardColor = cardColor,
                        elevation = cardElevation.dp,
                        showImage = showImage,
                        swipeEnabled = swipeEnabled
                    )
                    "Filled" -> FilledCardExample(
                        cardColor = cardColor,
                        showImage = showImage,
                        swipeEnabled = swipeEnabled
                    )
                    "Outlined" -> OutlinedCardExample(
                        cardColor = cardColor,
                        showImage = showImage,
                        swipeEnabled = swipeEnabled
                    )
                }
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                    Text("Card Grid", style = MaterialTheme.typography.headlineSmall)
                    CardGridExample(
                        cardColor = cardColor,
                        elevation = if(selectedCardType == "Elevated") cardElevation.dp else 0.dp,
                        showImage = showImage,
                        selectedCardType = selectedCardType
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ElevatedCardExample(
    cardColor: Color,
    elevation: Dp,
    showImage: Boolean,
    swipeEnabled: Boolean
) {
    CardWrapper(
        cardColor = cardColor,
        swipeEnabled = swipeEnabled,
        cardType = { modifier, colors ->
            ElevatedCard(
                modifier = modifier,
                colors = colors,
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = elevation),
                onClick = {}
            ) {
                CardContent(showImage)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilledCardExample(
    cardColor: Color,
    showImage: Boolean,
    swipeEnabled: Boolean
) {
    CardWrapper(
        cardColor = cardColor,
        swipeEnabled = swipeEnabled,
        cardType = { modifier, colors ->
            Card(
                modifier = modifier,
                colors = colors.copy(
                    containerColor = if (cardColor == Color.White) {
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)
                    } else {
                        cardColor.copy(alpha = 0.5f)
                    }
                ),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                onClick = {}
            ) {
                CardContent(showImage)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OutlinedCardExample(
    cardColor: Color,
    showImage: Boolean,
    swipeEnabled: Boolean
) {
    CardWrapper(
        cardColor = cardColor,
        swipeEnabled = swipeEnabled,
        cardType = { modifier, colors ->
            OutlinedCard(
                modifier = modifier,
                colors = colors,
                onClick = {}
            ) {
                CardContent(showImage)
            }
        }
    )
}

@Composable
private fun CardGridExample(
    cardColor: Color,
    elevation: Dp,
    showImage: Boolean,
    selectedCardType: String
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)
            .padding(vertical = 16.dp),
        content = {
            items(6) { index ->
                when (selectedCardType) {
                    "Elevated" -> ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((120 + (index % 3) * 40).dp)
                            .padding(4.dp),
                        colors = CardDefaults.elevatedCardColors(containerColor = cardColor),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = elevation),
                        onClick = {}
                    ) {
                        GridCardContent(showImage)
                    }
                    "Filled" -> Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((120 + (index % 3) * 40).dp)
                            .padding(4.dp),
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = if (cardColor == Color.White) {
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)
                            } else {
                                cardColor.copy(alpha = 0.5f)
                            }
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                        onClick = {}
                    ) {
                        GridCardContent(showImage)
                    }
                    "Outlined" -> OutlinedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((120 + (index % 3) * 40).dp)
                            .padding(4.dp),
                        colors = CardDefaults.outlinedCardColors(containerColor = cardColor),
                        onClick = {}
                    ) {
                        GridCardContent(showImage)
                    }
                }
            }
        }
    )
}

@Composable
private fun CardWrapper(
    cardColor: Color,
    swipeEnabled: Boolean,
    cardType: @Composable (Modifier, CardColors) -> Unit
) {
    val cardColors = CardDefaults.cardColors(
        containerColor = cardColor
    )
    if (swipeEnabled) {
        SwipeableCard(
            cardColors = cardColors,
            cardType = cardType
        )
    } else {
        cardType(
            Modifier
                .fillMaxWidth(),
            cardColors
        )
    }
}

@Composable
private fun CardContent(showImage: Boolean) {
    Column {
        if (showImage) {
            Image(
                painter = painterResource(R.drawable.droidcon_bg),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text("Card Title", style = MaterialTheme.typography.headlineSmall)
            Text("Secondary text", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Favorite, contentDescription = "Favorite")
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Share, contentDescription = "Share")
                }
            }
        }
    }
}

@Composable
private fun SwipeableCard(
    cardColors: CardColors,
    cardType: @Composable (Modifier, CardColors) -> Unit
) {
    val density = LocalDensity.current
    val maxSwipePx = with(density) { 80.dp.toPx() }

    var swipeOffset by remember { mutableFloatStateOf(0f) }
    val scope = rememberCoroutineScope()
    var cardHeight by remember { mutableStateOf(0.dp) }

    Box(modifier = Modifier.fillMaxWidth()) {
        if (swipeOffset < 0f) {
            Box(
                modifier = Modifier
                    .width(with(density) { swipeOffset.absoluteValue.toDp() } + 16.dp)
                    .height(cardHeight)
                    .background(
                        color = cardColors.containerColor.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            bottomStart = 0.dp,
                            topEnd = 12.dp,
                            bottomEnd = 12.dp
                        )
                    )
                    .border(
                        width = 1.dp,
                        color = if (cardColors.containerColor == Color.White)
                            MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                        else
                            cardColors.containerColor.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            bottomStart = 0.dp,
                            topEnd = 12.dp,
                            bottomEnd = 12.dp
                        )
                    )
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp)
                    .zIndex(0f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(
                        onClick = { scope.launch { swipeOffset = 0f } },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color.DarkGray
                        )
                    }
                    IconButton(
                        onClick = { scope.launch { swipeOffset = 0f } },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.DarkGray
                        )
                    }
                }
            }
        }
        cardType(
            Modifier
                .offset { IntOffset(swipeOffset.roundToInt(), 0) }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { _, dragAmount ->
                        swipeOffset = (swipeOffset + dragAmount)
                            .coerceIn(-maxSwipePx, 0f)
                    }
                }
                .onSizeChanged {
                    cardHeight = with(density) { it.height.toDp() }
                }
                .zIndex(1f),
            cardColors
        )
    }
}

@Composable
private fun GridCardContent(showImage: Boolean) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showImage) {
            Image(
                painter = painterResource(R.drawable.droidcon_bg),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = "Item",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(8.dp)
        )
    }
}