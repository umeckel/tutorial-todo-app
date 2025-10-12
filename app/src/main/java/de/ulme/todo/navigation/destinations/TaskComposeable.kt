package de.ulme.todo.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import de.ulme.todo.util.Action
import de.ulme.todo.util.Constants.TASK_ARGUMENT_KEY
import de.ulme.todo.util.Constants.TASK_SCREEN

fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (action: Action) -> Unit
) {
    composable(
        route = TASK_SCREEN, arguments = listOf(navArgument(TASK_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) {

    }
}