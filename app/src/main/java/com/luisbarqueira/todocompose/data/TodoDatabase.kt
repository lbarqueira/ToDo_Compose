package com.luisbarqueira.todocompose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luisbarqueira.todocompose.data.models.TodoTask

@Database(entities = [TodoTask::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {
    // abstract function that returns the TodoDao.
    abstract fun todoDao(): TodoDao

    // Define a companion object. The companion object allows access to the methods
    // for creating or getting the database using the class name as the qualifier.
/*    companion object {
        @Volatile
        private var INSTANCE: TodoDatabase? = null
        fun getDatabase(context: Context): TodoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "item_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }*/
}