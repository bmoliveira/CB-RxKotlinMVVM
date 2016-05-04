package com.boliveira.rxkotlin.activity

import android.os.Bundle
import android.support.v7.widget.Toolbar

import boliveira.com.rxkotlinmvvm.R
import com.boliveira.rxkotlin.model.DetailModel
import com.boliveira.rxkotlin.rxutil.getViewModel
import com.boliveira.rxkotlin.rxutil.putViewModel
import com.trello.rxlifecycle.components.support.RxAppCompatActivity

class CompanyDetailActivity : RxAppCompatActivity() {
    lateinit var model: DetailModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_detail)
        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)
        model = getViewModel() ?: throw NotImplementedError("At this point you need to have a viewmodel")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putViewModel(model)
    }
}
