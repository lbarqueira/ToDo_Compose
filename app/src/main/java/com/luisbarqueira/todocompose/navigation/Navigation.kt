package com.luisbarqueira.todocompose.navigation

import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.luisbarqueira.todocompose.ui.screens.list.ListScreen
import com.luisbarqueira.todocompose.ui.screens.task.TaskScreen
import com.luisbarqueira.todocompose.ui.viewmodels.SharedViewModel
import com.luisbarqueira.todocompose.util.Action
import com.luisbarqueira.todocompose.util.toAction

@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    action: Action,
    changeAction: (Action) -> Unit
) {
/*    val screen = remember(navController) {
        Screens(navController = navController)
    }*/

    var myAction by rememberSaveable { mutableStateOf(Action.NO_ACTION) }

    Log.d("SetupNavigation", "Recomposition of SetupNavigation")

    NavHost(
        navController = navController,
        startDestination = "list/{action}" // LIST_SCREEN
    ) {

        // definition of navigation destinations
        // routes are represented as strings.
        // A named argument is provided inside routes in curly braces like this {argument}.

        // FIXME: LaunchedEffect not working on configuration changes
        composable(
            route = "list/{action}", // LIST_SCREEN
            arguments = listOf(
                navArgument("action") {
                    // Make argument type safe
                    type = NavType.StringType
                })
        ) { entry -> // Look up "action" in NavBackStackEntry's arguments
            myAction = entry.arguments?.getString("action").toAction()

            Log.d("SetupNavigation", "myAction = ${myAction.name}")
            Log.d("SetupNavigation", "action = ${action.name}")


            // whenever actionName changes the block of code inside is triggered
            LaunchedEffect(key1 = myAction) {
                // sharedViewModel.action.value = action
                if (action != myAction) {
                    changeAction(myAction)
                    Log.d("SetupNavigation", "myAction_afterchange = ${action.name}")
                    myAction = action
                }
            }

            ListScreen(
                navigateToTaskScreen = { taskId ->
                    navController.navigate(route = "task/$taskId")
                },
                sharedViewModel = sharedViewModel,
                action = action,
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
            // Log.d("SetupNavigation", "taskId = $taskId")

            sharedViewModel.getSelectedTask(taskId = taskId)

            val selectedTask by sharedViewModel.selectTask.collectAsState()
            // Log.d("SetupNavigation", "selectedTask = $selectedTask")

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