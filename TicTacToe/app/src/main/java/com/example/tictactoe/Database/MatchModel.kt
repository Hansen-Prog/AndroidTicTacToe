package com.example.tictactoe.Database

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class MatchModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: MatchRepo
    val allMatches: LiveData<List<matchListInfo>>


    fun insert(match: matchListInfo) = scope.launch(Dispatchers.IO) {
        repository.insert(match)
    }

    fun deleteAll() = scope.launch(Dispatchers.IO){
        repository.deleteAll()
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    init {
        val matchDao = MatchDatabase.getDatabase(application,scope).matchDAO()
        repository = MatchRepo(matchDao)
        allMatches = repository.allStatsData
    }
}