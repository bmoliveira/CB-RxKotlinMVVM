package com.boliveira.rxkotlin.activity

import android.os.Bundle

import boliveira.com.rxkotlinmvvm.R
import com.boliveira.rxkotlin.model.LoadingActivityModel
import com.boliveira.rxkotlin.util.LogD
import com.boliveira.rxkotlin.util.LogE
import com.trello.rxlifecycle.components.support.RxAppCompatActivity


class LoadingActivity : RxAppCompatActivity(){
    lateinit var model: LoadingActivityModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        model = LoadingActivityModel(this)
        bindViewModel()
    }

    override fun onResume() {
        super.onResume()
        model.fetchCompanies().subscribe({}, {LogE("Error: $it")}, {LogD("Completed")})
    }

    private fun bindViewModel() {
        model.companies.subscribe { it?.let {LogD("result: $it")} }
    }
}



