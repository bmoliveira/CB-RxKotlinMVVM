package com.boliveira.crunchbase.rxutil


import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment

import android.support.v7.app.AppCompatActivity

private val viewModelKey = "view_model"

//Intent extensions to inject viewModel
fun Intent.putViewModel(value: Parcelable?) {
    value?.let {
        putExtra(viewModelKey, it)
    }
}

//Bundle extensions to inject viewModel
fun Bundle.putViewModel(value: Parcelable?) {
    value?.let {
        putParcelable(viewModelKey, it)
    }
}

//AppCompat extensions to retrieve
fun <T: Parcelable> AppCompatActivity.getViewModel(): T? {
    return intent?.getParcelableExtra<T>(viewModelKey)
}

//Support fragment extensions to retrieve and set viewModel
fun <T: Parcelable> Fragment.getViewModel(): T? {
    return arguments?.getParcelable(viewModelKey)
}

fun Fragment.setViewModelArgs(value: Parcelable) {
    var args = Bundle()
    args.putParcelable(viewModelKey, value)
    arguments = args
}

fun <T: Fragment> T.injectViewModel(viewModel: Parcelable): T {
    this.setViewModelArgs(viewModel)
    return this
}