package com.droidcon.composablebank.ui.feedback_loading

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
        topBar = {
            CustomTopAppBar(
                title = name,
                navController = navController
            )
        },
        content = { paddingValues ->
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
                    Text(
                        text = "$name Examples",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    InteractiveTextField(
                        value = toastMessage,
                        onValueChange = { toastMessage = it },
                        label = "Toast Message"
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    InteractiveSwitch(
                        label = "Duration:",
                        checked = duration == Toast.LENGTH_LONG,
                        onCheckedChange = {
                            duration = if (it) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    InteractiveButton(
                        text = "Show Generic Toast",
                        onClick = {
                            val genericToast = Toast.makeText(context, toastMessage, duration)
                            genericToast.show()
                        },
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    )
}