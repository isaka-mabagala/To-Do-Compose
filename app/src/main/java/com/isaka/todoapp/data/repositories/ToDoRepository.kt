package com.isaka.todoapp.data.repositories

import com.isaka.todoapp.data.ToDoDao
import com.isaka.todoapp.data.models.ToDoTask
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ToDoRepository @Inject constructor(private val toDoDao: ToDoDao) {
    val getAllTasks: Flow<List<ToDoTask>> = toDoDao.getAllTasks()
    val sortByLowPriority: Flow<List<ToDoTask>> = toDoDao.sortByLowPriority()
    val sortByHighPriority: Flow<List<ToDoTask>> = toDoDao.sortByHighPriority()

    fun getSelectedTask(id: Int): Flow<ToDoTask> {
        return toDoDao.getSelectedTask(id = id)
    }

    fun searchTasks(query: String): Flow<List<ToDoTask>> {
        return toDoDao.searchTasks(query = query)
    }

    suspend fun addTask(task: ToDoTask) {
        toDoDao.addTask(task = task)
    }

    suspend fun updateTask(task: ToDoTask) {
        toDoDao.updateTask(task = task)
    }

    suspend fun deleteTask(task: ToDoTask) {
        toDoDao.deleteTask(task = task)
    }

    suspend fun deleteAllTasks() {
        toDoDao.deleteAllTasks()
    }
}
