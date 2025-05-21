package com.droidcon.composablebank.ui.basic_comp_layouts

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.droidcon.composablebank.components.InteractiveColorPicker
import com.droidcon.composablebank.components.InteractiveDropdown
import com.droidcon.composablebank.components.InteractiveSlider
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.utils.CustomTopAppBar
import com.droidcon.composablebank.R
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalContext
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.CachePolicy
import coil.request.ImageRequest
import kotlin.random.Random

@Composable
internal fun ImageComposable(navController: NavController, name: String) {
    var imageColor by remember { mutableStateOf(Color.White) }
    var contentScale by remember { mutableStateOf(ContentScale.Fit) }
    var alignment by remember { mutableStateOf(Alignment.Center) }
    var alpha by remember { mutableFloatStateOf(1.0f) }
    var showPlaceholder by remember { mutableStateOf(false) }
    var selectedImageType by remember { mutableStateOf("Basic") }
    var modifierType by remember { mutableStateOf("None") }
    var brightness by remember { mutableFloatStateOf(0f) }
    var showColorFilter by remember { mutableStateOf(false) }
    var showTransformations by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf("None") }
    var rotation by remember { mutableFloatStateOf(0f) }
    var scale by remember { mutableFloatStateOf(1f) }
    var translationX by remember { mutableFloatStateOf(0f) }
    var translationY by remember { mutableFloatStateOf(0f) }

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
                ImageTypeControls(
                    selectedImageType = selectedImageType,
                    imageColor = imageColor,
                    contentScale = contentScale,
                    alignment = alignment,
                    alpha = alpha,
                    showPlaceholder = showPlaceholder,
                    modifierType = modifierType,
                    brightness = brightness,
                    showColorFilter = showColorFilter,
                    showTransformations = showTransformations,
                    onBrightnessChange = { brightness = it },
                    onImageTypeChange = { selectedImageType = it },
                    onColorChange = { imageColor = it },
                    onScaleChange = { contentScale = it },
                    onAlignmentChange = { alignment = it },
                    onAlphaChange = { alpha = it },
                    onPlaceholderChange = { showPlaceholder = it },
                    onModifierTypeChange = { modifierType = it },
                    onShowColorFilterChange = { showColorFilter = it },
                    onShowTransformationsChange = { showTransformations = it },
                )
            }
            if (selectedImageType == "Basic") {
                if (showColorFilter) {
                    item {
                        ImageColorFilterSection(
                            selectedFilter = selectedFilter,
                            tintColor = imageColor,
                            onFilterSelected = { selectedFilter = it },
                            onColorSelected = { imageColor = it }
                        )
                    }
                }
                if (showTransformations) {
                    item {
                        ImageTransformationsSection(
                            rotation = rotation,
                            scale = scale,
                            translationX = translationX,
                            translationY = translationY,
                            onRotationChange = { rotation = it },
                            onScaleChange = { scale = it },
                            onTranslationXChange = { translationX = it },
                            onTranslationYChange = { translationY = it },
                        )
                    }
                }
            }
            item {
                ImagePreviewSection(
                    selectedImageType = selectedImageType,
                    contentScale = contentScale,
                    alignment = alignment,
                    alpha = alpha,
                    brightness = brightness,
                    showPlaceholder = showPlaceholder,
                    modifierType = modifierType,
                    rotation = rotation.takeIf { showTransformations } ?: 0f,
                    scale = scale.takeIf { showTransformations } ?: 1f,
                    translationX = translationX.takeIf { showTransformations } ?: 0f,
                    translationY = translationY.takeIf { showTransformations } ?: 0f,
                    selectedFilter = selectedFilter,
                    tintColor = imageColor,
                    showTransformations = showTransformations
                )
            }
        }
    }
}

@Composable
private fun ImageColorFilterSection(
    selectedFilter: String,
    tintColor: Color,
    onFilterSelected: (String) -> Unit,
    onColorSelected: (Color) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InteractiveDropdown(
            label = "Filter Type",
            options = listOf("None", "Grayscale", "Sepia", "Tint"),
            selectedOption = selectedFilter,
            onOptionSelected = onFilterSelected
        )

        when(selectedFilter) {
            "Tint" -> InteractiveColorPicker(
                label = "Tint Color",
                selectedColor = tintColor,
                onColorSelected = onColorSelected
            )
        }
    }
}

@Composable
private fun ImageTransformationsSection(
    rotation: Float,
    scale: Float,
    translationX: Float,
    translationY: Float,
    onRotationChange: (Float) -> Unit,
    onScaleChange: (Float) -> Unit,
    onTranslationXChange: (Float) -> Unit,
    onTranslationYChange: (Float) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InteractiveSlider(
            label = "Rotation",
            value = rotation,
            onValueChange = onRotationChange,
            valueRange = 0f..360f
        )
        InteractiveSlider(
            label = "Scale",
            value = scale,
            onValueChange = onScaleChange,
            valueRange = 0.5f..2f
        )
        InteractiveSlider(
            label = "Translate X",
            value = translationX,
            onValueChange = onTranslationXChange,
            valueRange = -100f..100f
        )
        InteractiveSlider(
            label = "Translate Y",
            value = translationY,
            onValueChange = onTranslationYChange,
            valueRange = -100f..100f
        )
    }
}

@Composable
private fun ImageTypeControls(
    selectedImageType: String,
    imageColor: Color,
    contentScale: ContentScale,
    alignment: Alignment,
    alpha: Float,
    showPlaceholder: Boolean,
    modifierType: String,
    brightness: Float,
    showColorFilter: Boolean,
    showTransformations: Boolean,
    onImageTypeChange: (String) -> Unit,
    onColorChange: (Color) -> Unit,
    onScaleChange: (ContentScale) -> Unit,
    onAlignmentChange: (Alignment) -> Unit,
    onAlphaChange: (Float) -> Unit,
    onPlaceholderChange: (Boolean) -> Unit,
    onModifierTypeChange: (String) -> Unit,
    onShowColorFilterChange: (Boolean) -> Unit,
    onShowTransformationsChange: (Boolean) -> Unit,
    onBrightnessChange: (Float) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        InteractiveDropdown(
            label = "Image Type",
            options = listOf("Basic", "Network", "Vector", "Bitmap"),
            selectedOption = selectedImageType,
            onOptionSelected = onImageTypeChange
        )

        if (selectedImageType !in listOf("Basic", "Bitmap", "Network")) {
            InteractiveColorPicker(
                label = "Image Color",
                selectedColor = imageColor,
                onColorSelected = onColorChange
            )
        }

        if (selectedImageType in listOf("Basic", "Vector", "Bitmap")) {
            ContentScaleSelector(currentScale = contentScale) {
                onScaleChange(it)
            }
        }

        if (selectedImageType !in listOf("Vector", "Network")) {
            AlignmentSelector(currentAlignment = alignment) {
                onAlignmentChange(it)
            }
        }

        InteractiveSlider(
            label = "Alpha ${alpha.format(1)}",
            value = alpha,
            onValueChange = onAlphaChange,
            valueRange = 0.0f..1.0f
        )

        if (selectedImageType !in listOf("Vector", "Bitmap", "Network")) {
            InteractiveSwitch(
                label = "Show Progress Placeholder",
                checked = showPlaceholder,
                onCheckedChange = onPlaceholderChange
            )
        }

        if (selectedImageType !in listOf("Vector", "Bitmap", "Network")) {
            InteractiveDropdown(
                label = "Modifier Type",
                options = listOf("None", "Clip", "Border", "Combined"),
                selectedOption = modifierType,
                onOptionSelected = onModifierTypeChange
            )
        }

        if (selectedImageType !in listOf("Basic", "Vector")) {
            InteractiveSlider(
                label = "Brightness ${(brightness * 100).format(1)}%",
                value = brightness,
                onValueChange = { newValue ->
                    onBrightnessChange(newValue.coerceIn(-1f, 1f))
                },
                valueRange = -1.0f..1.0f
            )
        }

        if (selectedImageType == "Basic") {
            InteractiveSwitch(
                label = "Show Color Filters",
                checked = showColorFilter,
                onCheckedChange = onShowColorFilterChange
            )
            InteractiveSwitch(
                label = "Show Transformations",
                checked = showTransformations,
                onCheckedChange = onShowTransformationsChange
            )
        }
    }
}

private fun Float.format(digits: Int) =
    String.format("%.${digits}f", this)

@Composable
private fun ImagePreviewSection(
    selectedImageType: String,
    contentScale: ContentScale,
    alignment: Alignment,
    alpha: Float,
    brightness: Float,
    showPlaceholder: Boolean,
    modifierType: String,
    rotation: Float = 0f,
    scale: Float = 1f,
    translationX: Float = 0f,
    translationY: Float = 0f,
    selectedFilter: String,
    tintColor: Color,
    showTransformations: Boolean
) {
    val sepiaMatrix = remember {
        ColorMatrix(
            floatArrayOf(
                0.393f, 0.769f, 0.189f, 0f, 0f,
                0.349f, 0.686f, 0.168f, 0f, 0f,
                0.272f, 0.534f, 0.131f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        when(selectedImageType) {
            "Basic" -> ImageBasicExample(
                contentScale = contentScale,
                alignment = alignment,
                alpha = alpha,
                modifierType = modifierType,
                showPlaceholder = showPlaceholder,
                rotationParam = rotation,
                scaleParam = scale,
                translationXParam = translationX,
                translationYParam = translationY,
                colorFilter = when(selectedFilter) {
                    "Grayscale" -> ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
                    "Sepia" -> ColorFilter.colorMatrix(sepiaMatrix)
                    "Tint" -> ColorFilter.tint(tintColor)
                    else -> null
                }
            )
            "Network" -> ImageNetworkExample(
                alpha = alpha,
                brightness = brightness
            )
            "Vector" -> ImageVectorExample(
                contentScale = contentScale,
                alpha = alpha,
                modifierType = modifierType,
                tintColor = tintColor,
            )
            "Bitmap" -> ImageBitmapExample(
                contentScale = contentScale,
                alignment = alignment,
                alpha = alpha,
                brightness = brightness,
                modifierType = modifierType,
                rotation = rotation.takeIf { showTransformations } ?: 0f,
                scale = scale.takeIf { showTransformations } ?: 1f,
                translationX = translationX.takeIf { showTransformations } ?: 0f,
                translationY = translationY.takeIf { showTransformations } ?: 0f,
            )
        }
    }
}

@Composable
private fun ImageBasicExample(
    contentScale: ContentScale,
    alignment: Alignment,
    alpha: Float,
    modifierType: String,
    showPlaceholder: Boolean,
    rotationParam: Float = 0f,
    scaleParam: Float = 1f,
    translationXParam: Float = 0f,
    translationYParam: Float = 0f,
    colorFilter: ColorFilter? = null
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .applyImageModifiers(modifierType)
            .alpha(alpha)
            .graphicsLayer {
                rotationZ = rotationParam
                scaleX = scaleParam
                scaleY = scaleParam
                translationX = translationXParam
                translationY = translationYParam
            }
    ) {
        Image(
            painter = painterResource(R.drawable.droidcon_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = contentScale,
            alignment = alignment,
            colorFilter = colorFilter
        )

        if(showPlaceholder) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

private fun Modifier.applyImageModifiers(type: String): Modifier {
    return when(type) {
        "Clip" -> this.clip(RoundedCornerShape(16.dp))
        "Border" -> this.border(2.dp, Color.Red)
        "Combined" -> this
            .clip(RoundedCornerShape(12.dp))
            .border(2.dp, Color.Green)
        else -> this
    }
}


@Composable
private fun rememberBrightnessColorMatrix(brightness: Float): ColorMatrix {
    return remember(brightness) {
        ColorMatrix().apply {
            reset()
            this[0, 0] = 1f + brightness
            this[1, 1] = 1f + brightness
            this[2, 2] = 1f + brightness
        }
    }
}

@Composable
private fun ImageNetworkExample(
    alpha: Float = 1f,
    brightness: Float = 0f
) {
    val pexelsImageIds = listOf(
        2000000, 2300003, 2500000, 2750000, 3000000,
        3330000, 3500000, 3700000, 4000000, 4310020,
        4500000, 4700000, 5000000, 5300020, 5400300,
        5500000, 5700100, 5800000, 5900000, 6000000
    )

    var currentImageIndex by remember { mutableIntStateOf(0) }
    val imageId = pexelsImageIds[currentImageIndex]
    val cacheBuster = remember { Random.nextInt() }

    val originalImageUrl = "https://images.pexels.com/photos/$imageId/pexels-photo-$imageId.jpeg?cache=$cacheBuster&auto=compress&cs=tinysrgb&fit=crop&h=627&w=1200"
    val thumbnailUrl = "$originalImageUrl?auto=compress&cs=tinysrgb&h=130"

    var isOriginalReady by remember { mutableStateOf(false) }
    var imageSize by remember { mutableStateOf(IntSize.Zero) }

    val colorMatrix = rememberBrightnessColorMatrix(brightness)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (imageSize.height > 0) {
                        Modifier.aspectRatio(imageSize.width.toFloat() / imageSize.height)
                    } else {
                        Modifier.fillMaxWidth()
                    }
                )
                .clip(RoundedCornerShape(12.dp))
        ) {
            if (!isOriginalReady) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(thumbnailUrl)
                        .memoryCachePolicy(CachePolicy.DISABLED)
                        .diskCachePolicy(CachePolicy.DISABLED)
                        .build(),
                    contentDescription = "Thumbnail",
                    contentScale = ContentScale.Crop,
                    loading = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(48.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    success = { state ->
                        SubcomposeAsyncImageContent(
                            modifier = Modifier
                                .fillMaxSize()
                                .blur(radius = 12.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                )
            }

            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(originalImageUrl)
                    .memoryCachePolicy(CachePolicy.DISABLED)
                    .diskCachePolicy(CachePolicy.DISABLED)
                    .build(),
                contentDescription = "Original Image",
                modifier = Modifier.fillMaxSize()
                    .alpha(alpha),
                colorFilter = ColorFilter.colorMatrix(colorMatrix),
                contentScale = ContentScale.Crop,
                onSuccess = { state ->
                    state.painter.intrinsicSize.let {
                        imageSize = IntSize(it.width.toInt(), it.height.toInt())
                    }
                    isOriginalReady = true
                },
                loading = {
                },
                error = { state ->
                    isOriginalReady = true
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.ErrorOutline,
                            contentDescription = "Error",
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = "Error: ${state.result.throwable.message ?: "Unknown"}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                currentImageIndex = (currentImageIndex + 1) % pexelsImageIds.size
                isOriginalReady = false
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Load New Image")
        }
    }
}

@Composable
private fun ImageVectorExample(
    contentScale: ContentScale,
    alpha: Float,
    modifierType: String,
    tintColor: Color,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Android,
            contentDescription = "Vector Image",
            modifier = Modifier
                .size(250.dp)
                .applyImageModifiers(modifierType)
                .alpha(alpha)
                .graphicsLayer {
                    when(contentScale) {
                        ContentScale.Crop -> scaleX = 1.5f
                        ContentScale.Fit -> scaleX = 0.8f
                        else -> Unit
                    }
                },
            tint = tintColor.copy(alpha = alpha),
        )
    }
}

@Composable
private fun ImageBitmapExample(
    contentScale: ContentScale,
    alignment: Alignment,
    alpha: Float,
    brightness: Float,
    modifierType: String,
    rotation: Float,
    scale: Float,
    translationX: Float,
    translationY: Float,
) {
    val originalBitmap = ImageBitmap.imageResource(R.drawable.droidcon_bg)

    val colorMatrix = rememberBrightnessColorMatrix(brightness)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray.copy(alpha = 0.3f))
        ) {
            Image(
                bitmap = originalBitmap,
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .align(alignment)
                    .applyImageModifiers(modifierType)
                    .alpha(alpha)
                    .graphicsLayer {
                        rotationZ = rotation
                        scaleX = scale
                        scaleY = scale
                        this.translationX = translationX * 2f
                        this.translationY = translationY * 2f
                    },
                contentScale = contentScale,
                colorFilter = ColorFilter.colorMatrix(colorMatrix)
            )
        }
    }
}

@Composable
private fun ContentScaleSelector(
    currentScale: ContentScale,
    onScaleSelected: (ContentScale) -> Unit
) {
    val scaleOptions = listOf(
        ContentScale.None to "None",
        ContentScale.Fit to "Fit",
        ContentScale.Crop to "Crop",
        ContentScale.FillBounds to "Fill Bounds",
        ContentScale.FillWidth to "Fill Width",
        ContentScale.FillHeight to "Fill Height",
        ContentScale.Inside to "Inside"
    )

    val selectedLabel = scaleOptions.find { it.first == currentScale }?.second ?: "Fit"

    InteractiveDropdown(
        label = "Content Scale",
        options = scaleOptions.map { it.second },
        selectedOption = selectedLabel,
        onOptionSelected = { selectedName ->
            val selectedScale = scaleOptions.find { it.second == selectedName }?.first
            selectedScale?.let { onScaleSelected(it) }
        }
    )
}

@Composable
private fun AlignmentSelector(
    currentAlignment: Alignment,
    onAlignmentSelected: (Alignment) -> Unit
) {
    val alignmentOptions = listOf(
        "TopStart" to Alignment.TopStart,
        "TopCenter" to Alignment.TopCenter,
        "TopEnd" to Alignment.TopEnd,
        "CenterStart" to Alignment.CenterStart,
        "Center" to Alignment.Center,
        "CenterEnd" to Alignment.CenterEnd,
        "BottomStart" to Alignment.BottomStart,
        "BottomCenter" to Alignment.BottomCenter,
        "BottomEnd" to Alignment.BottomEnd
    )

    val selectedIndex = alignmentOptions.indexOfFirst { it.second == currentAlignment }
    val selectedLabel = alignmentOptions.getOrNull(selectedIndex)?.first ?: "Center"

    InteractiveDropdown(
        label = "Alignment",
        options = alignmentOptions.map { it.first },
        selectedOption = selectedLabel,
        onOptionSelected = { selectedName ->
            val selectedAlignment = alignmentOptions.find { it.first == selectedName }?.second
            selectedAlignment?.let { onAlignmentSelected(it) }
        }
    )
}