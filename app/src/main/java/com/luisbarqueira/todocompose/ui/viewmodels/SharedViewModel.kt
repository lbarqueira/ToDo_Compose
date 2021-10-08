package com.luisbarqueira.todocompose.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisbarqueira.todocompose.data.models.Priority
import com.luisbarqueira.todocompose.data.models.TodoTask
import com.luisbarqueira.todocompose.data.repositories.TodoRepository
import com.luisbarqueira.todocompose.util.Action
import com.luisbarqueira.todocompose.util.RequestState
import com.luisbarqueira.todocompose.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val repository: TodoRepository) :
    ViewModel() {

    val action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)

    fun changeAction(newAction: Action) {
        action.value = newAction
    }

    private val _allTasks =
        MutableStateFlow<RequestState<List<TodoTask>>>(RequestState.Idle)
    val allTasks: StateFlow<RequestState<List<TodoTask>>> = _allTasks

    init {
        getAllTasks()
        Log.d("SharedViewModel", "Init block")
    }

    // state: searchAppBarState
    var searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)
        private set //! I do not understand !!!!!

    // state: searchTextState
    var searchTextState: MutableState<String> = mutableStateOf("")
        private set

    // onSearchClicked is an event we're defining that the UI can invoke
    // (events flow up from UI)
    // event: onSearchClicked
    fun onSearchClicked(newSearchAppBarState: SearchAppBarState) {
        searchAppBarState.value = newSearchAppBarState
    }

    // event: onSearchTextChanged
    fun onSearchTextChanged(newText: String) {
        searchTextState.value = newText
    }


    // states
    val id: MutableState<Int> = mutableStateOf(0)

    val title: MutableState<String> = mutableStateOf("")

    val description: MutableState<String> = mutableStateOf("")

    val priority: MutableState<Priority> = mutableStateOf((Priority.LOW))

    // events:

    fun onTitleChange(newTitle: String) {
        // limit the character count
        if (newTitle.length < 20) {
            title.value = newTitle
        }
    }

    fun onDescriptionChange(newDescription: String) {
        description.value = newDescription
    }

    fun onPrioritySelected(newPriority: Priority) {
        priority.value = newPriority
    }


    fun updateTaskFields(selectedTask: TodoTask?) {
        if (selectedTask != null) {
            id.value = selectedTask.id
            title.value = selectedTask.title
            description.value = selectedTask.description
            priority.value = selectedTask.priority
        } else {
            id.value = 0
            title.value = ""
            description.value = ""
            priority.value = Priority.LOW
        }
    }

    fun validateFields(): Boolean {
        return title.value.isNotEmpty() && description.value.isNotEmpty()
    }


    private fun getAllTasks() {
        _allTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                // Trigger the flow and consume its elements using collect
                repository.getAllTasks.collect {
                    _allTasks.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _allTasks.value = RequestState.Error(e)

        }
    }


    private val _selectedTask: MutableStateFlow<TodoTask?> = MutableStateFlow(null)
    val selectTask: StateFlow<TodoTask?> = _selectedTask

    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch {
            repository.getSelectedTask(taskId = taskId).collect {
                _selectedTask.value = it
            }
        }
    }

    private fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val todoTask = TodoTask(
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repository.addTask(todoTask = todoTask)
        }
    }

    private fun updateTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val todoTask = TodoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repository.updateTask(todoTask = todoTask)
        }
    }

    private fun deleteTask(){
        viewModelScope.launch(Dispatchers.IO) {
            val todoTask = TodoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repository.deleteTask(todoTask = todoTask)
        }
    }



    fun handleDatabaseActions(action: Action) {
        when (action) {
            Action.ADD -> addTask()
            Action.UPDATE -> updateTask()
            Action.DELETE -> deleteTask()
            Action.DELETE_ALL -> {
            }
            Action.UNDO -> {
            }
            else -> {
            }
        }
        this.action.value = Action.NO_ACTION
    }

}