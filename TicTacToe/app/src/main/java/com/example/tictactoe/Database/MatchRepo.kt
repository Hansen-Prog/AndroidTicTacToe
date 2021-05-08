package com.example.tictactoe.Database

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread

//Controls queries and is associated with the dao
class MatchRepo(private val matchDao : matchDAO){

    val allStatsData : LiveData<List<matchListInfo>> = matchDao.getAllStats()

    @WorkerThread
    suspend fun insert(match : matchListInfo){
        matchDao.insert(match)
    }

    @WorkerThread
    suspend fun deleteAll(){
        matchDao.deleteAll()
    }
}