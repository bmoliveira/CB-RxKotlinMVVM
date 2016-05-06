package com.boliveira.rxkotlin.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem

import boliveira.com.rxkotlinmvvm.R
import com.boliveira.rxkotlin.fragment.CompanyDetailFragment
import com.boliveira.rxkotlin.fragment.CompanyListFragment
import com.boliveira.rxkotlin.model.CompanyDetailFragmentModel
import com.boliveira.rxkotlin.presenter.CompanyListPresenter
import com.boliveira.rxkotlin.presenter.CompanyPresenter


class MainActivity : AppCompatActivity(), CompanyListPresenter, CompanyPresenter {
    val fragmentHolderIdentifier = R.id.fragment_holder

    lateinit var toolbar: Toolbar

    //Show back if its not the last fragment
    val onFragmentManagerBackstackChanged = {
        if (supportFragmentManager.backStackEntryCount <= 1) {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        supportFragmentManager.addOnBackStackChangedListener {
            onFragmentManagerBackstackChanged()
        }

        showCompanyList()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    override fun showCompany(viewFragmentModelCompany: CompanyDetailFragmentModel) {
        val fragment = CompanyDetailFragment.withViewModel(viewFragmentModelCompany)
        supportFragmentManager.beginTransaction()
                .replace(fragmentHolderIdentifier, fragment)
                .addToBackStack(fragment.javaClass.name)
                .commit()
    }

    override fun showCompanyList() {
        val fragment = CompanyListFragment.initNew()
        supportFragmentManager.beginTransaction()
                .replace(fragmentHolderIdentifier, fragment)
                .show(fragment)
                .addToBackStack(fragment.javaClass.name)
                .commit()
    }
}
