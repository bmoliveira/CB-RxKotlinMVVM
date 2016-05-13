package com.boliveira.crunchbase.model

import com.boliveira.crunchbase.network.CrunchBaseService
import io.mironov.smuggler.AutoParcelable


data class CompanyDetailFragmentModel(private val companyItemModel: CompanyItemModel): AutoParcelable {
    val identifier = companyItemModel.identifier
    val title = companyItemModel.name
    val imageUrl = companyItemModel.imageUrl
    val twitter = companyItemModel.twitter
    val city = companyItemModel.city
    val description = companyItemModel.description
    val url = "${CrunchBaseService.companyEndpointPrefix}/${companyItemModel.url}"
}
