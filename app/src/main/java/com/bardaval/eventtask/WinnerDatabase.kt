package com.bardaval.eventtask

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Winner::class], version = 1)
abstract class WinnerDatabase : RoomDatabase() {
    abstract fun winnerDao(): WinnerDao

    companion object {
        @Volatile
        private var INSTANCE: WinnerDatabase? = null

        fun getDatabase(context: Context): WinnerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WinnerDatabase::class.java,
                    "winner_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
