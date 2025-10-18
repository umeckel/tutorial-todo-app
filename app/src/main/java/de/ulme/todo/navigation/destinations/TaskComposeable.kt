package de.ulme.todo.navigation.destinations

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import de.ulme.todo.ui.screens.task.TaskScreen
import de.ulme.todo.ui.viewmodel.SharedViewModel
import de.ulme.todo.util.Action
import de.ulme.todo.util.Constants.TASK_ARGUMENT_KEY
import de.ulme.todo.util.Constants.TASK_SCREEN

fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (action: Action) -> Unit,
    sharedViewModel: SharedViewModel,
) {
    composable(
        route = TASK_SCREEN, arguments = listOf(navArgument(TASK_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) { navBackStackEntry ->
        val taskId = navBackStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)
        sharedViewModel.getSelectedTask(taskId)
        val selectedTask by sharedViewModel.selectedTask.collectAsState()

        TaskScreen(selectedTask, navigateToListScreen = navigateToListScreen)
    }
}