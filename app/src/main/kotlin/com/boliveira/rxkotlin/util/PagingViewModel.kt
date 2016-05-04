package com.boliveira.rxkotlin.util

interface PagingViewModel {
    var currentPage: Int
    var requestingPage: Int?

    val isRequesting: Boolean
        get() = requestingPage != null

    fun pagingReset() {
        currentPage = 1
    }

    fun pagingRequestStarted() {
        requestingPage = currentPage
    }

    fun pagingRequestEnded() {
        requestingPage = null
        currentPage += 1
    }
}
