package de.ulme.todo.ui.screens.task

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import de.ulme.todo.util.Action

@Composable
fun TaskScreen(
    taskId: Int,
    navigateToListScreen: (Action) -> Unit,
) {
    Scaffold(
        topBar = {
            TaskAppBar(
                taskId, navigateToListScreen = navigateToListScreen
            )
        }) { paddingValues ->
        print(paddingValues)
    }
}

@Composable
@Preview
fun AddTaskScreenPreview() {
    TaskScreen(-1, navigateToListScreen = {})
}

@Composable
@Preview
fun ExistingTaskScreenPreview() {
    TaskScreen(5, navigateToListScreen = {})
}