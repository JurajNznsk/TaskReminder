package com.example.taskreminder.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val acronym: String,
    val description: String,
    val evenDate: String, // dd.mm.yyyy
    val eventTime: String, // HH:mm

    val important: Boolean, // T - starClicked; F - starNotClicked
    val todo: Boolean // T - showing in TO-DO tab; F - showing in DONE tab
)