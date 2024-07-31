package com.isaka.todoapp.ui.screens.task

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.isaka.todoapp.data.models.ToDoTask
import com.isaka.todoapp.ui.viewmodels.SharedViewModel
import com.isaka.todoapp.util.Action

@Composable
fun TaskScreen(
    selectedTask: ToDoTask?,
    navigateToListScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel
) {
    val context = LocalContext.current

    val title = sharedViewModel.title
    val description = sharedViewModel.description
    val priority = sharedViewModel.priority

    BackHandler { navigateToListScreen(Action.NO_ACTION) }

    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                navigateToListScreen = { action ->
                    if (action == Action.NO_ACTION || action == Action.DELETE) {
                        navigateToListScreen(action)
                    } else if (sharedViewModel.validateFields()) {
                        navigateToListScreen(action)
                    } else {
                        displayToast(context)
                    }
                }
            )
        },
        content = { padding ->
            Surface(
                modifier = Modifier.padding(padding)
            ) {
                TaskContent(
                    title = title,
                    description = description,
                    priority = priority,
                    onTitleChange = {
                        sharedViewModel.updateTitle(it)
                    },
                    onDescriptionChange = {
                        sharedViewModel.updateDescription(it)
                    },
                    onPrioritySelected = {
                        sharedViewModel.updatePriority(it)
                    }
                )
            }
        }
    )
}

private fun displayToast(context: Context) {
    Toast.makeText(
        context,
        "Fields Empty",
        Toast.LENGTH_SHORT
    ).show()
}
