package com.boliveira.rxkotlin.rxutil

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.boliveira.rxkotlin.util.PagingViewModel
import rx.lang.kotlin.PublishSubject
import rx.subjects.PublishSubject

fun RecyclerView.rx_onItemClicked(): rx.Observable<Int> {
    return onViewClicked(PublishSubject()).map { view ->
        getChildViewHolder(view).adapterPosition
    }
}

private fun RecyclerView.onViewClicked(pubSub: PublishSubject<View>): rx.Observable<View> {
    this.addOnChildAttachStateChangeListener(object: RecyclerView.OnChildAttachStateChangeListener {
        override fun onChildViewAttachedToWindow(view: View?) {
            view?.rx_clicked(pubSub)
        }

        override fun onChildViewDetachedFromWindow(view: View?) {
            view?.setOnClickListener(null)
        }
    })
    return pubSub.asObservable()
}

fun RecyclerView.rx_onPage(viewModel: PagingViewModel, threshold: Int = 20): rx.Observable<Int> {
    if (layoutManager !is LinearLayoutManager)
        throw NotImplementedError("This method only works with linear layout manager")
    val page = Variable<Int>(viewModel.currentPage)

    this.setOnScrollChangeListener { view, int1, int2, int3, int4 ->
        synchronized(page) {
            val linearLayoutManager = layoutManager as LinearLayoutManager

            val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
            val totalItems = linearLayoutManager.itemCount

            if (lastVisibleItem > totalItems - threshold && !viewModel.isRequesting) {
                page.value = viewModel.currentPage
            }
        }
    }
    return page.asObservable()
}