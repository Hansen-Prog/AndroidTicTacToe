package com.example.tictactoe.viewAndAdapters

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class ModifiedViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    private var swipeEnabled = false

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        if (this.swipeEnabled) {
            return super.onTouchEvent(event)
        }

        return false
    }


    override fun onInterceptTouchEvent(event : MotionEvent) : Boolean {
        if (this.swipeEnabled) {
            return super.onInterceptTouchEvent(event)
        }

        return false
    }

    fun setPagingEnabled(swipeEnabled : Boolean) {
        this.swipeEnabled = swipeEnabled;
    }
}