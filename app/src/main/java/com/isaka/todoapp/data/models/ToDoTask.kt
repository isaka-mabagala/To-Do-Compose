package com.isaka.todoapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.isaka.todoapp.util.Constants.DB_TABLE

@Entity(tableName = DB_TABLE)
data class ToDoTask(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority
)
