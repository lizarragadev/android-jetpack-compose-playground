package com.droidcon.composablebank.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

@Composable
fun CodeSnippetView(code: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1E1E1E))
            .padding(16.dp)
    ) {
        Text(
            text = code,
            color = Color.White,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(8.dp)
        )
    }
}