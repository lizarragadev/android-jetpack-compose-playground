package com.droidcon.composablebank.ui.input_controls

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.ui.Alignment
import com.droidcon.composablebank.components.InteractiveButton
import com.droidcon.composablebank.components.InteractiveColorPicker
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.utils.CustomTopAppBar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.compositeOver
import com.droidcon.composablebank.components.InteractiveTextField

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TimePickerControl(navController: NavController, name: String) {
    var showDialogPicker by remember { mutableStateOf(false) }
    var useCustomColors by remember { mutableStateOf(false) }
    var selectedColor by remember { mutableStateOf(Color.Gray) }
    var is24HourFormat by remember { mutableStateOf(false) }

    val initialTime = remember { LocalTime.now() }

    var timeState by remember {
        mutableStateOf(
            TimePickerState(
                initialHour = initialTime.hour,
                initialMinute = initialTime.minute,
                is24Hour = is24HourFormat
            )
        )
    }

    Scaffold(
        topBar = { CustomTopAppBar(title = name, navController = navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                TimePicker(
                    state = timeState,
                    colors = if (useCustomColors) {
                        TimePickerDefaults.colors(
                            clockDialColor = selectedColor.copy(alpha = 0.3f),
                            selectorColor = selectedColor.copy(alpha = 0.9f),
                            containerColor = MaterialTheme.colorScheme.surface,
                            clockDialSelectedContentColor = Color.White,
                            clockDialUnselectedContentColor = Color.DarkGray,
                            periodSelectorBorderColor = selectedColor.compositeOver(MaterialTheme.colorScheme.surface),
                            timeSelectorSelectedContainerColor = selectedColor.copy(alpha = 0.5f),
                            timeSelectorSelectedContentColor = Color.White,
                            periodSelectorSelectedContainerColor = selectedColor.copy(alpha = 0.5f),
                            periodSelectorSelectedContentColor = Color.White
                        )
                    } else {
                        TimePickerDefaults.colors()
                    }
                )
            }

            TimeDisplayText(
                timeState = timeState,
                is24Hour = is24HourFormat
            )

            Spacer(modifier = Modifier.height(24.dp))

            InteractiveSwitch(
                checked = useCustomColors,
                onCheckedChange = { useCustomColors = it },
                label = "Custom Colors"
            )

            if (useCustomColors) {
                InteractiveColorPicker(
                    label = "Picker Color",
                    selectedColor = selectedColor,
                    onColorSelected = { selectedColor = it }
                )
            }

            InteractiveSwitch(
                checked = is24HourFormat,
                onCheckedChange = { newValue ->
                    val currentHour = timeState.hour
                    val currentMinute = timeState.minute
                    is24HourFormat = newValue
                    timeState = TimePickerState(
                        initialHour = currentHour,
                        initialMinute = currentMinute,
                        is24Hour = newValue
                    )
                },
                label = "24-hour Format"
            )

            InteractiveButton(
                text = "Open Time Dialog",
                onClick = { showDialogPicker = true },
                modifier = Modifier.padding(vertical = 8.dp)
            )

            if (showDialogPicker) {
                TimePickerDialog(
                    initialHour = timeState.hour,
                    initialMinute = timeState.minute,
                    onTimeSelected = { hour, minute ->
                        timeState = TimePickerState(
                            initialHour = hour,
                            initialMinute = minute,
                            is24Hour = is24HourFormat
                        )
                        showDialogPicker = false
                    },
                    onDismiss = { showDialogPicker = false },
                    is24HourFormat = is24HourFormat,
                    selectedColor = selectedColor
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimePickerDialog(
    initialHour: Int,
    initialMinute: Int,
    onTimeSelected: (hour: Int, minute: Int) -> Unit,
    onDismiss: () -> Unit,
    is24HourFormat: Boolean,
    selectedColor: Color
) {
    val dialogState = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = is24HourFormat
    )
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onTimeSelected(dialogState.hour, dialogState.minute)
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        text = {
            TimePicker(
                state = dialogState,
                colors = TimePickerDefaults.colors(
                    clockDialColor = selectedColor.copy(alpha = 0.3f),
                    selectorColor = selectedColor.copy(alpha = 0.9f),
                    containerColor = MaterialTheme.colorScheme.surface,
                    clockDialSelectedContentColor = Color.White,
                    clockDialUnselectedContentColor = Color.DarkGray,
                    periodSelectorBorderColor = selectedColor.compositeOver(MaterialTheme.colorScheme.surface),
                    timeSelectorSelectedContainerColor = selectedColor.copy(alpha = 0.5f),
                    timeSelectorSelectedContentColor = Color.White,
                    periodSelectorSelectedContainerColor = selectedColor.copy(alpha = 0.5f),
                    periodSelectorSelectedContentColor = Color.White
                )
            )
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimeDisplayText(
    timeState: TimePickerState,
    is24Hour: Boolean
) {
    val pattern = if (is24Hour) "HH:mm" else "hh:mm a"
    val formatter = remember { DateTimeFormatter.ofPattern(pattern) }
    val localTime = remember(timeState.hour, timeState.minute) {
        LocalTime.of(timeState.hour, timeState.minute)
    }

    InteractiveTextField(
        value = localTime.format(formatter),
        onValueChange = {},
        label = "Selected Time",
    )
}