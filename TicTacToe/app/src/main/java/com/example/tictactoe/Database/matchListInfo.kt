package com.example.tictactoe.Database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

//The Base Information Saved to Database
@Entity(tableName = "stats_table")
data class matchListInfo(
    @PrimaryKey (autoGenerate = true)
    val id : Int,
    @NonNull
    @ColumnInfo(name = "winner")
    val winner : String,

    @NonNull
    @ColumnInfo(name = "stats")
    val stats : String
)
