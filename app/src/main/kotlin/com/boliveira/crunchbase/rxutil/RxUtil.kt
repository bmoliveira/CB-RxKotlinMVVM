package com.boliveira.crunchbase.rxutil

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

//Thread switcher extensions to easly switch between threads with RxStreams
fun <T> Observable<T>.assignToIO(): Observable<T> {
    return this.subscribeOn(Schedulers.computation())
}

fun <T> Observable<T>.assignToBackground(): Observable<T> {
    return this.subscribeOn(Schedulers.computation())
}

fun <T> Observable<T>.toBackground(): Observable<T> {
    return this.observeOn(Schedulers.newThread())
}

fun <T> Observable<T>.toForeground(): Observable<T> {
    return this.observeOn(AndroidSchedulers.mainThread())
}
