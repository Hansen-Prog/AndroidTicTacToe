package com.example.tictactoe.Database

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


//The RoomDatabase that runs LiveData in the background
@Database(entities = [matchListInfo::class], version = 2)
abstract class MatchDatabase : RoomDatabase() {

    abstract fun matchDAO() : matchDAO

    companion object {
        @Volatile
        private var INSTANCE: MatchDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): MatchDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MatchDatabase::class.java,
                    "word_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}