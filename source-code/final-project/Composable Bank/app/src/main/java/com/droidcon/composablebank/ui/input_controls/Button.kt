package com.droidcon.composablebank.ui.input_controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.droidcon.composablebank.utils.CustomTopAppBar
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.droidcon.composablebank.components.InteractiveRadioButtonGroup
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.components.InteractiveColorPicker
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Buttons(navController: NavController, name: String) {
    var showSnackbar by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    var buttonStyle by remember { mutableStateOf("Filled") }
    var showIcon by remember { mutableStateOf(true) }
    var isLoading by remember { mutableStateOf(false) }
    var buttonColor by remember { mutableStateOf(Color.Blue) }
    var useExtendedFab by remember { mutableStateOf(false) }

    val musicGenres = listOf("Rock", "Jazz", "Pop")
    val hobbies = listOf("Leer", "Jugar", "Cocinar", "Viajar")
    var selectedSingle by remember { mutableIntStateOf(0) }
    var selectedMulti by remember { mutableStateOf(List(hobbies.size) { false }) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { CustomTopAppBar(title = name, navController = navController) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            CustomFAB(
                useExtended = useExtendedFab,
                color = buttonColor,
                onClick = { showSnackbar = true }
            )
        },
        content = { paddingValues ->
            MainContent(
                paddingValues = paddingValues,
                buttonStyle = buttonStyle,
                showIcon = showIcon,
                isLoading = isLoading,
                buttonColor = buttonColor,
                useExtendedFab = useExtendedFab,
                musicGenres = musicGenres,
                hobbies = hobbies,
                selectedSingle = selectedSingle,
                selectedMulti = selectedMulti,
                onStyleChange = { buttonStyle = it },
                onIconChange = { showIcon = it },
                onLoadingChange = { isLoading = it },
                onColorChange = { buttonColor = it },
                onFabTypeChange = { useExtendedFab = it },
                onSingleSelect = { selectedSingle = it },
                onMultiSelect = { selectedMulti = it },
                onButtonClick = { showSnackbar = true }
            )
        }
    )

    if (showSnackbar) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar("${buttonStyle.uppercase()} button clicked")
            showSnackbar = false
        }
    }
}

@Composable
private fun MainContent(
    paddingValues: PaddingValues,
    buttonStyle: String,
    showIcon: Boolean,
    isLoading: Boolean,
    buttonColor: Color,
    useExtendedFab: Boolean,
    musicGenres: List<String>,
    hobbies: List<String>,
    selectedSingle: Int,
    selectedMulti: List<Boolean>,
    onStyleChange: (String) -> Unit,
    onIconChange: (Boolean) -> Unit,
    onLoadingChange: (Boolean) -> Unit,
    onColorChange: (Color) -> Unit,
    onFabTypeChange: (Boolean) -> Unit,
    onSingleSelect: (Int) -> Unit,
    onMultiSelect: (List<Boolean>) -> Unit,
    onButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ButtonSettingsPanel(
            buttonStyle = buttonStyle,
            showIcon = showIcon,
            isLoading = isLoading,
            buttonColor = buttonColor,
            useExtendedFab = useExtendedFab,
            onStyleChange = onStyleChange,
            onIconChange = onIconChange,
            onLoadingChange = onLoadingChange,
            onColorChange = onColorChange,
            onFabTypeChange = onFabTypeChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        DynamicButton(
            style = buttonStyle,
            showIcon = showIcon,
            isLoading = isLoading,
            color = buttonColor,
            onClick = onButtonClick
        )

        IconButtonSection(buttonColor, onButtonClick)

        SegmentedButtonGroup(
            title = "Music Genres",
            options = musicGenres,
            selectedIndices = List(musicGenres.size) { it == selectedSingle },
            isMultiSelect = false,
            onSelectionChange = { list ->
                val index = list.indexOfFirst { it }
                if (index != -1) onSingleSelect(index)
            },
            buttonColor = buttonColor
        )

        SegmentedButtonGroup(
            title = "Hobbies",
            options = hobbies,
            selectedIndices = selectedMulti,
            isMultiSelect = true,
            onSelectionChange = onMultiSelect,
            buttonColor = buttonColor
        )
        Spacer(Modifier.height(100.dp))
    }
}

@Composable
private fun ButtonSettingsPanel(
    buttonStyle: String,
    showIcon: Boolean,
    isLoading: Boolean,
    buttonColor: Color,
    useExtendedFab: Boolean,
    onStyleChange: (String) -> Unit,
    onIconChange: (Boolean) -> Unit,
    onLoadingChange: (Boolean) -> Unit,
    onColorChange: (Color) -> Unit,
    onFabTypeChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        InteractiveRadioButtonGroup(
            options = listOf("Filled", "Tonal", "Outlined", "Text", "Elevated"),
            selectedOption = buttonStyle,
            onOptionSelected = onStyleChange
        )
        InteractiveSwitch(
            label = "Show Icon",
            checked = showIcon,
            onCheckedChange = onIconChange
        )
        InteractiveSwitch(
            label = "Loading State",
            checked = isLoading,
            onCheckedChange = onLoadingChange
        )
        InteractiveColorPicker(
            label = "Button Color",
            selectedColor = buttonColor,
            onColorSelected = onColorChange
        )
        InteractiveSwitch(
            label = "Extended FAB",
            checked = useExtendedFab,
            onCheckedChange = onFabTypeChange
        )
    }
}

@Composable
private fun DynamicButton(
    style: String,
    showIcon: Boolean,
    isLoading: Boolean,
    color: Color,
    onClick: () -> Unit
) {
    val buttonModifier = Modifier.fillMaxWidth(0.8f)

    when (style) {
        "Filled" -> Button(
            onClick = onClick,
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = color,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = buttonModifier
        ) {
            ButtonContent(icon = Icons.Default.Check, text = "Filled Button", showIcon, isLoading)
        }
        "Tonal" -> FilledTonalButton(
            onClick = onClick,
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = color.copy(alpha = 0.2f),
                contentColor = color
            ),
            modifier = buttonModifier
        ) {
            ButtonContent(icon = Icons.Default.Edit, text = "Tonal Button", showIcon, false)
        }
        "Outlined" -> OutlinedButton(
            onClick = onClick,
            enabled = !isLoading,
            colors = ButtonDefaults.outlinedButtonColors(contentColor = color),
            modifier = buttonModifier
        ) {
            ButtonContent(icon = Icons.Default.Delete, text = "Outlined Button", showIcon, false)
        }
        "Text" -> TextButton(
            onClick = onClick,
            enabled = !isLoading,
            modifier = buttonModifier
        ) {
            ButtonContent(icon = Icons.Default.Share, text = "Text Button", showIcon, false)
        }
        "Elevated" -> ElevatedButton(
            onClick = onClick,
            enabled = !isLoading,
            elevation = ButtonDefaults.elevatedButtonElevation(),
            modifier = buttonModifier
        ) {
            ButtonContent(icon = Icons.Default.Star, text = "Elevated Button", showIcon, false)
        }
    }
}

@Composable
private fun ButtonContent(
    icon: ImageVector,
    text: String,
    showIcon: Boolean,
    isLoading: Boolean
) {
    if (showIcon) Icon(icon, null, modifier = Modifier.size(18.dp))
    Text(text, modifier = Modifier.padding(start = 8.dp))
    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(24.dp)
                .padding(start = 8.dp),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun IconButtonSection(
    buttonColor: Color,
    onClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Icon Button",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(8.dp)
        )
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(56.dp)
                .background(
                    color = buttonColor.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Icon(Icons.Default.Settings, null, tint = buttonColor)
        }
    }
}

@Composable
private fun SegmentedButtonGroup(
    title: String,
    options: List<String>,
    selectedIndices: List<Boolean>,
    isMultiSelect: Boolean,
    onSelectionChange: (List<Boolean>) -> Unit,
    buttonColor: Color
) {
    Column(modifier = Modifier.fillMaxWidth(0.8f)) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(8.dp)
        )

        if (isMultiSelect) {
            MultiChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                options.forEachIndexed { index, option ->
                    SegmentedButton(
                        checked = selectedIndices[index],
                        onCheckedChange = {
                            val newList = selectedIndices.toMutableList()
                            newList[index] = it
                            onSelectionChange(newList)
                        },
                        shape = getSegmentedButtonShape(index, options.size),
                        modifier = Modifier.weight(1f)
                    ) {
                        SegmentedButtonContent(
                            option = option,
                            selected = selectedIndices[index],
                            buttonColor = buttonColor
                        )
                    }
                }
            }
        } else {
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                options.forEachIndexed { index, option ->
                    SegmentedButton(
                        selected = selectedIndices[index],
                        onClick = {
                            onSelectionChange(List(options.size) { it == index })
                        },
                        shape = getSegmentedButtonShape(index, options.size),
                        modifier = Modifier.weight(1f)
                    ) {
                        SegmentedButtonContent(
                            option = option,
                            selected = selectedIndices[index],
                            buttonColor = buttonColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SegmentedButtonContent(
    option: String,
    selected: Boolean,
    buttonColor: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = when (option) {
                "Rock" -> Icons.Default.MusicNote
                "Jazz" -> Icons.Default.Brush
                "Pop" -> Icons.Default.Star
                "Read" -> Icons.Default.Book
                "Play" -> Icons.Default.SportsEsports
                "Cook" -> Icons.Default.Kitchen
                "Travel" -> Icons.Default.Luggage
                else -> Icons.Default.Help
            },
            contentDescription = option,
            tint = if (selected) buttonColor else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = option,
            style = MaterialTheme.typography.labelSmall,
            color = if (selected) buttonColor else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun CustomFAB(
    useExtended: Boolean,
    color: Color,
    onClick: () -> Unit
) {
    if (useExtended) {
        ExtendedFloatingActionButton(
            text = { Text("Extended FAB") },
            icon = { Icon(Icons.Default.Add, "Add") },
            onClick = onClick,
            containerColor = color,
            modifier = Modifier.padding(16.dp)
        )
    } else {
        FloatingActionButton(
            onClick = onClick,
            containerColor = color,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(Icons.Default.Add, "Add")
        }
    }
}

private fun getSegmentedButtonShape(index: Int, total: Int): Shape {
    return when (index) {
        0 -> RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
        total - 1 -> RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
        else -> RoundedCornerShape(0.dp)
    }
}