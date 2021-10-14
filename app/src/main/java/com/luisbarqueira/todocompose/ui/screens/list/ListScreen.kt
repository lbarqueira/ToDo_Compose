package com.luisbarqueira.todocompose.ui.screens.list

import android.util.Log
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.luisbarqueira.todocompose.R
import com.luisbarqueira.todocompose.ui.theme.fabBackgroundColor
import com.luisbarqueira.todocompose.ui.viewmodels.SharedViewModel
import com.luisbarqueira.todocompose.util.Action
import com.luisbarqueira.todocompose.util.SearchAppBarState
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel,
    action: Action
) {

    // To call suspend functions safely from inside a composable, use the LaunchedEffect API,
    // which triggers a coroutine-scoped side-effect in Compose.
    // Instead, see rememberCoroutineScope to obtain a CoroutineScope that may be used to launch
    // ongoing jobs scoped to the composition in response to event callbacks.

    //! getAllTasks() is in the init block of sharedViewModel

    //! Problem: readSortState() is triggered whenever there is a configuration change (rotation)
    // Fixed with issue #12 - is in the init block of sharedViewModel
/*    LaunchedEffect(true) {
        Log.d("ListScreen", "LaunchedEffect triggered")
        sharedViewModel.readSortState()
    }*/

    //! collectAsState() collects values from the StateFlow and represents the latest value
    //! via Compose's State API. This will make the Compose code that reads that state value recompose on new emissions.
    //! Compose also offers APIs for Android's most popular stream-based solutions, like:
    //! LiveData.observeAsState()

    val allTasks by sharedViewModel.allTasks.collectAsState() // the type is  RequestState<List<TodoTask>>
    val sortState by sharedViewModel.sortState.collectAsState() // the type is RequestState<Priority>

    val lowPriorityTasks by sharedViewModel.lowPriorityTasks.collectAsState() // the type is List<TodoTask>
    val highPriorityTasks by sharedViewModel.highPriorityTasks.collectAsState() // the type is List<TodoTask>

    //! 1 - We are declaring a variable searchAppBarState of type SearchAppBarState
    //! 2 - sharedViewModel.searchAppBarState is of type MutableState<SearchAppBarState>
    //! which is an observable state holder. Since it's observable, it will tell compose
    //! whenever it's updated so compose can recompose any composables that read it.
    //! 3 - by is the property delegate syntax in Kotlin, it lets us automatically unwrap
    //! the State<SearchAppBarState> from the sharedViewModel.searchAppBarState into a regular SearchAppBarState
    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState

    //! Alternatively you can do as following:
    val searchTextState: String = sharedViewModel.searchTextState.value

    val searchedTasks by sharedViewModel.searchedTasks.collectAsState()


    // sharedViewModel.handleDatabaseActions(action)

    val scaffoldState: ScaffoldState = rememberScaffoldState()

    // every time the ListScreen function triggers the following function is triggered
    DisplaySnackBar(
        scaffoldState = scaffoldState,
        handleDatabaseActions = { sharedViewModel.handleDatabaseActions(action) },
        taskTitle = sharedViewModel.title.value,
        action = action,
        onUndoClicked = {
            sharedViewModel.action.value = it
        },
    )


    Scaffold(
        // to be able to display the snackbar
        scaffoldState = scaffoldState,
        topBar = {
            ListAppBar(
                sharedViewModel = sharedViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState,
                onSearchClicked = sharedViewModel::onSearchClicked,
                onTextChange = sharedViewModel::onSearchTextChanged
            )
        },
        floatingActionButton = {
            ListFab(navigateToTaskScreen = navigateToTaskScreen)
        }
    ) {
        ListContent(
            navigateToTaskScreen = navigateToTaskScreen,
            allTasks = allTasks,
            searchAppBarState = searchAppBarState,
            searchedTasks = searchedTasks,
            lowPriorityTasks = lowPriorityTasks,
            highPriorityTasks = highPriorityTasks,
            sortState = sortState
        )
    }
}

@Composable
fun ListFab(
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    FloatingActionButton(
        onClick = { navigateToTaskScreen(-1) },
        backgroundColor = MaterialTheme.colors.fabBackgroundColor
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_button),
            tint = Color.White
        )
    }
}

@Composable
fun DisplaySnackBar(
    scaffoldState: ScaffoldState,
    handleDatabaseActions: () -> Unit,
    taskTitle: String,
    action: Action,
    onUndoClicked: (Action) -> Unit
) {
    //! May be improved. For now this function is triggered any time there is a
    //! recomposition of DisplaySnackBar composable. Ideally, it should be triggered
    //! when there is a change in action by means of a LaunchedEffect with key1 = action
    handleDatabaseActions()

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = setMessage(action = action, taskTitle = taskTitle),
                    actionLabel = setActionLabel(action = action)
                )
                undoDeletedTask(
                    action = action,
                    snackBarResult = snackbarResult,
                    onUndoClicked = onUndoClicked
                )
            }
        }
    }
}

private fun setMessage(
    action: Action,
    taskTitle: String
): String {

    return when (action) {
        Action.DELETE_ALL -> "All tasks removed."
        else -> "${action.name}: $taskTitle"
    }

}

private fun setActionLabel(action: Action): String {
    return if (action.name == "DELETE") {
        "UNDO"
    } else {
        "OK"
    }
}

private fun undoDeletedTask(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed
        && action == Action.DELETE
    ) {
        onUndoClicked(Action.UNDO)
    }
}

