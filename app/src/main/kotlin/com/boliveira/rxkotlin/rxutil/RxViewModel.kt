package com.boliveira.rxkotlin.rxutil


import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment

import android.support.v7.app.AppCompatActivity

private val viewModelKey = "view_model"

fun <T: Parcelable> Intent.putViewModel(value: T?) {
    value?.let {
        putExtra(viewModelKey, it)
    }
}

fun <T: Parcelable> Bundle.putViewModel(value: T?) {
    value?.let {
        putParcelable(viewModelKey, it)
    }
}

fun <T: Parcelable> AppCompatActivity.getViewModel(): T? {
    return intent?.getParcelableExtra<T>(viewModelKey)
}

fun <T: Parcelable> Fragment.getViewModel(): T? {
    return arguments?.getParcelable(viewModelKey)
}

fun <T: Parcelable> Fragment.setViewModelArgs(value: T) {
    var args = Bundle()
    args.putParcelable(viewModelKey, value)
    arguments = args
}

fun <T: Fragment> T.injectViewModel(viewModel: Parcelable): T {
    this.setViewModelArgs(viewModel)
    return this
}