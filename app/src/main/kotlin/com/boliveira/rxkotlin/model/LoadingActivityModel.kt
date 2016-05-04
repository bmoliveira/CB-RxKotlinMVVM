package com.boliveira.rxkotlin.model

import com.boliveira.rxkotlin.network.CrunchBaseService
import com.boliveira.rxkotlin.rxutil.Variable
import com.boliveira.rxkotlin.rxutil.assignToIO
import com.boliveira.rxkotlin.rxutil.toForeground
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import com.trello.rxlifecycle.ActivityLifecycleProvider

data class LoadingActivityModel(val boundedActivity: ActivityLifecycleProvider) {

    private var _companies = Variable<Array<CompanyItemModel>?>(null)

    val companies = _companies
            .asObservable()
            .bindToLifecycle(boundedActivity)

    fun detailModelForIndex(index: Int): DetailModel? {
        _companies.value?.get(index)?.let {
            return DetailModel(it)
        }
        return null
    }

    fun fetchCompanies() =
            CrunchBaseService.builder.organizations()
                    //Compute in background
                    .assignToIO()
                    //Respond in foreground
                    .toForeground()
                    //Bindo to activity lifecycle
                    .bindToLifecycle(boundedActivity)
                    //Transform in company model array
                    .map { CompanyItemModel.CompaniesFromResponse(it) }
                    //Use computed value
                    .doOnNext { next ->
                        next?.let { companies ->
                            _companies.value = companies
                        }
                    }
                    //Transform in void because we only want to send errors here
                    .map {}
}