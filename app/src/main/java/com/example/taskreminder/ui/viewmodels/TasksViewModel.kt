package com.example.taskreminder.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskreminder.data.Task
import com.example.taskreminder.data.TasksDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TasksViewModel(private val tasksDao: TasksDao) : ViewModel() {
    // Tasks list
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    // AddTaskDialog fields
    var acronym = MutableStateFlow("")
        private set
    var description = MutableStateFlow("")
        private set
    var eventDate = MutableStateFlow("00.00.0000")
        private set
    var eventTime = MutableStateFlow("00:00")
        private set

    init {
        loadTasks()
    }

    // Operations with AddTaskDialog
    fun onAcronymChange(newAcronym: String) {
        acronym.value = newAcronym
    }
    fun onDescriptionChange(newDescription: String) {
        description.value = newDescription
    }
    fun onDateChange() {}

    // Operations with Tasks stored
    private fun loadTasks() {
        viewModelScope.launch {
            _tasks.value = tasksDao.getAllTasks()
        }
    }
    fun addTask() {
        viewModelScope.launch {
            var formattedAcronym = acronym.value.trim().uppercase()
            if (formattedAcronym.isEmpty()) {
                formattedAcronym = "###"
            } else {
                if (formattedAcronym.length > 3) {
                    formattedAcronym = formattedAcronym.take(3)
                }
                if (formattedAcronym.length < 3) {
                    formattedAcronym = "#".repeat(3 - formattedAcronym.length) + formattedAcronym
                }
            }

            val task = Task(
                acronym = formattedAcronym,
                description = description.value,
                evenDate = eventDate.value,
                eventTime = eventTime.value
            )
            tasksDao.insertTask(task)
            loadTasks()
            // Clean-up fields for next use
            acronym.value = ""
            description.value = ""
        }
    }
    fun deleteTask(taskId: Int) {
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
}