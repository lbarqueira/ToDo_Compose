package com.luisbarqueira.todocompose.ui.screens.list

import android.util.Log
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.luisbarqueira.todocompose.R
import com.luisbarqueira.todocompose.ui.theme.fabBackgroundColor
import com.luisbarqueira.todocompose.ui.viewmodels.SharedViewModel
import com.luisbarqueira.todocompose.util.SearchAppBarState

@ExperimentalMaterialApi
@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {

    // To call suspend functions safely from inside a composable, use the LaunchedEffect API,
    // which triggers a coroutine-scoped side-effect in Compose.
    // Instead, see rememberCoroutineScope to obtain a CoroutineScope that may be used to launch
    // ongoing jobs scoped to the composition in response to event callbacks.

    LaunchedEffect(true) {
        Log.d("ListScreen", "LaunchedEffect triggered")
        sharedViewModel.getAllTasks()
    }

    //! collectAsState() collects values from the StateFlow and represents the latest value
    //! via Compose's State API. This will make the Compose code that reads that state value recompose on new emissions.
    //! Compose also offers APIs for Android's most popular stream-based solutions, like:
    //! LiveData.observeAsState()

    val allTasks by sharedViewModel.allTasks.collectAsState()
    for (task in allTasks) {
        Log.d("ListScreen", task.title)
    }


    //! 1 - We are declaring a variable searchAppBarState of type SearchAppBarState
    //! 2 - sharedViewModel.searchAppBarState is of type MutableState<SearchAppBarState>
    //! which is an observable state holder. Since it's observable, it will tell compose
    //! whenever it's updated so compose can recompose any composables that read it.
    //! 3 - by is the property delegate syntax in Kotlin, it lets us automatically unwrap
    //! the State<SearchAppBarState> from the sharedViewModel.searchAppBarState into a regular SearchAppBarState
    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState

    //! Alternatively you can do as following:
    val searchTextState: String = sharedViewModel.searchTextState.value




    Scaffold(
        topBar = {
            ListAppBar(
                // sharedViewModel = sharedViewModel,
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
            tasks = allTasks
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


/*
@Composable
@Preview
private fun PreviewListScreen() {
    ListScreen(navigateToTaskScreen = {})
}*/
