package com.bardaval.eventtask

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WinnerDao {
    @Insert
    suspend fun insertWinner(winner: Winner)

    @Query("SELECT * FROM winners ORDER BY timeTaken ASC")
    suspend fun getAllWinners(): List<Winner>
}
