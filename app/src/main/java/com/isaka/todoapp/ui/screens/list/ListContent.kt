package com.isaka.todoapp.ui.screens.list

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Icon
import com.isaka.todoapp.data.models.Priority
import com.isaka.todoapp.data.models.ToDoTask
import com.isaka.todoapp.ui.theme.LARGE_PADDING
import com.isaka.todoapp.ui.theme.PRIORITY_INDICATOR_SIZE
import com.isaka.todoapp.util.Action
import com.isaka.todoapp.util.RequestState
import com.isaka.todoapp.util.SearchAppBarState

@Composable
fun ListContent(
    tasks: RequestState<List<ToDoTask>>,
    searchedTasks: RequestState<List<ToDoTask>>,
    searchAppBarState: SearchAppBarState,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    if (searchAppBarState == SearchAppBarState.TRIGGERED) {
        if (searchedTasks is RequestState.Success) {
            // fix not showing empty content on start with RequestState
            // while fetching data from database
            HandleListContent(
                tasks = searchedTasks.data,
                onSwipeToDelete = onSwipeToDelete,
                navigateToTaskScreen = navigateToTaskScreen
            )
        }
    } else {
        if (tasks is RequestState.Success) {
            // fix not showing empty content on start with RequestState
            // while fetching data from database
            HandleListContent(
                tasks = tasks.data,
                onSwipeToDelete = onSwipeToDelete,
                navigateToTaskScreen = navigateToTaskScreen
            )
        }
    }
}

@Composable
fun HandleListContent(
    tasks: List<ToDoTask>,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    if (tasks.isEmpty()) {
        EmptyContent()
    } else {
        DisplayTasks(
            tasks = tasks,
            onSwipeToDelete = onSwipeToDelete,
            navigateToTaskScreen = navigateToTaskScreen
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayTasks(
    tasks: List<ToDoTask>,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    LazyColumn {
        items(
            items = tasks,
            key = { task ->
                task.id
            }
        ) { task ->
            val dismissState = rememberSwipeToDismissBoxState(
                positionalThreshold = { it * .35f },
                confirmValueChange = {
                    if (it == SwipeToDismissBoxValue.EndToStart) {
                        onSwipeToDelete(Action.DELETE, task)
                    }
                    true
                }
            )
            val degrees by animateFloatAsState(
                if (dismissState.targetValue != SwipeToDismissBoxValue.EndToStart) 0f else -45f,
                label = "SwipeToDismissDegreesAnimation"
            )

            SwipeToDismiss(
                state = dismissState,
                directions = setOf(SwipeToDismissBoxValue.EndToStart),
                background = { RedBackground(degrees = degrees) },
                dismissContent = {
                    TaskItem(
                        task = task,
                        navigateToTaskScreen = navigateToTaskScreen
                    )
                }
            )
        }
    }
}

@Composable
fun RedBackground(degrees: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Red)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            modifier = Modifier.rotate(degrees = degrees),
            imageVector = Icons.Filled.Delete,
            tint = Color.White,
            contentDescription = null
        )
    }
}

@Composable
fun TaskItem(
    task: ToDoTask,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RectangleShape,
        onClick = {
            navigateToTaskScreen(task.id)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(all = LARGE_PADDING)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(8f),
                    text = task.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Canvas(
                        modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)
                    ) {
                        drawCircle(
                            color = task.priority.color
                        )
                    }
                }
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = task.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
@Preview
fun TaskItemPreview() {
    TaskItem(
        task = ToDoTask(
            title = "Test",
            description = "Testing description",
            priority = Priority.LOW
        ),
        navigateToTaskScreen = {}
    )
}
