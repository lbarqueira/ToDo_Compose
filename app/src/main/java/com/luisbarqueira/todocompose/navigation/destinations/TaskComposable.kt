package com.luisbarqueira.todocompose.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.luisbarqueira.todocompose.util.Action
import com.luisbarqueira.todocompose.util.Constants
import com.luisbarqueira.todocompose.util.Constants.TASK_SCREEN


fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit
) {
    composable(
        route = TASK_SCREEN,
        arguments = listOf(navArgument(Constants.TASK_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) {
        //TODO
    }
}