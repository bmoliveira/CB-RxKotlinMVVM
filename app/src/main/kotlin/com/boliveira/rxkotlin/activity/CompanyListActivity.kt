package com.boliveira.rxkotlin.activity

import android.content.Intent
import android.os.Bundle
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
        if (model.currentPage == 1) {
            model.fetchCompanies()
                    .bindToLifecycle(this@CompanyListActivity)
                    .subscribe(
                            {},
                            { LogE("Error: $it") },
                            { LogD("Completed") }
                    )
        }
    }

    private fun bindViewModel() {
        adapter.subscribe(model.companies)
    }

    private fun setupAdapter() {
        companies_recycler.layoutManager = LinearLayoutManager(this)
        companies_recycler.adapter = adapter
        companies_recycler
                .rx_onItemClicked()
                .bindToLifecycle(this)
                .subscribe { next ->
                    val intent = Intent(baseContext, CompanyDetailActivity::class.java)
                    intent.putViewModel(model.detailModelForIndex(next))
                    startActivity(intent)
                }

        companies_recycler.rx_onPage(model)
        .bindToLifecycle(this)
        .subscribe { page ->
            model.fetchCompanies().subscribe()
        }
    }
}