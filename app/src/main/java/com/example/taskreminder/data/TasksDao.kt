package com.example.taskreminder.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface TasksDao {
    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<Task>

    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun deleteTaskById(taskId: Int)

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()
}