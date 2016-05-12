package com.boliveira.crunchbase.rxutil

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.boliveira.crunchbase.util.PagingViewModel
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import rx.lang.kotlin.PublishSubject
import rx.subjects.PublishSubject

//Add a way to detect clicks on recycler view though streams
fun <T: RecyclerView.ViewHolder> RecyclerView.rx_onItemClicked(): rx.Observable<Pair<T, Int>> {
    return onViewClicked(PublishSubject()).map { view ->
        val holder = getChildViewHolder(view)
        Pair(holder as T, holder.adapterPosition)
    }
}

// This detects scroll reaching the end and request more pages
fun RecyclerView.rx_onPage(viewModel: PagingViewModel, threshold: Int = 20): rx.Observable<Int> {
    if (layoutManager !is LinearLayoutManager)
        throw NotImplementedError("This method only works with linear layout manager")
    val page = Variable<Int>(viewModel.currentPage)

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
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
    } else {
        this.setOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                synchronized(page) {
                    val linearLayoutManager = layoutManager as LinearLayoutManager

                    val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                    val totalItems = linearLayoutManager.itemCount

                    if (lastVisibleItem > totalItems - threshold && !viewModel.isRequesting) {
                        page.value = viewModel.currentPage
                    }
                }
            }
        })
    }
    return page.asObservable().bindToLifecycle(this)
}

// This attaches and detaches listeners on recycler view items
private fun RecyclerView.onViewClicked(pubSub: PublishSubject<View>): rx.Observable<View> {
    this.addOnChildAttachStateChangeListener(object: RecyclerView.OnChildAttachStateChangeListener {
        override fun onChildViewAttachedToWindow(view: View?) {
            view?.rx_clicked(pubSub)
        }

        override fun onChildViewDetachedFromWindow(view: View?) {
            view?.setOnClickListener(null)
        }
    })
    return pubSub.asObservable().bindToLifecycle(this)
}
