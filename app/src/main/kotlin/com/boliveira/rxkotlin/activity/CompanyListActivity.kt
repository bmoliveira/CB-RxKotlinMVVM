package com.boliveira.rxkotlin.activity

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager

import boliveira.com.rxkotlinmvvm.R
import com.boliveira.rxkotlin.adapter.CompanyAdapter
import com.boliveira.rxkotlin.model.LoadingActivityModel
import com.boliveira.rxkotlin.rxutil.putViewModel
import com.boliveira.rxkotlin.rxutil.rx_onItemClicked
import com.boliveira.rxkotlin.util.LogD
import com.boliveira.rxkotlin.util.LogE
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.activity_loading.*

class CompanyListActivity : RxAppCompatActivity(){
    val adapter = CompanyAdapter()
    lateinit var model: LoadingActivityModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_list)
        model = LoadingActivityModel(this)
        setupAdapter()
        bindViewModel()
    }

    override fun onResume() {
        super.onResume()
        model.fetchCompanies()
                .subscribe(
                        {},
                        {LogE("Error: $it")},
                        {LogD("Completed")}
                )
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
    }
}