package com.droidcon.composablebank.ui.input_controls

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.droidcon.composablebank.components.InteractiveButton
import com.droidcon.composablebank.components.InteractiveColorPicker
import com.droidcon.composablebank.components.InteractiveRadioButtonGroup
import com.droidcon.composablebank.utils.CustomTopAppBar
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import com.droidcon.composablebank.components.InteractiveDropdown
import java.util.Locale

@Composable
internal fun DatePickerControl(navController: NavController, name: String) {
    var selectedDate by rememberSaveable(stateSaver = LocalDateSaver) {
        mutableStateOf<LocalDate?>(null)
    }
    var selectedRange by remember { mutableStateOf<Pair<LocalDate, LocalDate>?>(null) }
    var pickerStyle by remember { mutableStateOf("Default") }
    var customColor by remember { mutableStateOf(Color.Blue) }
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { CustomTopAppBar(title = name, navController = navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            InteractiveDropdown(
                options = listOf("Default", "Dialog", "Custom", "Range", "Localized"),
                selectedOption = pickerStyle,
                onOptionSelected = { pickerStyle = it },
                label = "Select picker style"
            )
            InteractiveColorPicker(
                label = "Picker Color",
                selectedColor = customColor,
                onColorSelected = { customColor = it }
            )

            if (pickerStyle != "Range" && selectedDate != null) {
                Text(
                    text = "Selected Date: ${selectedDate!!.dayOfMonth}/${selectedDate!!.monthNumber}/${selectedDate!!.year}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }

            if (pickerStyle == "Range" && selectedRange != null) {
                val (start, end) = selectedRange!!
                Text(
                    text = "Selected Range: ${start.dayOfMonth}/${start.monthNumber}/${start.year} - ${end.dayOfMonth}/${end.monthNumber}/${end.year}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }

            when (pickerStyle) {
                "Default" -> DefaultDatePicker(
                    selectedDate = selectedDate,
                    onDateSelected = { selectedDate = it },
                    customColor = customColor
                )
                "Dialog" -> DialogDatePicker(
                    showDialog = showDialog,
                    onShowDialog = { showDialog = it },
                    onDateSelected = { selectedDate = it },
                    customColor = customColor
                )
                "Custom" -> CustomDatePicker(
                    selectedDate = selectedDate,
                    onDateSelected = { selectedDate = it },
                    customColor = customColor
                )
                "Range" -> DateRangePicker(
                    selectedRange = selectedRange,
                    onRangeSelected = { selectedRange = it }
                )
                "Localized" -> LocalizedPicker(
                    onDateSelected = { selectedDate = it }
                )
            }
        }
    }
}

val LocalDateSaver = Saver<LocalDate?, String>(
    save = { date -> date?.toString() ?: "" },
    restore = { dateString ->
        if (dateString.isEmpty()) null
        else LocalDate.parse(dateString)
    }
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DefaultDatePicker(
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    customColor: Color
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate?.atStartOfDayIn(TimeZone.currentSystemDefault())?.toEpochMilliseconds()
    )

    Column {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                containerColor = customColor.copy(alpha = 0.1f),
                selectedDayContainerColor = customColor
            )
        )
        InteractiveButton(
            text = "Confirm Date",
            onClick = {
                datePickerState.selectedDateMillis?.let {
                    val instant = Instant.fromEpochMilliseconds(it)
                    val localDate = instant.toLocalDateTime(TimeZone.UTC).date
                    onDateSelected(localDate)
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DialogDatePicker(
    showDialog: Boolean,
    onShowDialog: (Boolean) -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    customColor: Color
) {
    val dialogColors = DatePickerDefaults.colors(
        containerColor = customColor.copy(alpha = 0.1f),
        selectedDayContainerColor = customColor
    )
    val state = rememberDatePickerState()

    InteractiveButton(
        text = "Open Date Dialog",
        onClick = { onShowDialog(true) }
    )

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { onShowDialog(false) },
            colors = dialogColors,
            confirmButton = {
                TextButton(
                    onClick = {
                        state.selectedDateMillis?.let {
                            val instant = Instant.fromEpochMilliseconds(it)
                            val localDate = instant.toLocalDateTime(TimeZone.UTC).date
                            onDateSelected(localDate)
                        }
                        onShowDialog(false)
                    }
                ) {
                    Text("OK", color = customColor)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onShowDialog(false) }
                ) {
                    Text("Cancel", color = MaterialTheme.colorScheme.onSurface)
                }
            }
        ) {
            DatePicker(state = state)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomDatePicker(
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    customColor: Color
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate?.atStartOfDayIn(TimeZone.currentSystemDefault())?.toEpochMilliseconds()
    )
    Column {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                containerColor = customColor.copy(alpha = 0.1f),
                selectedDayContainerColor = customColor,
                todayContentColor = customColor,
                todayDateBorderColor = customColor
            )
        )
        InteractiveButton(
            text = "Save Date",
            backgroundColor = customColor,
            onClick = {
                datePickerState.selectedDateMillis?.let {
                    val instant = Instant.fromEpochMilliseconds(it)
                    val localDate = instant.toLocalDateTime(TimeZone.UTC).date
                    onDateSelected(localDate)
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateRangePicker(
    selectedRange: Pair<LocalDate, LocalDate>?,
    onRangeSelected: (Pair<LocalDate, LocalDate>) -> Unit
) {
    val utcTimeZone = TimeZone.UTC

    val state = rememberDateRangePickerState(
        initialSelectedStartDateMillis = selectedRange?.first?.run {
            atStartOfDayIn(utcTimeZone).toEpochMilliseconds()
        },
        initialSelectedEndDateMillis = selectedRange?.second?.run {
            atStartOfDayIn(utcTimeZone).toEpochMilliseconds()
        }
    )
    LaunchedEffect(state.selectedStartDateMillis, state.selectedEndDateMillis) {
        state.selectedStartDateMillis?.let { startMillis ->
            state.selectedEndDateMillis?.let { endMillis ->
                val startDate = Instant.fromEpochMilliseconds(startMillis)
                    .toLocalDateTime(utcTimeZone).date
                val endDate = Instant.fromEpochMilliseconds(endMillis)
                    .toLocalDateTime(utcTimeZone).date
                onRangeSelected(Pair(startDate, endDate))
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        DateRangePicker(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 500.dp),
            title = {
                Text(
                    "Select Date Range",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocalizedPicker(onDateSelected: (LocalDate) -> Unit) {
    var selectedLocale by remember { mutableStateOf(Locale.getDefault()) }
    val state = rememberDatePickerState()

    Column {
        InteractiveRadioButtonGroup(
            options = listOf("English", "Español", "Français"),
            selectedOption = when(selectedLocale.language) {
                "es" -> "Español"
                "fr" -> "Français"
                else -> "English"
            },
            onOptionSelected = {
                selectedLocale = when(it) {
                    "Español" -> Locale("es", "ES")
                    "Français" -> Locale("fr", "FR")
                    else -> Locale.ENGLISH
                }
            }
        )

        CompositionLocalProvider(
            LocalConfiguration provides Configuration(LocalConfiguration.current).apply {
                setLocale(selectedLocale)
            }
        ) {
            DatePicker(
                state = state,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        LaunchedEffect(state.selectedDateMillis) {
            state.selectedDateMillis?.let {
                val instant = Instant.fromEpochMilliseconds(it)
                val localDate = instant.toLocalDateTime(TimeZone.UTC).date
                onDateSelected(localDate)
            }
        }
    }
}
