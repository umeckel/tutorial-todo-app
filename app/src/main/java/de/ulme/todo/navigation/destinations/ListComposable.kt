package de.ulme.todo.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import de.ulme.todo.ui.screens.list.ListScreen
import de.ulme.todo.util.Action
import de.ulme.todo.util.Constants.LIST_ARGUMENT_KEY
import de.ulme.todo.util.Constants.LIST_SCREEN

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type = NavType.StringType
            defaultValue = Action.NO_ACTION.name
        })
    ) {
        ListScreen(navigateToTaskScreen = navigateToTaskScreen)
    }
}
