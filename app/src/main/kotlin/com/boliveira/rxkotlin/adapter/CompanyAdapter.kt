package com.boliveira.rxkotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import boliveira.com.rxkotlinmvvm.R
import com.boliveira.rxkotlin.adapter.holder.CompanyViewHolder
import com.boliveira.rxkotlin.model.CompanyItemModel
import com.boliveira.rxkotlin.rxutil.RxRecyclerViewAdapter

class CompanyAdapter: RxRecyclerViewAdapter<CompanyItemModel, CompanyViewHolder>() {
    override fun onBindViewHolder(holder: CompanyViewHolder?, position: Int) {
        holder?.viewModel = getItemAtIndex(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CompanyViewHolder? {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.company_recycler_item, null)
        return CompanyViewHolder(view)
    }
}
