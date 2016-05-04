package com.boliveira.rxkotlin.util

interface PagingViewModel {
    var currentPage: Int
    var requestingPage: Int?

    val isRequesting: Boolean
        get() = requestingPage != null

    fun requestStarted() {
        requestingPage = currentPage
    }

    fun requestEnded() {
        requestingPage = null
        currentPage += 1
    }
}
