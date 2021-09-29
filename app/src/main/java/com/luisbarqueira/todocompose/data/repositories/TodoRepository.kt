package com.luisbarqueira.todocompose.data.repositories

import com.luisbarqueira.todocompose.data.TodoDao
import com.luisbarqueira.todocompose.data.models.TodoTask
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class TodoRepository @Inject constructor(private val todoDao: TodoDao) {

    val getAllTasks: Flow<List<TodoTask>> = todoDao.getAllTasks()
    val sortByLowPriority: Flow<List<TodoTask>> = todoDao.sortByLowPriority()
    val sortByHighPriority: Flow<List<TodoTask>> = todoDao.sortByHighPriority()

    fun getSelectedTask(taskId: Int): Flow<TodoTask> {
        return todoDao.getSelectedTask(taskId = taskId)
    }

    suspend fun addTask(todoTask: TodoTask) {
        todoDao.addTask(todoTask = todoTask)
    }

    suspend fun updateTask(todoTask: TodoTask) {
        todoDao.updateTask(todoTask = todoTask)
    }

    suspend fun deleteTask(todoTask: TodoTask) {
        todoDao.deleteTask(todoTask = todoTask)
    }

    suspend fun deleteAllTasks() {
        todoDao.deleteAllTasks()
    }

    fun searchDatabase(searchQuery: String): Flow<List<TodoTask>> {
        return todoDao.searchDatabase(searchQuery = searchQuery)
    }
}