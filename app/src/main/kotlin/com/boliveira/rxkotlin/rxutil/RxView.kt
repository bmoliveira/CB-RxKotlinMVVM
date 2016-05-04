package com.boliveira.rxkotlin.rxutil

import android.view.View
import rx.lang.kotlin.PublishSubject
import rx.subjects.PublishSubject

fun View.rx_clicked(pubSub: PublishSubject<View> = PublishSubject<View>()): rx.Observable<View> {
    this.setOnClickListener { view ->
        pubSub.onNext(view)
    }
    return pubSub.asObservable()
}