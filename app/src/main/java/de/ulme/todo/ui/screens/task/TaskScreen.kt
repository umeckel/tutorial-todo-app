package de.ulme.todo.ui.screens.task

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.ulme.todo.data.models.Priority
import de.ulme.todo.data.models.ToDoTask
import de.ulme.todo.ui.viewmodel.SharedViewModel
import de.ulme.todo.util.Action

@Composable
fun TaskScreen(
    task: ToDoTask?,
    navigateToListScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel
) {
    val title: String = sharedViewModel.title
    val description: String = sharedViewModel.description
    val priority: Priority = sharedViewModel.priority
    Scaffold(
        topBar = {
            TaskAppBar(
                task, navigateToListScreen = navigateToListScreen
            )
        }) { padding ->
        TaskContent(
            title = title,
            onTitleChange = {
                sharedViewModel.updateTitle(it)
            },
            description = description,
            onDescriptionChanged = {
                sharedViewModel.updateDescription(it)
            },
            priority = priority,
            onPriorityChanged = {
                sharedViewModel.updatePriority(it)
            },
            modifier = Modifier.padding(
                top = padding.calculateTopPadding(), bottom = padding.calculateBottomPadding()
            ),
        )
    }
}

//@Composable
//@Preview
//fun AddTaskScreenPreview() {
//    TaskScreen(null, navigateToListScreen = {})
//}
//
//@Composable
//@Preview
//fun ExistingTaskScreenPreview() {
//    val task = ToDoTask(
//        id = 0,
//        title = "Fancy Title",
//        description = "Lorem Ipsum dolor sit amet consectetur adipiscing elit",
//        priority = Priority.LOW
//    )
//    TaskScreen(task, navigateToListScreen = {})
//}