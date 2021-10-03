package com.luisbarqueira.todocompose.ui.screens.task

import android.content.Context
import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.luisbarqueira.todocompose.data.models.TodoTask
import com.luisbarqueira.todocompose.ui.viewmodels.SharedViewModel
import com.luisbarqueira.todocompose.util.Action

@Composable
fun TaskScreen(
    sharedViewModel: SharedViewModel,
    selectedTask: TodoTask?,
    navigateToListScreen: (Action) -> Unit
) {
    val context = LocalContext.current


    Scaffold(
        topBar = {
            TaskAppBar(
                navigateToListScreen = { action ->
                    if (action == Action.NO_ACTION) {
                        navigateToListScreen(action)
                    } else {
                        if (sharedViewModel.validateFields()) {
                            navigateToListScreen(action)
                        } else {
                            displayToast(context = context)
                        }
                    }
                },
                selectedTask = selectedTask
            )
        }
    ) {
        TaskContent(
            title = sharedViewModel.title.value,
            onTitleChange = sharedViewModel::onTitleChange,
            description = sharedViewModel.description.value,
            onDescriptionChange = sharedViewModel::onDescriptionChange,
            priority = sharedViewModel.priority.value,
            onPrioritySelected = {
                sharedViewModel.onPrioritySelected(it)
            }
        )
    }
}

fun displayToast(context: Context) {
    Toast.makeText(
        context,
        "Fields Empty",
        Toast.LENGTH_SHORT
    ).show()
}
