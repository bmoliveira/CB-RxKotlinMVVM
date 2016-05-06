package com.boliveira.rxkotlin.util

import android.util.Log

fun Any.TAG(): String {
    return this.javaClass.name
}

fun Any.LogE(message: String = "Default message $this") {
    Log.e(TAG(), message);
}

fun Any.LogD(message: String = "Default message $this") {
    Log.d(TAG(), message);
}

fun Any.LogW(message: String = "Default message $this") {
    Log.w(TAG(), message);
}
