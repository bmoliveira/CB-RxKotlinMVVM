package com.boliveira.rxkotlin.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import boliveira.com.rxkotlinmvvm.R
import com.boliveira.rxkotlin.model.CompanyItemModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.company_recycler_item.view.*

class CompanyViewHolder(private var view: View): RecyclerView.ViewHolder(view) {
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

                it.twitter?.apply {
                    twitter.visibility = View.VISIBLE
                    twitter.text = replace("https://twitter.com/", "@")
                } ?: twitter.setVisibility(View.GONE)

                it.city?.let {
                    cityCountry.visibility = View.VISIBLE
                    cityCountry.text = it
                } ?: cityCountry.setVisibility(View.GONE)

                companyType.text = it.type?.capitalize()
                Picasso.with(view.context)
                        .load(it.imageUrl)
                        .into(image)

            } ?: resetCell()
        }

    private fun resetCell() {
        name.text = ""
        twitter.text = ""
        cityCountry.text = ""
        companyType.text = ""
    }
}
