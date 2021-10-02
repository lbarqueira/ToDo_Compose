package com.luisbarqueira.todocompose.navigation

import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.luisbarqueira.todocompose.ui.screens.list.ListScreen
import com.luisbarqueira.todocompose.ui.screens.task.TaskScreen
import com.luisbarqueira.todocompose.ui.viewmodels.SharedViewModel

@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
/*    val screen = remember(navController) {
        Screens(navController = navController)
    }*/

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

            Log.d("SetupNavigation", "action = $actionName")

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

        composable(
            route = "task/{taskId}", // TASK_SCREEN
            arguments = listOf(
                navArgument("taskId") {
                    // Make argument type safe
                    type = NavType.IntType
                })
        ) { entry -> // Look up "taskId" in NavBackStackEntry's arguments
            val taskId = entry.arguments!!.getInt("taskId")

            //FIXME: To eliminate this Log.d
            Log.d("SetupNavigation", "taskId = $taskId")

            sharedViewModel.getSelectedTask(taskId = taskId)

            val selectedTask by sharedViewModel.selectTask.collectAsState()

            // Everytime selectedTask changes the block of code is triggered
            LaunchedEffect(key1 = selectedTask) {
                sharedViewModel.updateTaskFields(selectedTask)
            }

            // sharedViewModel.updateTaskFields(selectedTask)

            TaskScreen(
                sharedViewModel = sharedViewModel,
                selectedTask = selectedTask,
                navigateToListScreen = { action ->
                    navController.navigate(route = "list/${action.name}") {
                        popUpTo("list/{action}") { inclusive = true }
                    }
                }
            )
        }


/*        taskComposable(
            navigateToListScreen = screen.list
        )*/

    }
}