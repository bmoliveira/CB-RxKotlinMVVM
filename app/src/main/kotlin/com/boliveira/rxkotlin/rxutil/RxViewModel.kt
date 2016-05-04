package com.boliveira.rxkotlin.rxutil

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity

fun Intent.putViewModel(value: Parcelable?) {
    value?.let {
        putExtra("view_model", it)
    }
}

fun Bundle.putViewModel(value: Parcelable?) {
    value?.let {
        putParcelable("view_model", it)
    }
}

fun <T: Parcelable> AppCompatActivity.getViewModel(): T? {
    return intent?.getParcelableExtra<T>("view_model")
}

