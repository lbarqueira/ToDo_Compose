package com.luisbarqueira.todocompose.data

import androidx.room.*
import com.luisbarqueira.todocompose.data.models.TodoTask
import kotlinx.coroutines.flow.Flow

// Data Access Object - where you define your database interactions.
@Dao
interface TodoDao {

    // The database operations can take a long time to execute, so they should run on a separate
    // thread. Make the function a suspend function, so that this function can be called from a coroutine.
    // The argument OnConflict tells the Room what to do in case of a conflict.
    // The OnConflictStrategy.IGNORE strategy ignores a new item if it's primary key is already in the database.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(todoTask: TodoTask)

    @Update
    suspend fun updateTask(todoTask: TodoTask)

    @Delete
    suspend fun deleteTask(todoTask: TodoTask)

    @Query("DELETE from todo_table")
    suspend fun deleteAllTasks()


    // Using Flow or LiveData as return type will ensure you get notified whenever the data in the database changes.
    // It is recommended to use Flow in the persistence layer.
    // The Room keeps this Flow updated for you, which means you only need to explicitly get the data once.
    // Because of the Flow return type, Room also runs the query on the background thread.
    // You don't need to explicitly make it a suspend function and call inside a coroutine scope.

    @Query("SELECT * from todo_table WHERE id = :taskId")
    fun getSelectedTask(taskId: Int): Flow<TodoTask>

    @Query("SELECT * from todo_table ORDER BY id ASC")
    fun getAllTasks(): Flow<List<TodoTask>>

    @Query("SELECT * from todo_table WHERE title LIKE :searchQuery OR description LIKE :searchQuery")
    fun searchDatabase(searchQuery:String):Flow<List<TodoTask>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority(): Flow<List<TodoTask>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority(): Flow<List<TodoTask>>

}