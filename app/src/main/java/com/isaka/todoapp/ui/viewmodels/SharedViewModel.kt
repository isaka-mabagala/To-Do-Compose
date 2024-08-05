package com.isaka.todoapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isaka.todoapp.data.SettingsStore
import com.isaka.todoapp.data.models.Priority
import com.isaka.todoapp.data.models.ToDoTask
import com.isaka.todoapp.data.repositories.ToDoRepository
import com.isaka.todoapp.util.Action
import com.isaka.todoapp.util.Constants.MAX_TITLE_LENGTH
import com.isaka.todoapp.util.RequestState
import com.isaka.todoapp.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: ToDoRepository,
    private val appSettings: SettingsStore
) : ViewModel() {
    var isDarkTheme by mutableStateOf(false)
        private set
    var action by mutableStateOf(Action.NO_ACTION)
        private set
    var id by mutableIntStateOf(0)
        private set
    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set
    var priority by mutableStateOf(Priority.LOW)
        private set
    var searchAppBarState by mutableStateOf(SearchAppBarState.CLOSED)
        private set
    var searchTextState by mutableStateOf("")
        private set

    private val _selectedTask = MutableStateFlow<ToDoTask?>(null)
    val selectedTask: StateFlow<ToDoTask?> = _selectedTask

    private val _searchedTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val searchedTasks: StateFlow<RequestState<List<ToDoTask>>> = _searchedTasks

    private val _allTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val allTasks: StateFlow<RequestState<List<ToDoTask>>> = _allTasks

    init {
        getAllTasks()
    }

    fun getAllTasks(priority: Priority = Priority.NONE) {
        _allTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                when (priority) {
                    Priority.LOW -> {
                        repository.sortByLowPriority.collect {
                            _allTasks.value = RequestState.Success(it)
                        }
                    }

                    Priority.HIGH -> {
                        repository.sortByHighPriority.collect {
                            _allTasks.value = RequestState.Success(it)
                        }
                    }

                    else -> {
                        repository.getAllTasks.collect {
                            _allTasks.value = RequestState.Success(it)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            _allTasks.value = RequestState.Error(e)
        }
    }

    fun searchDatabase(query: String) {
        _searchedTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.searchTasks(query = "%$query%").collect {
                    _searchedTasks.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _searchedTasks.value = RequestState.Error(e)
        }
        updateAppBarState(state = SearchAppBarState.TRIGGERED)
    }

    fun getSelectedTask(id: Int) {
        viewModelScope.launch {
            repository.getSelectedTask(id = id).collect { task ->
                _selectedTask.value = task
            }
        }
    }

    private fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val task = ToDoTask(
                title = title,
                description = description,
                priority = priority
            )
            repository.addTask(task = task)
        }
        updateAppBarState(state = SearchAppBarState.CLOSED)
    }

    private fun updateTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val task = ToDoTask(
                id = id,
                title = title,
                description = description,
                priority = priority
            )
            repository.updateTask(task = task)
        }
    }

    private fun deleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val task = ToDoTask(
                id = id,
                title = title,
                description = description,
                priority = priority
            )
            repository.deleteTask(task = task)
        }
    }

    private fun deleteAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTasks()
        }
    }

    fun updateTaskFields(task: ToDoTask?) {
        if (task != null) {
            id = task.id
            title = task.title
            description = task.description
            priority = task.priority
        } else {
            id = 0
            title = ""
            description = ""
            priority = Priority.LOW
        }
    }

    fun handleDatabaseActions(action: Action) {
        when (action) {
            Action.ADD -> {
                addTask()
            }

            Action.UPDATE -> {
                updateTask()
            }

            Action.DELETE -> {
                deleteTask()
            }

            Action.DELETE_ALL -> {
                deleteAllTasks()
            }

            Action.UNDO -> {
                addTask()
            }

            else -> {}
        }
    }

    fun changeTheme() {
        isDarkTheme = !isDarkTheme
        viewModelScope.launch(Dispatchers.IO) {
            appSettings.setDarkMode(isDarkTheme)
        }
    }

    fun getThemeMode() {
        viewModelScope.launch(Dispatchers.IO) {
            appSettings.isDarkModeTheme.collect { status ->
                isDarkTheme = status
            }
        }
    }

    fun updateAppBarState(state: SearchAppBarState) {
        searchAppBarState = state
    }

    fun updateSearchTextState(text: String) {
        searchTextState = text
    }

    fun updateAction(newAction: Action) {
        action = newAction
    }

    fun updateTitle(newTitle: String) {
        if (newTitle.length <= MAX_TITLE_LENGTH) {
            title = newTitle
        }
    }

    fun validateFields(): Boolean {
        return title.isNotEmpty() && description.isNotEmpty()
    }

    fun updateDescription(newDescription: String) {
        description = newDescription
    }

    fun updatePriority(newPriority: Priority) {
        priority = newPriority
    }
}
