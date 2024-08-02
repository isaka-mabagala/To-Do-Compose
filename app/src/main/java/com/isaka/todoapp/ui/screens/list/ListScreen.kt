package com.isaka.todoapp.ui.screens.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.isaka.todoapp.ui.viewmodels.SharedViewModel
import com.isaka.todoapp.util.Action
import kotlinx.coroutines.time.delay
import java.time.Duration

@Composable
fun ListScreen(
    action: Action,
    navigateToTaskScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    val searchedTasks by sharedViewModel.searchedTasks.collectAsState()
    val allTasks by sharedViewModel.allTasks.collectAsState()
    val searchAppBarState = sharedViewModel.searchAppBarState
    val searchTextState = sharedViewModel.searchTextState
    val snackBarHost = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        // the methods moved to shared model init to avoid
        // recall the methods when rotate the device screen
        //sharedViewModel.getAllTasks()
    }

    LaunchedEffect(key1 = action) {
        sharedViewModel.handleDatabaseActions(action)
    }

    DisplaySnackBar(
        snackBarHost = snackBarHost,
        taskTitle = sharedViewModel.title,
        action = action,
        onComplete = { sharedViewModel.updateAction(it) },
        onUndoClicked = { sharedViewModel.updateAction(it) }
    )

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHost) },
        topBar = {
            ListAppBar(
                sharedViewModel = sharedViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState
            )
        },
        content = { padding ->
            Surface(
                modifier = Modifier.padding(padding),
            ) {
                ListContent(
                    tasks = allTasks,
                    searchedTasks = searchedTasks,
                    searchAppBarState = searchAppBarState,
                    navigateToTaskScreen = navigateToTaskScreen,
                    onSwipeToDelete = { action, task ->
                        sharedViewModel.updateAction(newAction = action)
                        sharedViewModel.updateTaskFields(task = task)
                        snackBarHost.currentSnackbarData?.dismiss()
                    }
                )
            }
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTaskScreen)
        }
    )
}

@Composable
fun DisplaySnackBar(
    snackBarHost: SnackbarHostState,
    taskTitle: String,
    action: Action,
    onComplete: (Action) -> Unit,
    onUndoClicked: (Action) -> Unit
) {
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            val snackBarResult = snackBarHost.showSnackbar(
                message = setMessage(action, taskTitle),
                actionLabel = setActionLabel(action)
            )
            if (snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE) {
                onUndoClicked(Action.UNDO)
            } else if (snackBarResult == SnackbarResult.Dismissed || action != Action.DELETE) {
                onComplete(Action.NO_ACTION)
            }
            delay(Duration.ofSeconds(3))
            snackBarHost.currentSnackbarData?.dismiss()
        }
    }

    // dismiss snack bar after 3 seconds
    LaunchedEffect(key1 = action) {
        delay(Duration.ofSeconds(3))
        snackBarHost.currentSnackbarData?.dismiss()
    }
}

@Composable
fun ListFab(onFabClicked: (Int) -> Unit) {
    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        onClick = {
            onFabClicked(-1)
        }
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = null
        )
    }
}

private fun setMessage(action: Action, taskTitle: String): String {
    return when (action) {
        Action.DELETE_ALL -> "All Tasks Removed."
        else -> "${action.name}: $taskTitle"
    }
}

private fun setActionLabel(action: Action): String {
    return if (action == Action.DELETE) {
        "UNDO"
    } else {
        "OK"
    }
}

@Composable
@Preview
fun ListFabPreview() {
    ListFab(onFabClicked = {})
}
