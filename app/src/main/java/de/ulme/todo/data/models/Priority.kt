package de.ulme.todo.data.models

import androidx.compose.ui.graphics.Color
import de.ulme.todo.ui.theme.HighPriorityColor
import de.ulme.todo.ui.theme.LowPriorityColor
import de.ulme.todo.ui.theme.MediumPriorityColor
import de.ulme.todo.ui.theme.NonePriorityColor

enum class Priority(val color: Color) {
    LOW(LowPriorityColor),
    MEDIUM(MediumPriorityColor),
    HIGH(HighPriorityColor),
    NONE(NonePriorityColor);
}