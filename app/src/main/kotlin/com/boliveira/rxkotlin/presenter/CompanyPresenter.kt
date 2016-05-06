package com.boliveira.rxkotlin.presenter

import com.boliveira.rxkotlin.model.CompanyDetailFragmentModel

/*
    This presenters are just to simple interfaces without logic
    just to give access to Fragments to show children views
 */

interface CompanyListPresenter {
    fun showCompanyList()
}

interface CompanyPresenter {
    fun showCompany(viewFragmentModelCompany: CompanyDetailFragmentModel)
}
