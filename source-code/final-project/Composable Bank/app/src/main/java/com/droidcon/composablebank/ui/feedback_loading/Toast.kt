package com.droidcon.composablebank.ui.feedback_loading

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.droidcon.composablebank.components.InteractiveButton
import com.droidcon.composablebank.components.InteractiveTextField
import com.droidcon.composablebank.components.InteractiveSwitch
import com.droidcon.composablebank.utils.CustomTopAppBar

@Composable
fun Toast(navController: NavController, name: String) {
    var toastMessage by remember { mutableStateOf("Default Toast Message") }
    var duration by remember { mutableIntStateOf(Toast.LENGTH_SHORT) }
    val context = LocalContext.current

    Scaffold(
        topBar = { CustomTopAppBar(title = name, navController = navController) },
        content = { paddingValues ->
            ToastContent(
                paddingValues = paddingValues,
                name = name,
                toastMessage = toastMessage,
                duration = duration,
                onMessageChange = { toastMessage = it },
                onDurationChange = { isLong ->
                    duration = if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
                },
                onShowToast = { showAndroidToast(context, toastMessage, duration) }
            )
        }
    )
}

@Composable
private fun ToastContent(
    paddingValues: PaddingValues,
    name: String,
    toastMessage: String,
    duration: Int,
    onMessageChange: (String) -> Unit,
    onDurationChange: (Boolean) -> Unit,
    onShowToast: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ToastTitle(name = name)
            ToastConfiguration(
                toastMessage = toastMessage,
                duration = duration,
                onMessageChange = onMessageChange,
                onDurationChange = onDurationChange
            )
            ShowToastButton(onClick = onShowToast)
        }
    }
}

@Composable
private fun ToastTitle(name: String) {
    Text(
        text = "$name Examples",
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@Composable
private fun ToastConfiguration(
    toastMessage: String,
    duration: Int,
    onMessageChange: (String) -> Unit,
    onDurationChange: (Boolean) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        InteractiveTextField(
            value = toastMessage,
            onValueChange = onMessageChange,
            label = "Toast Message"
        )
        Spacer(modifier = Modifier.height(8.dp))

        DurationSelector(
            isLongDuration = duration == Toast.LENGTH_LONG,
            onDurationChange = onDurationChange
        )
    }
}

@Composable
private fun DurationSelector(
    isLongDuration: Boolean,
    onDurationChange: (Boolean) -> Unit
) {
    InteractiveSwitch(
        label = "Duration:",
        checked = isLongDuration,
        onCheckedChange = onDurationChange
    )
}

@Composable
private fun ShowToastButton(onClick: () -> Unit) {
    InteractiveButton(
        text = "Show Generic Toast",
        onClick = onClick,
        backgroundColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier.fillMaxWidth()
    )
}

private fun showAndroidToast(context: Context, message: String, duration: Int) {
    Toast.makeText(context, message, duration).show()
}