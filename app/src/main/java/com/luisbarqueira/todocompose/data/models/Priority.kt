package com.luisbarqueira.todocompose.data.models

import androidx.compose.ui.graphics.Color
import com.luisbarqueira.todocompose.ui.theme.HighPriorityColor
import com.luisbarqueira.todocompose.ui.theme.LowPriorityColor
import com.luisbarqueira.todocompose.ui.theme.MediumPriorityColor
import com.luisbarqueira.todocompose.ui.theme.NonePriorityColor


enum class Priority(val color: Color) {
    HIGH(color = HighPriorityColor),
    MEDIUM(color = MediumPriorityColor),
    LOW(color = LowPriorityColor),
    NONE(color = NonePriorityColor)
}
