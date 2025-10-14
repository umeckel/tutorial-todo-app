package de.ulme.todo.ui.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddReaction
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.ulme.todo.R


@Composable
fun EmptyContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(120.dp),
            imageVector = Icons.Filled.AddReaction,
            contentDescription = stringResource(R.string.no_tasks),
            tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
        )
        Text(text = stringResource(R.string.all_done),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
        fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.titleSmall.fontSize)
    }
}

@Composable
@Preview
fun EmptyContentPreview() {
    EmptyContent()
}