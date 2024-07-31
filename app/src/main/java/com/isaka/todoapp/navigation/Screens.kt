package com.isaka.todoapp.navigation

import androidx.navigation.NavHostController
import com.isaka.todoapp.util.Action

class Screens(navController: NavHostController) {
    val list: (Action) -> Unit = { action ->
        navController.navigate("list/${action.name}") {
            popUpTo(LIST_SCREEN) { inclusive = true }
        }
    }

    val task: (Int) -> Unit = { id ->
        navController.navigate("task/$id")
    }

    companion object {
        const val LIST_ARGUMENT_KEY = "action"
        const val LIST_SCREEN = "list/{$LIST_ARGUMENT_KEY}"
        const val TASK_ARGUMENT_KEY = "id"
        const val TASK_SCREEN = "task/{$TASK_ARGUMENT_KEY}"
    }
}
