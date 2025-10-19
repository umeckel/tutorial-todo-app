package de.ulme.todo.ui.screens.task

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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

    val context = LocalContext.current
    Scaffold(
        topBar = {
            TaskAppBar(
                task, navigateToListScreen = { action ->
                    if (action == Action.NO_ACTION) {
                        navigateToListScreen(action)
                    } else {
                        if (sharedViewModel.validateFields()) {
                            navigateToListScreen(action)
                        } else {
                            displayToast(context = context)
                        }
                    }
                }
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


fun displayToast(context: Context) {
    Toast.makeText(
        context,
        "Required Field is Empty",
        Toast.LENGTH_SHORT
    ).show()

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