package com.luisbarqueira.todocompose.ui.screens.list

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.luisbarqueira.todocompose.data.models.Priority
import com.luisbarqueira.todocompose.data.models.TodoTask
import com.luisbarqueira.todocompose.ui.theme.*
import com.luisbarqueira.todocompose.util.RequestState
import com.luisbarqueira.todocompose.util.SearchAppBarState

@ExperimentalMaterialApi
@Composable
fun ListContent(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    allTasks: RequestState<List<TodoTask>>,
    lowPriorityTasks: List<TodoTask>,
    highPriorityTasks: List<TodoTask>,
    sortState: RequestState<Priority>,
    searchAppBarState: SearchAppBarState,
    searchedTasks: RequestState<List<TodoTask>>
) {
    if (sortState is RequestState.Success) {
        when {
            searchAppBarState == SearchAppBarState.TRIGGERED -> {
                if (searchedTasks is RequestState.Success) {
                    HandleListContent(
                        tasks = searchedTasks.data,
                        navigateToTaskScreen = navigateToTaskScreen
                    )
                }
            }
            sortState.data == Priority.NONE -> {
                if (allTasks is RequestState.Success) {
                    HandleListContent(
                        tasks = allTasks.data,
                        navigateToTaskScreen = navigateToTaskScreen
                    )
                }
            }
            sortState.data == Priority.LOW -> {
                HandleListContent(
                    tasks = lowPriorityTasks,
                    navigateToTaskScreen = navigateToTaskScreen
                )
            }
            sortState.data == Priority.HIGH -> {
                HandleListContent(
                    tasks = highPriorityTasks,
                    navigateToTaskScreen = navigateToTaskScreen
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun HandleListContent(
    tasks: List<TodoTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit

) {
    if (tasks.isEmpty()) EmptyContent() else
        DisplayTasks(
            tasks = tasks,
            navigateToTaskScreen = navigateToTaskScreen
        )

}

@ExperimentalMaterialApi
@Composable
fun DisplayTasks(tasks: List<TodoTask>, navigateToTaskScreen: (taskId: Int) -> Unit) {
    LazyColumn {
        items(
            items = tasks,
            key = { task -> task.id }
        ) { task ->
            TaskItem(
                todoTask = task,
                navigateToTaskScreen = navigateToTaskScreen
            )
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun TaskItem(
    todoTask: TodoTask,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colors.taskItemBackgroundColor,
        shape = RectangleShape,
        elevation = TASK_ITEM_ELEVATION,
        onClick = {
            navigateToTaskScreen(todoTask.id)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(all = LARGE_PADDING)
                .fillMaxWidth()
        )
        {
            Row {
                Text(
                    modifier = Modifier.weight(8f),
                    text = todoTask.title,
                    color = MaterialTheme.colors.taskItemTextColor,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(PRIORITY_INDICATOR_SIZE)
                    ) {
                        drawCircle(
                            color = todoTask.priority.color
                        )
                    }
                }
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = todoTask.description,
                color = MaterialTheme.colors.taskItemTextColor,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

    }
}

@ExperimentalMaterialApi
@Composable
@Preview
fun TaskItemPreview() {
    TaskItem(
        todoTask = TodoTask(
            id = 0,
            title = "Title",
            description = "Some random text",
            priority = Priority.MEDIUM
        ),
        navigateToTaskScreen = {}
    )
}