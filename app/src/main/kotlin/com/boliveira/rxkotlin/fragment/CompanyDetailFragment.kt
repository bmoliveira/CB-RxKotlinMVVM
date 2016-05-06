package com.boliveira.rxkotlin.fragment

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import boliveira.com.rxkotlinmvvm.R
import com.boliveira.rxkotlin.model.CompanyDetailFragmentModel
import com.boliveira.rxkotlin.rxutil.getViewModel
import com.boliveira.rxkotlin.rxutil.injectViewModel
import com.boliveira.rxkotlin.rxutil.putViewModel
import com.boliveira.rxkotlin.util.LateInitModel
import com.trello.rxlifecycle.components.support.RxFragment
import kotlinx.android.synthetic.main.activity_main.*

class CompanyDetailFragment(): RxFragment(), LateInitModel<CompanyDetailFragmentModel> {
    override var lateinitModel: CompanyDetailFragmentModel? = null
    lateinit var toolbar: Toolbar

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
        initViews()
        modelReplaceNonNull(getViewModel())
    }

    override fun onResume() {
        super.onResume()
        toolbar.title = model.title
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putViewModel(model)
    }

    private fun initViews() {
        toolbar = activity.toolbar
    }
}
