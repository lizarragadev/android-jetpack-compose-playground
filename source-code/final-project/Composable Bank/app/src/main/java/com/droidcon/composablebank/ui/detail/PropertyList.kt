package com.droidcon.composablebank.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

@Composable
fun PropertyList(properties: List<String>) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        properties.forEachIndexed { index, property ->
            Text(
                text = property,
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)

            )
            if (index < properties.size - 1) {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outlineVariant,
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}