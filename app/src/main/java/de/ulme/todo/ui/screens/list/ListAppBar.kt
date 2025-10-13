package de.ulme.todo.ui.screens.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import de.ulme.todo.R
import de.ulme.todo.components.PriorityItem
import de.ulme.todo.data.models.Priority
import de.ulme.todo.ui.theme.LARGE_PADDING
import de.ulme.todo.ui.theme.Typography

@Composable
fun ListAppBar() {
    DefaultListAppBar(onSearchClicked = {}, onSortClicked = {}, onDeleteClicked = {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultListAppBar(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteClicked: () -> Unit,
) {
    TopAppBar(
        title = { Text(stringResource(R.string.default_top_title)) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        actions = {
            ListBarActions(
                onSearchClicked = onSearchClicked,
                onSortClicked = onSortClicked,
                onDeleteClicked = onDeleteClicked
            )
        })
}

@Composable
fun ListBarActions(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteClicked: () -> Unit,
) {
    SearchAction(onSearchClicked = onSearchClicked)
    SortAction(onSortClicked = onSortClicked)
    DeleteAction(onDeleteClicked = onDeleteClicked)
}

@Composable
fun SearchAction(
    onSearchClicked: () -> Unit,
) {
    IconButton(
        onClick = { onSearchClicked() },
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(
                R.string.actions_search
            ),
            tint = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
fun SortAction(
    onSortClicked: (Priority) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val priorities = listOf(Priority.LOW, Priority.HIGH, Priority.NONE)

    IconButton(
        onClick = { expanded = !expanded },
    ) {
        Icon(
            imageVector = Icons.Filled.FilterList,
            contentDescription = stringResource(
                R.string.actions_sort
            ),
            tint = MaterialTheme.colorScheme.primary,
        )
        DropdownMenu(
            expanded = expanded, onDismissRequest = { expanded = false }) {

            priorities.forEach { priority ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    onSortClicked(priority)
                }, text = {
                    PriorityItem(priority = priority)
                })
            }
        }
    }
}

@Composable
fun DeleteAction(
    onDeleteClicked: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }


    IconButton(
        onClick = { expanded = !expanded },
    ) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(
                R.string.actions_delete
            ),
            tint = MaterialTheme.colorScheme.primary,
        )
        DropdownMenu(
            expanded = expanded, onDismissRequest = { expanded = false }) {


            DropdownMenuItem(onClick = {
                expanded = false
                onDeleteClicked()
            }, text = {
                Text(
                    modifier = Modifier.padding(start = LARGE_PADDING),
                    text = stringResource(R.string.delete_all),
                    style = Typography.titleSmall,
                )
            })

        }
    }
}

@Composable
@Preview
private fun DefaultListAppBarPreview() {
    DefaultListAppBar(onSearchClicked = {}, onSortClicked = {}, onDeleteClicked = {})
}