package com.boliveira.rxkotlin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import boliveira.com.rxkotlinmvvm.R
import com.boliveira.rxkotlin.activity.ToolbarManager
import com.boliveira.rxkotlin.model.CompanyDetailFragmentModel
import com.boliveira.rxkotlin.presenter.WebUrlPresenter
import com.boliveira.rxkotlin.rxutil.getViewModel
import com.boliveira.rxkotlin.rxutil.injectViewModel
import com.boliveira.rxkotlin.rxutil.putViewModel
import com.boliveira.rxkotlin.rxutil.rx_clicked
import com.boliveira.rxkotlin.util.LateInitModel
import com.bumptech.glide.Glide
import com.trello.rxlifecycle.components.support.RxFragment
import kotlinx.android.synthetic.main.fragment_company_detail.*

class CompanyDetailFragment(): RxFragment(), LateInitModel<CompanyDetailFragmentModel> {
    override var lateinitModel: CompanyDetailFragmentModel? = null
    lateinit var toolbarManager: ToolbarManager
    lateinit var urlPresenter: WebUrlPresenter

    companion object Static {
        fun withViewModel(fragmentModelCompany: CompanyDetailFragmentModel): CompanyDetailFragment {
            return CompanyDetailFragment().injectViewModel(fragmentModelCompany)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_company_detail, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        modelReplaceNonNull(getViewModel())
        initViews()
    }

    override fun onResume() {
        super.onResume()
        toolbarManager.toolbar.title = model.title
        bindViewModel()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putViewModel(model)
    }

    private fun initViews() {
        company_detail_image.transitionName = model.identifier
        toolbarManager = activity as ToolbarManager
        urlPresenter = activity as WebUrlPresenter
    }

    private fun bindViewModel() {
        company_detail_image.apply {
            model.imageUrl?.let {
                Glide.with(this@CompanyDetailFragment).load(it)
                        .into(this)
            } ?: setImageResource(R.mipmap.not_found)
        }

        company_detail_name.apply {
            text = model.title
        }

        company_detail_twitter.apply {
            model.twitter?.let {
                text = it
                visibility = View.VISIBLE
            } ?: setVisibility(View.GONE)
        }

        company_detail_city.apply {
            model.city?.let {
                text = it
                visibility = View.VISIBLE
            } ?: setVisibility(View.GONE)
        }

        company_detail_description.apply {
            text = model.description
        }

        company_detail_source_button.apply {
            rx_clicked().subscribe { clicked ->
                urlPresenter.showPage(model.url)
            }
        }
    }
}
