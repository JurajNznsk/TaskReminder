package com.example.taskreminder.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskreminder.data.Task
import com.example.taskreminder.data.TasksDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class TasksViewModel(private val tasksDao: TasksDao) : ViewModel() {
    // Tasks list
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    // AddTaskDialog fields
    var acronym = MutableStateFlow("")
        private set
    var description = MutableStateFlow("")
        private set
    private val dateFormat = SimpleDateFormat("EE dd.MM.yyyy", Locale.getDefault())
    var eventDate = MutableStateFlow(dateFormat.format(Date()))
        private set
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    var eventTime = MutableStateFlow(timeFormat.format(Date()))
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
    fun onDateChange(newDate: String) {
        eventDate.value = newDate
    }
    fun onTimeChange(newTime: String) {
        eventTime.value = newTime
    }

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
                eventTime = eventTime.value,
                important = false,
                todo = true
            )
            tasksDao.insertTask(task)
            loadTasks()
            // Clean-up fields for next use
            acronym.value = ""
            description.value = ""
            val currentCalendar = Calendar.getInstance()
            val dateFormatter = SimpleDateFormat("EE dd.MM.yyyy", Locale.getDefault())
            eventDate.value = dateFormatter.format(currentCalendar.time)
            val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
            eventTime.value = timeFormatter.format(currentCalendar.time)
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
    fun changeImportance(task: Task) {
        viewModelScope.launch {
            val updatedTask = task.copy(important = !task.important)
            tasksDao.updateTask(updatedTask)
            loadTasks()
        }
    }
    fun changeTodoDone(task: Task) {
        viewModelScope.launch {
            val updatedTask = task.copy(todo = !task.todo)
            tasksDao.updateTask(updatedTask)
            loadTasks()
        }
    }
}