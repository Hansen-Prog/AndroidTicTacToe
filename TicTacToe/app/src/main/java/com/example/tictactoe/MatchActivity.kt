package com.example.tictactoe

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.tictactoe.Database.MatchModel
import com.example.tictactoe.Database.matchDAO
import com.example.tictactoe.Database.matchListInfo
import com.example.tictactoe.viewAndAdapters.MatchFragCycleAdapter
import kotlinx.android.synthetic.main.layout_match_history.*

class MatchActivity : AppCompatActivity() {

    private lateinit var matchModel: MatchModel
    private lateinit var match: matchListInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_match_history)

        val recyclerView = findViewById<RecyclerView>(R.id.match_recycler)
        val matchAdapter = MatchFragCycleAdapter(this)
        recyclerView.adapter = matchAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //observering mostly livedata in MatchModel Class
        matchModel = ViewModelProviders.of(this).get(MatchModel::class.java)
        matchModel.allMatches.observe(this, Observer { matches ->
            matches?.let { matchAdapter.setMatch(it) }
        })

        //gathers the info from SaveToDB() in MainActivity()
        val matchStats = intent
        val isPassed = matchStats.getBooleanExtra("isPassed", false)
        if (isPassed) {
            matchStats.let {
                match = matchListInfo(0,matchStats.getStringExtra("PlayerDb"), matchStats.getStringExtra("ScoreDb"))
                matchModel.insert(match)
            }
        }
        else{
        }

            match_menu_btn.setOnClickListener {
                val btnIntent = Intent(this, ActivityStart::class.java)
                startActivity(btnIntent)
            }

            //Clears the db
            match_clear_btn.setOnClickListener {
                matchModel.deleteAll()
            }
        }
    }
