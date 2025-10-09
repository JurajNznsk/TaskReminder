package com.example.taskreminder.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskreminder.data.Task
import com.example.taskreminder.data.TasksDao
import kotlinx.coroutines.launch

class TasksViewmodel(private val tasksDao: TasksDao) : ViewModel() {

    var tasks: List<Task> = emptyList()
        private set

    // Operation with Tasks stored
    fun loadTasks() {
        viewModelScope.launch {
            tasks = tasksDao.getAllTasks()
        }
    }
    fun addTask(acronym: String, desription: String) {
        viewModelScope.launch {
            val task = Task(acronym = acronym, description = desription)
            tasksDao.insertTask(task)
            loadTasks()
        }
    }
    fun deletaTask(taskId: Int) {
        viewModelScope.launch {
            tasksDao.deleteTaskById(taskId)
            loadTasks()
        }
    }
    fun clearTasks() {
        viewModelScope.launch {
            tasksDao.deleteAllTasks()
            loadTasks()
        }
    }

    //
}