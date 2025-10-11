package de.ulme.todo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import de.ulme.todo.data.models.ToDoTask

@Database(entities = [ToDoTask::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao
}