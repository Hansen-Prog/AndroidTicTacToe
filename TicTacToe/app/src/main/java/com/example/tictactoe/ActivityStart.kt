package com.example.tictactoe

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.tictactoe.viewAndAdapters.ActivityStartPagerAdapter
import com.example.tictactoe.viewAndAdapters.ModifiedViewPager


class ActivityStart : AppCompatActivity() {

    private lateinit var pagerAdapter: ActivityStartPagerAdapter
    private lateinit var modifiedViewPager : ModifiedViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        pagerAdapter = ActivityStartPagerAdapter(supportFragmentManager)
        modifiedViewPager = findViewById(R.id.page_viewer)
        setPagerAdapter(modifiedViewPager)
    }

    //adding Fragments to a list of fragments
    private fun setPagerAdapter(modifiedViewPager: ModifiedViewPager){
        pagerAdapter = ActivityStartPagerAdapter(supportFragmentManager)
        pagerAdapter.addTicTacFragment(MenuFragment())
        pagerAdapter.addTicTacFragment(StartTwoPlayer())
        pagerAdapter.addTicTacFragment(StartSinglePlayer())
        modifiedViewPager.adapter = pagerAdapter
    }

    //sets the chosen fragment active from 1-3
    fun setCurrentPage(frag : Int){
        modifiedViewPager.currentItem = frag
    }



}
