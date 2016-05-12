package com.boliveira.crunchbase.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import boliveira.com.rxkotlinmvvm.R
import com.boliveira.crunchbase.activity.ToolbarManager
import com.boliveira.crunchbase.adapter.CompanyAdapter
import com.boliveira.crunchbase.adapter.holder.CompanyViewHolder
import com.boliveira.crunchbase.model.CompanyListFragmentModel
import com.boliveira.crunchbase.presenter.CompanyDetailPresenter
import com.boliveira.crunchbase.rxutil.*
import com.boliveira.crunchbase.util.LateInitModel
import com.boliveira.crunchbase.util.LogD
import com.boliveira.crunchbase.util.LogE
import com.boliveira.crunchbase.view.SpaceItemDecoration
import com.trello.rxlifecycle.components.support.RxFragment
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.fragment_company_list.*

class CompanyListFragment(): RxFragment(), LateInitModel<CompanyListFragmentModel> {
    lateinit var companyPresenter: CompanyDetailPresenter
    lateinit var toolbarManager: ToolbarManager

    override var lateinitModel: CompanyListFragmentModel? = null

    val adapter = CompanyAdapter()

    companion object Initializer {
        fun initNew(): CompanyListFragment {
            return CompanyListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       return inflater?.inflate(R.layout.fragment_company_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        loadViewModel()
        setupAdapter()
        bindViewModel()
    }

    override fun onResume() {
        super.onResume()
        toolbarManager.toolbar.title = "Crunch Base Companies - London"
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putViewModel(model)
    }

    private fun initViews() {
        companyPresenter = activity as CompanyDetailPresenter
        toolbarManager = activity as ToolbarManager
    }

    private fun loadViewModel() {
        modelReplaceNonNull(getViewModel())
        if (!modelIsInited) {
            model = CompanyListFragmentModel()
            companies_recycler_swipe_to_refresh.showTopRefresh()
            reloadCompanies()
        }
    }

    private fun reloadCompanies() {
        model.reloadCompanies()
                .bindToLifecycle(this)
                .subscribe(
                        { companies_recycler_swipe_to_refresh.hideRefresh() },
                        { LogE("Error: $model") },
                        { LogD("Completed") }
                )

    }

    private fun bindViewModel() {
        adapter.subscribe(model.companies)
        companies_recycler_swipe_to_refresh.setOnRefreshListener {
            reloadCompanies()
        }
    }

    private fun setupAdapter() {
        companies_recycler.layoutManager = LinearLayoutManager(context)
        companies_recycler.adapter = adapter
        companies_recycler.addItemDecoration(SpaceItemDecoration(context, R.dimen.big_margin))
        companies_recycler.setHasFixedSize(true)
        companies_recycler.isVerticalScrollBarEnabled = true

        companies_recycler.rx_onItemClicked<CompanyViewHolder>()
                .bindToLifecycle(this)
                .subscribe { next ->
                    model.detailModelForIndex(next.second)?.let { detailModel ->
                        companyPresenter.showCompany(detailModel, this@CompanyListFragment, next.first.image)
                    }
                }

        companies_recycler.rx_onPage(model)
                .bindToLifecycle(this)
                .subscribe { page ->
                    companies_recycler_swipe_to_refresh.showBottomRefresh()
                    model.fetchCompanies().subscribe {
                        companies_recycler_swipe_to_refresh.hideRefresh()
                    }
                }
    }
}
