package com.isaka.todoapp.ui.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.isaka.todoapp.data.models.Priority
import com.isaka.todoapp.ui.components.PriorityDropDown
import com.isaka.todoapp.ui.theme.LARGE_PADDING

@Composable
fun TaskContent(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(all = LARGE_PADDING)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { onTitleChange(it) },
            label = { Text(text = "Title") },
            textStyle = MaterialTheme.typography.bodyMedium,
            singleLine = true
        )
        Spacer(modifier = Modifier.height(LARGE_PADDING))
        PriorityDropDown(
            priority = priority,
            onPrioritySelected = onPrioritySelected
        )
        Spacer(modifier = Modifier.height(LARGE_PADDING))
        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = description,
            onValueChange = { onDescriptionChange(it) },
            label = { Text(text = "Description") },
            textStyle = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
@Preview
fun TaskContentPreview() {
    TaskContent(
        title = "Testing",
        description = "Woo nice with compose",
        priority = Priority.MEDIUM,
        onTitleChange = {},
        onDescriptionChange = {},
        onPrioritySelected = {}
    )
}
