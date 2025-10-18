package de.ulme.todo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import de.ulme.todo.data.models.ToDoTask
import de.ulme.todo.util.Constants.TABLE_TASK
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the ToDoTask entity.
 */
@Dao
interface ToDoDao {

    /**
     * Retrieves all tasks from the database, ordered by their ID in ascending order.
     *
     * @return a Flow emitting a list of all ToDoTasks.
     */
    @Query("SELECT * FROM $TABLE_TASK ORDER BY id ASC")
    fun getAllTasks(): Flow<List<ToDoTask>>

    /**
     * Retrieves a specific task from the database by its ID.
     *
     * @param taskId the ID of the task to retrieve.
     * @return a Flow emitting the selected ToDoTask.
     */
    @Query("SELECT * FROM $TABLE_TASK WHERE id=:taskId")
    fun getSelectedTask(taskId: Int): Flow<ToDoTask?>

    /**
     * Inserts a new task into the database.
     *
     * @param toDoTask the task to be added.
     */
    @Insert
    suspend fun addTask(toDoTask: ToDoTask)

    /**
     * Updates an existing task in the database.
     *
     * @param toDoTask the task to be updated.
     */
    @Update
    suspend fun updateTask(toDoTask: ToDoTask)

    @Delete
    suspend fun deleteTask(toDoTask: ToDoTask)

    @Query("DELETE FROM $TABLE_TASK")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM $TABLE_TASK WHERE title LIKE :searchQuery OR description LIKE :searchQuery ORDER BY id ASC")
    fun searchDatabase(searchQuery: String): Flow<List<ToDoTask>>

    @Query(
        "SELECT * FROM $TABLE_TASK ORDER BY CASE " +
                "WHEN priority LIKE 'L%' THEN 1 " +
                "WHEN priority LIKE 'M%' THEN 2 " +
                "WHEN priority LIKE 'H%' THEN 3 " +
                "END"
    )
    fun sortByLowPriority(): Flow<List<ToDoTask>>

    @Query(
        "SELECT * FROM $TABLE_TASK ORDER BY CASE " +
                "WHEN priority LIKE 'H%' THEN 1 " +
                "WHEN priority LIKE 'M%' THEN 2 " +
                "WHEN priority LIKE 'L%' THEN 3 " +
                "END"
    )
    fun sortByHighPriority(): Flow<List<ToDoTask>>
}