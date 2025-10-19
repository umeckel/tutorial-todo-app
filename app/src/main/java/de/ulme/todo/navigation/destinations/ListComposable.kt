package de.ulme.todo.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import de.ulme.todo.ui.screens.list.ListScreen
import de.ulme.todo.ui.viewmodel.SharedViewModel
import de.ulme.todo.util.Action
import de.ulme.todo.util.Constants.LIST_ARGUMENT_KEY
import de.ulme.todo.util.Constants.LIST_SCREEN
import de.ulme.todo.util.toAction

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel,
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type = NavType.StringType
            defaultValue = Action.NO_ACTION.name
        })
    ) { navBackStackEntry ->
        val action = navBackStackEntry.arguments?.getString(LIST_ARGUMENT_KEY).toAction()
        LaunchedEffect(key1 = action) {
            sharedViewModel.updateAction(action)
        }
        ListScreen(
            navigateToTaskScreen = navigateToTaskScreen,
            sharedViewModel = sharedViewModel
        )
    }
}
