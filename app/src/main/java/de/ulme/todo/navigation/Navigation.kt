package de.ulme.todo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import de.ulme.todo.navigation.destinations.listComposable
import de.ulme.todo.navigation.destinations.taskComposable
import de.ulme.todo.ui.viewmodel.SharedViewModel
import de.ulme.todo.util.Constants.LIST_SCREEN

@Composable
fun SetupNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
) {
    val screens = remember(navController) {
        Screens(navController = navController)
    }

    NavHost(
        navController = navController,
        startDestination = LIST_SCREEN,
    ) {
        listComposable(
            navigateToTaskScreen = screens.task,
            sharedViewModel = sharedViewModel
        )
        taskComposable(
            navigateToListScreen = screens.list
        )
    }
}