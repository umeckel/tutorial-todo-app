package de.ulme.todo.ui.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.ulme.todo.R
import de.ulme.todo.components.PriorityItem
import de.ulme.todo.data.models.Priority
import de.ulme.todo.ui.theme.LARGE_PADDING
import de.ulme.todo.ui.theme.SEARCH_BAR_HEIGHT
import de.ulme.todo.ui.theme.Typography
import de.ulme.todo.ui.viewmodel.SharedViewModel
import de.ulme.todo.util.SearchAppBarState

@Composable
fun ListAppBar(
    sharedViewModel: SharedViewModel,
    searchAppBarState: SearchAppBarState,
    searchTextState: String,
) {
    when (searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultListAppBar(onSearchClicked = {
                sharedViewModel.searchAppBarState.value = SearchAppBarState.OPENED
            }, onSortClicked = {}, onDeleteClicked = {})
        }

        else -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = { searchString ->
                    sharedViewModel.searchTextState.value = searchString
                },
                onCloseClicked = {
                    sharedViewModel.searchAppBarState.value = SearchAppBarState.CLOSED
                    sharedViewModel.searchTextState.value = ""
                },
                onSearchClicked = {},
            )
        }
    }
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
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        actions = {
            ListBarActions(
                onSearchClicked = onSearchClicked,
                onSortClicked = onSortClicked,
                onDeleteClicked = onDeleteClicked
            )
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .statusBarsPadding()
            .fillMaxWidth()
            .height(SEARCH_BAR_HEIGHT),
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                focusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
            value = text,
            onValueChange = { onTextChange(it) },
            placeholder = {
                Text(
                    modifier = Modifier.alpha(0.5f),
                    text = stringResource(R.string.search_placeholder),
                )
            },
            textStyle = TextStyle(
                fontSize = Typography.bodyMedium.fontSize,
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(R.string.search_icon),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    modifier = Modifier.alpha(0.5f),
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(R.string.close_icon),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }),
        )

    }
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
private fun SearchAppBarPreview() {
    SearchAppBar(text = "", onSearchClicked = {}, onTextChange = {}, onCloseClicked = {})
}

@Composable
@Preview
private fun DefaultListAppBarPreview() {
    DefaultListAppBar(onSearchClicked = {}, onSortClicked = {}, onDeleteClicked = {})
}