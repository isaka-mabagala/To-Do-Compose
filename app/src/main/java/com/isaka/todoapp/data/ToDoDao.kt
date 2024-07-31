package com.isaka.todoapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.isaka.todoapp.data.models.ToDoTask
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo_tb ORDER BY id DESC")
    fun getAllTasks(): Flow<List<ToDoTask>>

    @Query(" SELECT * FROM todo_tb WHERE id=:id")
    fun getSelectedTask(id: Int): Flow<ToDoTask>

    @Query(
        """
        SELECT * FROM todo_tb
        WHERE
        title LIKE :query
        OR
        description LIKE :query
    """
    )
    fun searchTasks(query: String): Flow<List<ToDoTask>>

    @Query(
        """
        SELECT * FROM todo_tb
        ORDER BY CASE
        WHEN priority LIKE 'L%' THEN 1
        WHEN priority LIKE 'M%' THEN 2
        WHEN priority LIKE 'H%' THEN 3
        END
    """
    )
    fun sortByLowPriority(): Flow<List<ToDoTask>>

    @Query(
        """
        SELECT * FROM todo_tb
        ORDER BY CASE
        WHEN priority LIKE 'H%' THEN 1
        WHEN priority LIKE 'M%' THEN 2
        WHEN priority LIKE 'L%' THEN 3
        END
    """
    )
    fun sortByHighPriority(): Flow<List<ToDoTask>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task: ToDoTask)

    @Update
    suspend fun updateTask(task: ToDoTask)

    @Delete
    suspend fun deleteTask(task: ToDoTask)

    @Query("DELETE FROM todo_tb")
    suspend fun deleteAllTasks()
}
