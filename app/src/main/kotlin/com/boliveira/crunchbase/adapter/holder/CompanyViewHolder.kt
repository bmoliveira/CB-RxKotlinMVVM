package com.boliveira.crunchbase.adapter.holder

import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import com.boliveira.crunchbase.R
import com.boliveira.crunchbase.model.CompanyItemModel
import com.bumptech.glide.Glide
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
                    twitter.text = this
                } ?: twitter.setVisibility(View.GONE)

                it.city?.apply {
                    cityCountry.visibility = View.VISIBLE
                    cityCountry.text = this
                } ?: cityCountry.setVisibility(View.GONE)

                companyType.text = it.type?.capitalize()
                image.transitionName = it.identifier
                it.imageUrl?.apply {
                    Glide.with(view.context)
                            .load(this)
                            .error(R.mipmap.not_found)
                            .into(image)
                } ?: image.setImageResource(R.mipmap.not_found)
            } ?: resetCell()
        }

    private fun resetCell() {
        name.text = ""
        twitter.text = ""
        cityCountry.text = ""
        companyType.text = ""
    }
}
