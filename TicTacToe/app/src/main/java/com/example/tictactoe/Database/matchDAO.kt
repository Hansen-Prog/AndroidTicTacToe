package com.example.tictactoe.Database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query


//queries against the database
@Dao
interface matchDAO {

    @Insert
    fun insert(match : matchListInfo)

    @Query("DELETE FROM stats_table")
    fun deleteAll()

    @Query("SELECT * FROM stats_table ORDER BY id DESC")
    fun getAllStats() : LiveData<List<matchListInfo>>



}