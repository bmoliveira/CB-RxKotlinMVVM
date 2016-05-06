package com.boliveira.rxkotlin.util

/**
 * Created by bruno on 04/05/16.
 */
import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet


class TopBottomSwipeRefreshLayout(context: Context, attrs: AttributeSet?) : SwipeRefreshLayout(context, attrs) {
    companion object {
        var defaultThreshold = 100
    }

    fun showTopRefresh(){
        this.setProgressViewEndTarget(true, TopBottomSwipeRefreshLayout.defaultThreshold)
        this.isRefreshing = true
    }

    fun showBottomRefresh(){
        this.setProgressViewEndTarget(true, this.height - TopBottomSwipeRefreshLayout.defaultThreshold)
        this.isRefreshing = true
    }

    fun hideRefresh(){
        this.isRefreshing = false
        this.setProgressViewEndTarget(true, TopBottomSwipeRefreshLayout.defaultThreshold)
    }
}