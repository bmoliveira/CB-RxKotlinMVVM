package com.boliveira.rxkotlin.model

import com.boliveira.rxkotlin.network.CrunchBaseService
import com.boliveira.rxkotlin.rxutil.Variable
import com.boliveira.rxkotlin.rxutil.assignToIO
import com.boliveira.rxkotlin.rxutil.toForeground
import com.boliveira.rxkotlin.util.PagingViewModel
import io.mironov.smuggler.AutoParcelable

data class CompanyListFragmentModel(private var startingPage: Int = 1): AutoParcelable, PagingViewModel {
    override var currentPage = startingPage
    override var requestingPage: Int? = null

    private var _companies = Variable<Array<CompanyItemModel>?>(null)

    val companies = _companies.asObservable()

    // Return DetailModel at certain index if exists
    fun detailModelForIndex(index: Int): CompanyDetailFragmentModel? {
        _companies.value?.get(index)?.let {
            return CompanyDetailFragmentModel(it)
        }
        return null
    }

    fun reloadCompanies(): rx.Observable<Unit> {
        pagingReset()
        return fetchCompanies()
    }

    fun fetchCompanies(): rx.Observable<Unit> {
        pagingRequestStarted()
        return CrunchBaseService.builder.organizations(page = currentPage)
                //Compute in background
                .assignToIO()
                //Respond in foreground
                .toForeground()
                //Transform in company model array
                .map { CompanyItemModel.CompaniesFromResponse(it) }
                //Use computed value
                .doOnNext { next ->
                    next?.let { companies ->
                        if (currentPage == 1) {
                            _companies.value = companies
                        } else {
                            _companies.value = _companies.value?.plus(companies)
                        }
                        pagingRequestEnded()
                    }
                }
                //Transform in void because we only want to send errors here
                .map {}
    }
}