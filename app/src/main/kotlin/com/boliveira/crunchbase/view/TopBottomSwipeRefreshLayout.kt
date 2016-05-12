package com.boliveira.crunchbase.view

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet

class TopBottomSwipeRefreshLayout(context: Context, attrs: AttributeSet?) : SwipeRefreshLayout(context, attrs) {
    companion object {
        var defaultThreshold = 150
    }

    fun showTopRefresh(){
        this.setProgressViewEndTarget(true, Companion.defaultThreshold)
        this.isRefreshing = true
    }

    fun showBottomRefresh(){
        this.setProgressViewEndTarget(true, this.height - Companion.defaultThreshold)
        this.isRefreshing = true
    }

    fun hideRefresh(){
        this.isRefreshing = false
        this.setProgressViewEndTarget(true, Companion.defaultThreshold)
    }
}
