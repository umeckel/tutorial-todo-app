package de.ulme.todo.ui.screens.task

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TaskScreen() {
    Scaffold(
        topBar = {
            TaskAppBar(
                navigateToListScreen = {}
            )
        }) { paddingValues ->
        print(paddingValues)
    }
}

@Composable
@Preview
fun TaskScreenPreview() {
    TaskScreen()
}