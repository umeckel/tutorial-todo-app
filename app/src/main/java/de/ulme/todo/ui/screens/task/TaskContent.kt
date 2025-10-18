package de.ulme.todo.ui.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import de.ulme.todo.R
import de.ulme.todo.components.PriorityDropDown
import de.ulme.todo.data.models.Priority
import de.ulme.todo.ui.theme.LARGE_PADDING
import de.ulme.todo.ui.theme.MEDIUM_PADDING

@Composable
fun TaskContent(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChanged: (String) -> Unit,
    priority: Priority,
    onPriorityChanged: (Priority) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(LARGE_PADDING)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { onTitleChange(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(R.string.title)) },
            textStyle = MaterialTheme.typography.bodyMedium,
            singleLine = true,
        )
        HorizontalDivider(
            modifier = Modifier.height(MEDIUM_PADDING),
            color = MaterialTheme.colorScheme.onSecondary
        )
        PriorityDropDown(
            priority = priority,
            onPrioritySelected = onPriorityChanged
        )
        OutlinedTextField(
            value = description,
            onValueChange = { onDescriptionChanged(it) },
            modifier = Modifier.fillMaxSize(),
            label = { Text(text = stringResource(R.string.description)) },
            textStyle = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
@Preview
fun TaskContentPreview() {
    TaskContent(
        title = "The Title",
        onTitleChange = {},
        description = "The Description",
        onDescriptionChanged = {},
        priority = Priority.MEDIUM,
        onPriorityChanged = {}
    )
}
