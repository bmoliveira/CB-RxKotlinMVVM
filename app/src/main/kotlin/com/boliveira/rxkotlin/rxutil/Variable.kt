package com.boliveira.rxkotlin.rxutil

import rx.lang.kotlin.BehaviorSubject

class Variable<T>(value: T) {
    private var _valuePublisher = BehaviorSubject<T>()

    var value: T = value
    set(newValue) {
        synchronized(this) {
            _valuePublisher.onNext(newValue)
            field = newValue
        }
    }
    fun asObservable() = _valuePublisher.asObservable()
}