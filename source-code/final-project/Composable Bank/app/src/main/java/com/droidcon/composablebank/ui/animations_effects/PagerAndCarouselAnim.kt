package com.droidcon.composablebank.ui.animations_effects

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.droidcon.composablebank.components.InteractiveButton
import com.droidcon.composablebank.components.InteractiveColorPicker
import com.droidcon.composablebank.components.InteractiveDropdown
import com.droidcon.composablebank.components.InteractiveSlider
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.utils.CustomTopAppBar
import kotlinx.coroutines.launch
import com.droidcon.composablebank.R
import kotlin.math.abs

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun PagerAndCarouselAnim(navController: NavController, name: String) {
    var selectedExample by remember { mutableStateOf("Horizontal Pager") }
    val examples = listOf("Horizontal Pager", "Vertical Pager", "Advanced Pager", "Carousel")
    var pageCount by remember { mutableIntStateOf(5) }
    var pageSpacing by remember { mutableIntStateOf(8) }
    var infiniteScroll by remember { mutableStateOf(false) }
    var pageColor by remember { mutableStateOf(Color.Yellow) }
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        if (infiniteScroll) Int.MAX_VALUE else pageCount
    }

    val showSlidersAndSwitch = selectedExample != "Carousel"
    val showColorAndButtons = selectedExample in listOf("Horizontal Pager", "Vertical Pager")

    Scaffold(
        topBar = { CustomTopAppBar(title = name, navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                InteractiveDropdown(
                    options = examples,
                    selectedOption = selectedExample,
                    onOptionSelected = { selectedExample = it },
                    label = "Select Pager Type"
                )
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    tonalElevation = 4.dp,
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        if (showSlidersAndSwitch) {
                            InteractiveSlider(
                                label = "Page Count: $pageCount",
                                value = pageCount.toFloat(),
                                onValueChange = { pageCount = it.toInt().coerceAtLeast(1) },
                                valueRange = 1f..20f
                            )
                            InteractiveSlider(
                                label = "Page Spacing: ${pageSpacing}dp",
                                value = pageSpacing.toFloat(),
                                onValueChange = { pageSpacing = it.toInt().coerceAtLeast(0) },
                                valueRange = 0f..24f
                            )
                            InteractiveSwitch(
                                label = "Infinite Scroll",
                                checked = infiniteScroll,
                                onCheckedChange = { infiniteScroll = it }
                            )
                        }
                        if (showColorAndButtons) {
                            InteractiveColorPicker(
                                label = "Page Color",
                                selectedColor = pageColor,
                                onColorSelected = { pageColor = it }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.weight(1f)) {
                when (selectedExample) {
                    "Horizontal Pager" -> HorizontalPagerExample(
                        pagerState = pagerState,
                        pageCount = pageCount,
                        pageSpacing = pageSpacing.dp,
                        pageColor = pageColor
                    )
                    "Vertical Pager" -> VerticalPagerExample(
                        pagerState = pagerState,
                        pageCount = pageCount,
                        pageSpacing = pageSpacing.dp,
                        pageColor = pageColor
                    )
                    "Advanced Pager" -> AdvancedPagerExample(
                        pagerState = pagerState,
                        pageCount = pageCount,
                        pageSpacing = pageSpacing.dp,
                        pageColor = pageColor
                    )
                    "Carousel" -> HorizontalMultiBrowseCarouselSample()
                }
            }
            if (showColorAndButtons) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    InteractiveButton(
                        text = "Prev",
                        onClick = {
                            scope.launch {
                                val targetPage = if (infiniteScroll) {
                                    (pagerState.currentPage - 1 + pageCount) % pageCount
                                } else {
                                    (pagerState.currentPage - 1).coerceAtLeast(0)
                                }
                                pagerState.animateScrollToPage(targetPage)
                            }
                        },
                        modifier = Modifier.padding(4.dp)
                    )

                    InteractiveButton(
                        text = "Next",
                        onClick = {
                            scope.launch {
                                val targetPage = if (infiniteScroll) {
                                    (pagerState.currentPage + 1) % pageCount
                                } else {
                                    (pagerState.currentPage + 1).coerceAtMost(pageCount - 1)
                                }
                                pagerState.animateScrollToPage(targetPage)
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
private fun HorizontalPagerExample(
    pagerState: PagerState,
    pageCount: Int,
    pageSpacing: Dp,
    pageColor: Color
) {
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 32.dp),
        pageSpacing = pageSpacing,
    ) { page ->
        val actualPage = page % pageCount
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .padding(vertical = 32.dp),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp,
                pressedElevation = 4.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = pageColor
            )
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Page ${actualPage + 1}",
                    style = MaterialTheme.typography.headlineLarge
                )
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(top = 16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun VerticalPagerExample(
    pagerState: PagerState,
    pageCount: Int,
    pageSpacing: Dp,
    pageColor: Color
) {
    VerticalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 32.dp),
        pageSpacing = pageSpacing,
    ) { page ->
        val actualPage = page % pageCount
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .padding(horizontal = 32.dp),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp,
                pressedElevation = 4.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = pageColor
            )
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Page ${actualPage + 1}",
                    style = MaterialTheme.typography.headlineLarge
                )
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.Yellow,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(top = 16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AdvancedPagerExample(
    pagerState: PagerState,
    pageCount: Int,
    pageSpacing: Dp,
    pageColor: Color
) {
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 64.dp),
            pageSpacing = pageSpacing,
            flingBehavior = PagerDefaults.flingBehavior(pagerState)
        ) { page ->
            val actualPage = page % pageCount
            val pageOffset = (pagerState.currentPage - page).toFloat()

            val animatedScale by animateFloatAsState(
                targetValue = 1f - abs(pageOffset) * 0.1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            val animatedAlpha by animateFloatAsState(
                targetValue = 1f - abs(pageOffset) * 0.7f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .graphicsLayer {
                        scaleX = animatedScale
                        scaleY = animatedScale
                        alpha = animatedAlpha
                    },
                shape = MaterialTheme.shapes.large,
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = pageColor)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.droidcon_bg),
                        contentDescription = "Page ${actualPage + 1}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${actualPage + 1}",
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.White
                        )
                    }
                }
            }
        }

        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pageCount) { iteration ->
                val isSelected = pagerState.currentPage % pageCount == iteration

                val animatedSize by animateDpAsState(
                    targetValue = if (isSelected) 16.dp else 8.dp,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )

                val animatedColor by animateColorAsState(
                    targetValue = if (isSelected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(animatedColor)
                        .size(animatedSize)
                        .animateContentSize()
                        .clickable {
                            scope.launch {
                                pagerState.animateScrollToPage(
                                    page = iteration + (pagerState.currentPage / pageCount) * pageCount
                                )
                            }
                        }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorizontalMultiBrowseCarouselSample() {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val pageCount = 8
    val pageSpacing = 8.dp

    val items = remember {
        List(pageCount) { index ->
            CarouselItem(
                id = index,
                imageResId = R.drawable.droidcon_bg,
                contentDescriptionResId = R.string.app_name
            )
        }
    }

    val carouselState = rememberCarouselState(
        itemCount = { items.size }
    )

    HorizontalMultiBrowseCarousel(
        state = carouselState,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        preferredItemWidth = (screenWidth * 0.7f),
        itemSpacing = pageSpacing,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) { index ->
        val item = items.getOrNull(index) ?: return@HorizontalMultiBrowseCarousel

        Card(
            modifier = Modifier
                .width(400.dp)
                .height(300.dp)
                .padding(horizontal = 8.dp),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp,
                pressedElevation = 4.dp
            ),
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { },
                painter = painterResource(id = item.imageResId),
                contentDescription = stringResource(id = item.contentDescriptionResId),
                contentScale = ContentScale.Crop
            )
        }
    }
}

data class CarouselItem(
    val id: Int,
    @DrawableRes val imageResId: Int,
    @StringRes val contentDescriptionResId: Int
)