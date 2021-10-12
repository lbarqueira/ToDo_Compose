package com.luisbarqueira.todocompose.components


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisbarqueira.todocompose.data.models.Priority
import com.luisbarqueira.todocompose.ui.theme.LARGE_PADDING
import com.luisbarqueira.todocompose.ui.theme.PRIORITY_INDICATOR_SIZE

@Composable
fun PriorityItem(priority:Priority){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)){
            drawCircle(color = priority.color)
        }
        Text(
            text = priority.name,
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.padding(start = LARGE_PADDING)
        )
    }
}

@Composable
@Preview
fun PriorityItemPreview(){
    PriorityItem(priority = Priority.HIGH)
}