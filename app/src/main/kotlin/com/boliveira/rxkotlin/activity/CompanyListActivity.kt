package com.boliveira.rxkotlin.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager

import boliveira.com.rxkotlinmvvm.R
import com.boliveira.rxkotlin.adapter.CompanyAdapter
import com.boliveira.rxkotlin.model.LoadingActivityModel
import com.boliveira.rxkotlin.rxutil.getViewModel
import com.boliveira.rxkotlin.rxutil.putViewModel
import com.boliveira.rxkotlin.rxutil.rx_onItemClicked
import com.boliveira.rxkotlin.rxutil.rx_onPage
import com.boliveira.rxkotlin.util.LogD
import com.boliveira.rxkotlin.util.LogE
import com.boliveira.rxkotlin.util.SpaceItemDecoration
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.activity_company_list.*

class CompanyListActivity : RxAppCompatActivity(){
    val adapter = CompanyAdapter()
    lateinit var model: LoadingActivityModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_list)
        model = getViewModel<LoadingActivityModel>() ?: LoadingActivityModel()
        setupAdapter()
        bindViewModel()
    }

    override fun onResume() {
        super.onResume()
        if (model.currentPage <= 1) {
            companies_recycler_swipe_to_refresh.showTopRefresh()
            reloadCompanies()
        }
    }

    private fun reloadCompanies() {
        model.reloadCompanies()
                .bindToLifecycle(this)
                .subscribe(
                        { companies_recycler_swipe_to_refresh.hideRefresh() },
                        { LogE("Error: $it") },
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
        companies_recycler.layoutManager = LinearLayoutManager(this)
        companies_recycler.adapter = adapter
        companies_recycler.addItemDecoration(SpaceItemDecoration(this, R.dimen.big_margin))
        companies_recycler.rx_onItemClicked()
                .bindToLifecycle(this)
                .subscribe { next ->
                    val intent = Intent(baseContext, CompanyDetailActivity::class.java)
                    intent.putViewModel(model.detailModelForIndex(next))
                    startActivity(intent)
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