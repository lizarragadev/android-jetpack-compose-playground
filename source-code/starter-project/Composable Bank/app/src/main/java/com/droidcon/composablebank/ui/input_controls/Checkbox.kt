package com.droidcon.composablebank.ui.input_controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.droidcon.composablebank.utils.CustomTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.graphics.Color
import com.droidcon.composablebank.components.InteractiveRadioButtonGroup
import com.droidcon.composablebank.components.InteractiveColorPicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Checkboxes(navController: NavController, name: String) {
    var showSnackbar by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    var checkboxType by remember { mutableStateOf("single") }
    var checkboxColor by remember { mutableStateOf(Color.Blue) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { CustomTopAppBar(title = name, navController = navController) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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
                        options = listOf("Single", "Group", "Select All"),
                        selectedOption = checkboxType,
                        onOptionSelected = { checkboxType = it.lowercase() }
                    )
                    Spacer(modifier = Modifier.height(8.dp))


                    Spacer(modifier = Modifier.height(8.dp))
                    InteractiveColorPicker(
                        label = "Checkbox Color",
                        selectedColor = checkboxColor,
                        onColorSelected = { checkboxColor = it }
                    )
                    Spacer(modifier = Modifier.height(8.dp))



                }

                Spacer(modifier = Modifier.height(24.dp))




                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    )

    if (showSnackbar) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar("$checkboxType checkbox updated")
            showSnackbar = false
        }
    }
}

@Composable
private fun SingleCheckboxExample(
    color: Color,
    enabled: Boolean
) {
    var isChecked by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(0.8f)
    ) {



        Text(
            text = if (isChecked) "Checked" else "Unchecked",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
private fun GroupCheckboxExample(
    items: List<String>,
    selectedItems: MutableList<Boolean>,
    alignment: String,
    color: Color,
    enabled: Boolean
) {
    val isHorizontal = alignment == "horizontal"

    if (isHorizontal) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEachIndexed { index, item ->



            }
        }
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {


        }
    }
}

@Composable
private fun SelectAllCheckboxExample(
    items: List<String>,
    selectedItems: MutableList<Boolean>,
    alignment: String,
    color: Color,
    enabled: Boolean
) {
    var isAllChecked by remember { mutableStateOf(false) }

    LaunchedEffect(isAllChecked) {
        selectedItems.indices.forEach { index ->
            selectedItems[index] = isAllChecked
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxWidth(0.8f)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 0.dp)
        ) {



            Text(
                text = "Select All",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(3.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp)
        ) {



        }
    }
}

@Composable
private fun SingleCheckboxWithLabel(
    label: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    color: Color,
    enabled: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {



        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}