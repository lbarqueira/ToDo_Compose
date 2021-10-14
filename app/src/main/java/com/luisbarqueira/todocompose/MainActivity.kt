package com.luisbarqueira.todocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.luisbarqueira.todocompose.navigation.SetupNavigation
import com.luisbarqueira.todocompose.ui.theme.ToDoComposeTheme
import com.luisbarqueira.todocompose.ui.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

//! We are going to have only one Activity and Zero Fragments

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController


    private val sharedViewModel by viewModels<SharedViewModel>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoComposeTheme {
                // navController = rememberNavController()
                navController = rememberAnimatedNavController()
                SetupNavigation(
                    navController = navController,
                    sharedViewModel = sharedViewModel,
                    changeAction = sharedViewModel::changeAction,
                    action = sharedViewModel.action.value
                )
            }
        }
    }
}

