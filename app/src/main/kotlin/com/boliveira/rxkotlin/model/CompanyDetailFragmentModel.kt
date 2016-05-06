package com.boliveira.rxkotlin.model

import io.mironov.smuggler.AutoParcelable


data class CompanyDetailFragmentModel(private val companyItemModel: CompanyItemModel): AutoParcelable {
    val title = companyItemModel.name
}