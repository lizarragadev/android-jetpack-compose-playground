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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Buttons(navController: NavController, name: String) {
    var showSnackbar by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    var buttonStyle by remember { mutableStateOf("filled") }
    var showIcon by remember { mutableStateOf(true) }
    var isLoading by remember { mutableStateOf(false) }
    var buttonColor by remember { mutableStateOf(Color.Blue) }
    var useExtendedFab by remember { mutableStateOf(false) }

    val musicGenres = listOf("Rock", "Jazz", "Pop")
    val hobbies = listOf("Leer", "Jugar", "Cocinar", "Viajar")
    var selectedSingle by remember { mutableIntStateOf(0) }
    var selectedMulti by remember { mutableStateOf(listOf(false, false, false, false)) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { CustomTopAppBar(title = name, navController = navController) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            if (useExtendedFab) {
                ExtendedFloatingActionButton(
                    text = { Text("Extended FAB") },
                    icon = { Icon(Icons.Default.Add, contentDescription = "Add") },
                    onClick = { showSnackbar = true },
                    containerColor = buttonColor,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                FloatingActionButton(
                    onClick = { showSnackbar = true },
                    containerColor = buttonColor,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        },
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
                        .padding(16.dp)
                ) {
                    InteractiveRadioButtonGroup(
                        options = listOf("Filled", "Tonal", "Outlined", "Text", "Elevated"),
                        selectedOption = buttonStyle,
                        onOptionSelected = { buttonStyle = it.lowercase() }
                    )
                    InteractiveSwitch(
                        label = "Show Icon",
                        checked = showIcon,
                        onCheckedChange = { showIcon = it }
                    )
                    InteractiveSwitch(
                        label = "Loading State",
                        checked = isLoading,
                        onCheckedChange = { isLoading = it }
                    )
                    InteractiveColorPicker(
                        label = "Button Color",
                        selectedColor = buttonColor,
                        onColorSelected = { buttonColor = it }
                    )
                    InteractiveSwitch(
                        label = "Extended FAB",
                        checked = useExtendedFab,
                        onCheckedChange = { useExtendedFab = it }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                when (buttonStyle) {
                    "filled" -> Button(
                        onClick = { showSnackbar = true },
                        enabled = !isLoading,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = buttonColor,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.fillMaxWidth(0.8f)
                    ) {
                        if (showIcon) Icon(Icons.Default.Check, "Check", modifier = Modifier.size(18.dp))
                        Text("Filled Button", modifier = Modifier.padding(start = 8.dp))
                        if (isLoading) CircularProgressIndicator(
                            modifier = Modifier.size(24.dp).padding(start = 8.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    "tonal" -> FilledTonalButton(
                        onClick = { showSnackbar = true },
                        enabled = !isLoading,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = buttonColor.copy(alpha = 0.2f),
                            contentColor = buttonColor
                        ),
                        modifier = Modifier.fillMaxWidth(0.8f)
                    ) {
                        if (showIcon) Icon(Icons.Default.Edit, "Edit", modifier = Modifier.size(18.dp))
                        Text("Tonal Button", modifier = Modifier.padding(start = 8.dp))
                    }

                    "outlined" -> OutlinedButton(
                        onClick = { showSnackbar = true },
                        enabled = !isLoading,
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = buttonColor
                        ),
                        modifier = Modifier.fillMaxWidth(0.8f)
                    ) {
                        if (showIcon) Icon(Icons.Default.Delete, "Delete", modifier = Modifier.size(18.dp))
                        Text("Outlined Button", modifier = Modifier.padding(start = 8.dp))
                    }

                    "text" -> TextButton(
                        onClick = { showSnackbar = true },
                        enabled = !isLoading,
                        modifier = Modifier.fillMaxWidth(0.8f)
                    ) {
                        if (showIcon) Icon(Icons.Default.Share, "Share", modifier = Modifier.size(18.dp))
                        Text("Text Button", modifier = Modifier.padding(start = 8.dp))
                    }

                    "elevated" -> ElevatedButton(
                        onClick = { showSnackbar = true },
                        enabled = !isLoading,
                        elevation = ButtonDefaults.elevatedButtonElevation(),
                        modifier = Modifier.fillMaxWidth(0.8f)
                    ) {
                        if (showIcon) Icon(Icons.Default.Star, "Star", modifier = Modifier.size(18.dp))
                        Text("Elevated Button", modifier = Modifier.padding(start = 8.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Icon Button",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(8.dp)
                )
                IconButton(
                    onClick = { showSnackbar = true },
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            color = buttonColor.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    Icon(Icons.Default.Settings, "Settings", tint = buttonColor)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Music Genres",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(8.dp)
                )
                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier.fillMaxWidth(0.8f),
                ) {
                    musicGenres.forEachIndexed { index, genre ->
                        SegmentedButton(
                            selected = selectedSingle == index,
                            onClick = { selectedSingle = index },
                            shape = when (index) {
                                0 -> RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
                                musicGenres.lastIndex -> RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
                                else -> RoundedCornerShape(0.dp)
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = when (genre) {
                                        "Rock" -> Icons.Default.MusicNote
                                        "Jazz" -> Icons.Default.Brush
                                        "Pop" -> Icons.Default.Star
                                        else -> Icons.Default.Delete
                                    },
                                    contentDescription = genre,
                                    tint = if (selectedSingle == index)
                                        buttonColor
                                    else
                                        MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = genre,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (selectedSingle == index)
                                        buttonColor
                                    else
                                        MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Hobbies",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(8.dp)
                )
                MultiChoiceSegmentedButtonRow(
                    modifier = Modifier.fillMaxWidth(0.8f),
                ) {
                    hobbies.forEachIndexed { index, hobby ->
                        SegmentedButton(
                            checked = selectedMulti[index],
                            onCheckedChange = {
                                val newList = selectedMulti.toMutableList()
                                newList[index] = !newList[index]
                                selectedMulti = newList
                            },
                            shape = when (index) {
                                0 -> RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
                                hobbies.lastIndex -> RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
                                else -> RoundedCornerShape(0.dp)
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = when (hobby) {
                                        "Leer" -> Icons.Default.Book
                                        "Jugar" -> Icons.Default.SportsEsports
                                        "Cocinar" -> Icons.Default.Kitchen
                                        "Viajar" -> Icons.Default.Luggage
                                        else -> Icons.Default.Help
                                    },
                                    contentDescription = hobby,
                                    tint = if (selectedMulti[index])
                                        buttonColor
                                    else
                                        MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = hobby,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (selectedMulti[index])
                                        buttonColor
                                    else
                                        MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }
    )

    if (showSnackbar) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar("${buttonStyle.uppercase()} button clicked")
            showSnackbar = false
        }
    }
}