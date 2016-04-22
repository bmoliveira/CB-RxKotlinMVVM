package com.boliveira.rxkotlin.model

import com.boliveira.rxkotlin.network.CrunchBaseService
import com.boliveira.rxkotlin.rxutil.Variable
import com.boliveira.rxkotlin.rxutil.assignToIO
import com.boliveira.rxkotlin.rxutil.toForeground
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import com.trello.rxlifecycle.ActivityLifecycleProvider

class LoadingActivityModel(val boundedActivity: ActivityLifecycleProvider) {

    private var _companies = Variable<Model.ODMOrganizations?>(null)

    val companies = _companies
            .asObservable()
            .bindToLifecycle(boundedActivity)

    fun fetchCompanies() =
            CrunchBaseService
                    .builder
                    .organizations()
                    .assignToIO()
                    .toForeground()
                    .bindToLifecycle(boundedActivity)
                    .doOnNext { _companies.value = it }
                    .map {}
}