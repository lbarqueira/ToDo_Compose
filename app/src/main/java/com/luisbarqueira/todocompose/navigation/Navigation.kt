package com.luisbarqueira.todocompose.navigation
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import com.google.accompanist.navigation.animation.composable
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.luisbarqueira.todocompose.ui.screens.list.ListScreen
import com.luisbarqueira.todocompose.ui.screens.task.TaskScreen
import com.luisbarqueira.todocompose.ui.splash.SplashScreen
import com.luisbarqueira.todocompose.ui.viewmodels.SharedViewModel
import com.luisbarqueira.todocompose.util.Action
import com.luisbarqueira.todocompose.util.toAction

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    changeAction: (Action) -> Unit,
    action: Action
) {
    Log.d("SetupNavigation", "Recomposition of SetupNavigation")

    AnimatedNavHost(
        navController = navController,
        startDestination = "splash" // SPLASH_SCREEN
    ) {

        // definition of navigation destinations
        // routes are represented as strings.
        // A named argument is provided inside routes in curly braces like this {argument}.

        composable(
            route = "splash", // SPLASH_SCREEN
            exitTransition = {
                when (targetState.destination.route) {
                    "list/{action}" ->
                        slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(300))
                    else -> null
                }
            },
        ) {
            SplashScreen(
                navigateToListScreen = {
                    navController.navigate(route = "list/${Action.NO_ACTION}") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = "list/{action}", // LIST_SCREEN
            arguments = listOf(
                navArgument("action") {
                    // Make argument type safe
                    type = NavType.StringType
                })
        ) { entry -> // Look up "action" in NavBackStackEntry's arguments

            val actionArg = entry.arguments?.getString("action").toAction()

            var myAction by rememberSaveable { mutableStateOf(Action.NO_ACTION) }

            // whenever myAction changes the block of code inside is triggered
            LaunchedEffect(key1 = myAction) {
                if (actionArg != myAction) {
                    myAction = actionArg
                    changeAction(myAction)  // sharedViewModel.action.value = myAction
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

        composable(
            route = "task/{taskId}", // TASK_SCREEN
            arguments = listOf(
                navArgument("taskId") {
                    // Make argument type safe
                    type = NavType.IntType
                }),
            enterTransition = {
                slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(300))
            }

        ) { entry -> // Look up "taskId" in NavBackStackEntry's arguments

            val taskId = entry.arguments!!.getInt("taskId")

            LaunchedEffect(key1 = taskId, block = {
                sharedViewModel.getSelectedTask(taskId = taskId)
            })

            val selectedTask by sharedViewModel.selectTask.collectAsState()
            // Log.d("SetupNavigation", "selectedTask = $selectedTask")

            // Everytime selectedTask changes the block of code is triggered
            LaunchedEffect(key1 = selectedTask) {
                if (selectedTask != null || taskId == -1) {
                    sharedViewModel.updateTaskFields(selectedTask)
                }
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
    }
}