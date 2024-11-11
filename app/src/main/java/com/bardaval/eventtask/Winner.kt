package com.bardaval.eventtask

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "winners")
data class Winner(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val timeTaken: Long
)
