package com.example.taskreminder.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskreminder.data.Task
import com.example.taskreminder.data.TasksDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TasksViewModel(private val tasksDao: TasksDao) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    init {
        loadTasks()
    }

    // Operation with Tasks stored
    fun loadTasks() {
        viewModelScope.launch {
            _tasks.value = tasksDao.getAllTasks()
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

    fun addTestTask() {
        viewModelScope.launch {
            val toBeDeletedTask = Task(acronym = "MAS", description = "Test T4")
            tasksDao.insertTask(toBeDeletedTask)
            loadTasks()
        }
    }
}