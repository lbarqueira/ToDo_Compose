package com.luisbarqueira.todocompose.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisbarqueira.todocompose.data.models.TodoTask
import com.luisbarqueira.todocompose.data.repositories.TodoRepository
import com.luisbarqueira.todocompose.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val repository: TodoRepository) :
    ViewModel() {

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


    private val _allTasks = MutableStateFlow<List<TodoTask>>(emptyList())
    val allTasks: StateFlow<List<TodoTask>> = _allTasks

    fun getAllTasks() {
        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            repository.getAllTasks.collect {
                _allTasks.value = it
            }
        }
    }
}