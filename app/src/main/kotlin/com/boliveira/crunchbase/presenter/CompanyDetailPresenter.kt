package com.boliveira.crunchbase.presenter

import android.support.v4.app.Fragment
import android.widget.ImageView
import com.boliveira.crunchbase.model.CompanyDetailFragmentModel

interface CompanyDetailPresenter {
    fun showCompany(viewFragmentModelCompany: CompanyDetailFragmentModel,
                    fromFragment: Fragment? = null, detailImage: ImageView? = null)
}
