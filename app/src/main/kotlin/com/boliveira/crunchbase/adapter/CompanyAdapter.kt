package com.boliveira.crunchbase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.boliveira.crunchbase.R
import com.boliveira.crunchbase.adapter.holder.CompanyViewHolder
import com.boliveira.crunchbase.model.CompanyItemModel
import com.boliveira.crunchbase.rxutil.RxRecyclerViewAdapter

class CompanyAdapter: RxRecyclerViewAdapter<CompanyItemModel, CompanyViewHolder>() {
    override fun onBindViewHolder(holder: CompanyViewHolder?, position: Int) {
        holder?.viewModel = getItemAtIndex(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CompanyViewHolder? {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.company_recycler_item, null)
        return CompanyViewHolder(view)
    }
}
