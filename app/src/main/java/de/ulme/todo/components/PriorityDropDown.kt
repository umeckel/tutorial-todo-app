package de.ulme.todo.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.ulme.todo.R
import de.ulme.todo.data.models.Priority
import de.ulme.todo.ui.theme.PRIORITY_DROPDOWN_HEIGHT
import de.ulme.todo.ui.theme.PRIORITY_INDICATOR_SIZE


@Composable
fun PriorityDropDown(
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    val angle: Float by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f
    )
    Row(
        modifier = modifier
//            .safeDrawingPadding() // needed for preview on emulator
            .fillMaxWidth()
            .height(PRIORITY_DROPDOWN_HEIGHT)
            .clickable { expanded = !expanded }
            .border(
                width = 1.dp, color = MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.38f
                )
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = Modifier
                .size(PRIORITY_INDICATOR_SIZE)
                .weight(1f)
        ) {
            drawCircle(color = priority.color)
        }
        Text(
            modifier = Modifier.weight(8f),
            text = priority.name,
            style = MaterialTheme.typography.bodyMedium
        )
        IconButton(
            modifier = Modifier
                .alpha(0.5f)
                .rotate(angle)
                .weight(1.5f),
            onClick = { expanded = true },
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = stringResource(R.string.drop_down_indicator),
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
    }
    DropdownMenu(
        expanded = expanded,
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = { expanded = false },
    ) {
        DropdownMenuItem(
            onClick = {
                onPrioritySelected(Priority.LOW)
                expanded = false
            },
            text = {
                PriorityItem(priority = Priority.LOW)
            },
//            modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer)
        )
        DropdownMenuItem(
            onClick = {
                onPrioritySelected(Priority.MEDIUM)
                expanded = false
            },
            text = {
                PriorityItem(priority = Priority.MEDIUM)
            },
//            modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer),
        )
        DropdownMenuItem(
            onClick = {
                onPrioritySelected(Priority.HIGH)
                expanded = false
            },
            text = {
                PriorityItem(priority = Priority.HIGH)
            },
//            modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer),
        )
    }
}

@Composable
@Preview
fun PriorityDropDownPreview() {
    PriorityDropDown(
        priority = Priority.HIGH,
        onPrioritySelected = {},
        modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
    )
}