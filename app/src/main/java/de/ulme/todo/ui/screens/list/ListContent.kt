package de.ulme.todo.ui.screens.list

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import de.ulme.todo.R
import de.ulme.todo.data.models.Priority
import de.ulme.todo.data.models.ToDoTask
import de.ulme.todo.ui.theme.LARGEST_PADDING
import de.ulme.todo.ui.theme.LARGE_PADDING
import de.ulme.todo.ui.theme.PRIORITY_INDICATOR_SIZE
import de.ulme.todo.ui.theme.SwipeDeleteColor
import de.ulme.todo.ui.theme.TASK_ITEM_ELEVATION
import de.ulme.todo.util.Action
import de.ulme.todo.util.RequestState
import de.ulme.todo.util.SearchAppBarState

@Composable
fun ListContent(
    allTasks: RequestState<List<ToDoTask>>,
    searchTasks: RequestState<List<ToDoTask>>,
    lowPriorityTasks: List<ToDoTask>,
    highPriorityTasks: List<ToDoTask>,
    sortState: RequestState<Priority>,
    searchAppBarState: SearchAppBarState,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    modifier: Modifier = Modifier,
) {
    val searchTriggered = searchAppBarState == SearchAppBarState.TRIGGERED
    val searchIsFinished = searchTasks is RequestState.Success
    if (sortState is RequestState.Success) {
        when {
            searchTriggered -> {
                if (searchIsFinished) {
                    HandleListContent(
                        tasks = searchTasks.data,
                        navigateToTaskScreen = navigateToTaskScreen,
                        onSwipeToDelete = onSwipeToDelete,
                        modifier = modifier,
                    )
                }
            }

            sortState.data == Priority.NONE -> {
                if (allTasks is RequestState.Success) {
                    HandleListContent(
                        tasks = allTasks.data,
                        navigateToTaskScreen = navigateToTaskScreen,
                        onSwipeToDelete = onSwipeToDelete,
                        modifier = modifier,
                    )
                }
            }

            sortState.data == Priority.LOW -> {
                HandleListContent(
                    tasks = lowPriorityTasks,
                    navigateToTaskScreen = navigateToTaskScreen,
                    onSwipeToDelete = onSwipeToDelete,
                    modifier = modifier,
                )
            }

            sortState.data == Priority.HIGH -> {
                HandleListContent(
                    tasks = highPriorityTasks,
                    navigateToTaskScreen = navigateToTaskScreen,
                    onSwipeToDelete = onSwipeToDelete,
                    modifier = modifier,
                )
            }
        }
    }
//    if (searchTriggered && searchIsFinished) {
//        HandleListContent(
//            tasks = searchTasks.data,
//            navigateToTaskScreen = navigateToTaskScreen,
//            modifier = modifier,
//        )
//    } else {
//        if (allTasks is RequestState.Success) {
//            HandleListContent(
//                tasks = allTasks.data,
//                navigateToTaskScreen = navigateToTaskScreen,
//                modifier = modifier,
//            )
//        }
//    }
}

@Composable
fun HandleListContent(
    tasks: List<ToDoTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (tasks.isEmpty()) {
        EmptyContent()
    } else {
        DisplayTasks(
            modifier = modifier,
            navigateToTaskScreen = navigateToTaskScreen,
            tasks = tasks,
            onSwipeToDelete = onSwipeToDelete
        )
    }
}

@Composable
fun RedBackground(degrees: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SwipeDeleteColor)
            .padding(horizontal = LARGEST_PADDING),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            modifier = Modifier.rotate(degrees = degrees),
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete_icon),
            tint = Color.White
        )
    }
}

@Composable
private fun DisplayTasks(
    tasks: List<ToDoTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    modifier: Modifier = Modifier,
) {

    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = tasks,
            key = { it.id }
        ) { task ->
            val swipeState = rememberSwipeToDismissBoxState()

            Log.println(
                Log.INFO,
                "SWIPE",
                "Swipe ${swipeState.dismissDirection}: ${swipeState.progress}"
            )
            val degrees by animateFloatAsState(
                if (swipeState.progress == 1f) 0f
                else -45f,
                label = "Degree animation"
            )
            SwipeToDismissBox(
                state = swipeState,
                modifier = Modifier.fillMaxSize(),
                enableDismissFromStartToEnd = false,
                backgroundContent = { RedBackground(degrees = degrees) },
                onDismiss = {
                    onSwipeToDelete(Action.DELETE, task)
                }
            ) {
                TaskItem(
                    toDoTask = task,
                    navigate = { navigateToTaskScreen(task.id) }
                )
            }
        }
    }
}


@Composable
fun TaskItem(
    toDoTask: ToDoTask,
    navigate: (taskId: Int) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = RectangleShape,
        shadowElevation = TASK_ITEM_ELEVATION,
        onClick = {
            navigate(toDoTask.id)
        },
    ) {
        Column(
            modifier = Modifier
                .padding(LARGE_PADDING)
                .fillMaxWidth()
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(8f),
                    text = toDoTask.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Canvas(
                        modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)
                    ) {
                        drawCircle(color = toDoTask.priority.color)
                    }
                }
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = toDoTask.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )
        }
    }
}

@Composable
@Preview
fun TaskItemPreview() {
    val todo = ToDoTask(
        id = 0, title = "Title", description = "Description", priority = Priority.HIGH
    )
    TaskItem(
        toDoTask = todo, navigate = {})
}