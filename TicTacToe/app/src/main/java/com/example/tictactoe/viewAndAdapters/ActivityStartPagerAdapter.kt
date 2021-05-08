package com.example.tictactoe.viewAndAdapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class ActivityStartPagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    private final var fragmentList = arrayListOf<Fragment>()


    fun addTicTacFragment(frag: Fragment){
        fragmentList.add(frag)
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }
}