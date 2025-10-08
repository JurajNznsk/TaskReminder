package com.example.taskreminder.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.taskreminder.data.TasksDao
import com.example.taskreminder.data.TasksDatabase

class TasksViewmodel(private val tasksDao: TasksDao) : ViewModel() {
    suspend fun getAllTasks() = tasksDao.getAllTasks()
    suspend fun deleteAllTasks() = tasksDao.deleteAllTasks()
    suspend fun deleteTaskById(id: Int) = tasksDao.deleteTaskById(id)
}