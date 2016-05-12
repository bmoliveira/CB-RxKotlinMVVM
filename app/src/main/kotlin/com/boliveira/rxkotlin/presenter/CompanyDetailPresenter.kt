package com.boliveira.rxkotlin.presenter

import android.support.v4.app.Fragment
import android.widget.ImageView
import com.boliveira.rxkotlin.model.CompanyDetailFragmentModel

interface CompanyDetailPresenter {
    fun showCompany(viewFragmentModelCompany: CompanyDetailFragmentModel,
                    fromFragment: Fragment? = null, detailImage: ImageView? = null)
}
