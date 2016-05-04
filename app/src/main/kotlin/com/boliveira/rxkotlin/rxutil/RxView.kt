package com.boliveira.rxkotlin.rxutil

import android.support.v7.widget.RecyclerView
import android.view.View
import rx.lang.kotlin.PublishSubject
import rx.subjects.PublishSubject

fun View.rx_clicked(pubSub: PublishSubject<View> = PublishSubject<View>()): rx.Observable<View> {
    this.setOnClickListener { view ->
        pubSub.onNext(view)
    }
    return pubSub.asObservable()
}

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