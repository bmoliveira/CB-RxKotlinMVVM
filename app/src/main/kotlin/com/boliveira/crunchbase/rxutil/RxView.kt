package com.boliveira.crunchbase.rxutil

import android.view.View
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import rx.lang.kotlin.PublishSubject
import rx.subjects.PublishSubject

//Rx signal to clicks on views
fun View.rx_clicked(pubSub: PublishSubject<View> = PublishSubject<View>()): rx.Observable<View> {
    this.setOnClickListener { view ->
        pubSub.onNext(view)
    }
    return pubSub.asObservable()
            .bindToLifecycle(this)
}