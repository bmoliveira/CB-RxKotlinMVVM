package com.boliveira.rxkotlin.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.boliveira.rxkotlin.model.CompanyItemModel
import kotlinx.android.synthetic.main.company_recycler_item.view.*

class CompanyViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val image = view.company_image
    val name = view.company_name
    val twitter = view.company_twitter
    val cityCountry = view.company_city_country
    val companyType = view.company_type

    init {
        resetCell()
    }

    var viewModel: CompanyItemModel? = null
        set(value) {
            value?.let {
                name.text = it.name
                twitter.text = it.twitter
                cityCountry.text = "${it.city} - ${it.country}"
                companyType.text = it.type
            } ?: resetCell()
        }

    private fun resetCell() {
        name.text = ""
        twitter.text = ""
        cityCountry.text = ""
        companyType.text = ""
    }
}
