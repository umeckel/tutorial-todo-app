package de.ulme.todo.ui.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import de.ulme.todo.R
import de.ulme.todo.ui.viewmodel.SharedViewModel
import de.ulme.todo.util.SearchAppBarState

@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel,
) {
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllTasks()
    }

    val allTasks by sharedViewModel.allTasks.collectAsState()
    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState

    Scaffold(topBar = {
        ListAppBar(
            sharedViewModel,
            searchAppBarState = searchAppBarState,
            searchTextState = searchTextState,
        )
    }, content = { padding ->
        ListContent(
            tasks = allTasks,
            modifier = Modifier.padding(
                top = padding.calculateTopPadding(), bottom = padding.calculateBottomPadding()
            ),
        )
    }, floatingActionButton = {
        ListFab(onFabClicked = navigateToTaskScreen)
    })
}

@Composable
fun ListFab(
    onFabClicked: (taskId: Int) -> Unit
) {
    FloatingActionButton(
        onClick = { onFabClicked(-1) },
        containerColor = MaterialTheme.colorScheme.tertiary,
        contentColor = MaterialTheme.colorScheme.onTertiary,
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(
                R.string.add_button
            ),
            tint = MaterialTheme.colorScheme.onTertiary,
        )
    }
}

@Composable
@Preview
fun ListFabPreview() {
    ListFab({})
}