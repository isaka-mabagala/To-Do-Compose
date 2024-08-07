package com.isaka.todoapp.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.isaka.todoapp.data.models.Priority
import com.isaka.todoapp.ui.theme.LARGE_PADDING
import com.isaka.todoapp.ui.theme.PRIORITY_INDICATOR_SIZE

@Composable
fun PriorityItem(priority: Priority) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)
        ) {
            drawCircle(color = priority.color)
        }
        Text(
            modifier = Modifier.padding(start = LARGE_PADDING),
            text = priority.name,
            style = MaterialTheme.typography.caption1
        )
    }
}

@Composable
@Preview
fun PriorityItemPreview() {
    PriorityItem(priority = Priority.LOW)
}
