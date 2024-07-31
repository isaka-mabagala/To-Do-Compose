package com.isaka.todoapp.data.models

import androidx.compose.ui.graphics.Color
import com.isaka.todoapp.ui.theme.HighPriorityColor
import com.isaka.todoapp.ui.theme.LowPriorityColor
import com.isaka.todoapp.ui.theme.MediumPriorityColor
import com.isaka.todoapp.ui.theme.NonePriorityColor

enum class Priority(val color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor),
}
