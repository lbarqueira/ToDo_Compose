package com.luisbarqueira.todocompose.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.luisbarqueira.todocompose.navigation.destinations.taskComposable
import com.luisbarqueira.todocompose.ui.screens.list.ListScreen
import com.luisbarqueira.todocompose.ui.viewmodels.SharedViewModel
import com.luisbarqueira.todocompose.util.Constants.LIST_ARGUMENT_KEY
import com.luisbarqueira.todocompose.util.Constants.LIST_SCREEN

@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    val screen = remember(navController) {
        Screens(navController = navController)
    }

    NavHost(
        navController = navController,
        startDestination = "list/{action}" // LIST_SCREEN
    ) {

        // definition of navigation destinations
        // routes are represented as strings.
        // A named argument is provided inside routes in curly braces like this {argument}.

        composable(
            route = "list/{action}", // LIST_SCREEN
            arguments = listOf(
                navArgument("action") {
                    // Make argument type safe
                    type = NavType.StringType
                })
        ) { entry -> // Look up "action" in NavBackStackEntry's arguments
            val actionName = entry.arguments?.getString("action")
            //! With this argument I can do what so ever ...

            ListScreen(
                navigateToTaskScreen = { taskId ->
                    navController.navigate(route = "task/$taskId")
                },
                sharedViewModel = sharedViewModel
            )
        }


/*        listComposable (
            navigateToTaskScreen = screen.task
        )*/

        taskComposable(
            navigateToListScreen = screen.list
        )

    }
}