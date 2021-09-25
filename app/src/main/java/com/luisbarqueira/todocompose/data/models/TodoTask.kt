package com.luisbarqueira.todocompose.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.luisbarqueira.todocompose.util.Constants.DATABASE_TABLE


// This class represents a database entity in your app.
// Above the TodoTask class declaration, annotate the data class with @Entity.
// Use tableName argument to give the todo_table as the SQLite table name.

@Entity(tableName = DATABASE_TABLE)
data class TodoTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority
)