package com.boliveira.rxkotlin.activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.transition.TransitionInflater
import android.view.MenuItem
import android.widget.ImageView

import boliveira.com.rxkotlinmvvm.R
import com.boliveira.rxkotlin.fragment.CompanyDetailFragment
import com.boliveira.rxkotlin.fragment.CompanyListFragment
import com.boliveira.rxkotlin.model.CompanyDetailFragmentModel
import com.boliveira.rxkotlin.presenter.CompanyListPresenter
import com.boliveira.rxkotlin.presenter.CompanyPresenter
import com.boliveira.rxkotlin.presenter.WebUrlPresenter
import com.boliveira.rxkotlin.util.LogE


class MainActivity : AppCompatActivity(), CompanyListPresenter, CompanyPresenter, WebUrlPresenter {


    override val presentingContext = this

    lateinit var toolbar: Toolbar

    val fragmentHolderIdentifier = R.id.fragment_holder

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

        showFragmentIfStackEmpty()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun showFragmentIfStackEmpty() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            showCompanyList()
            return
        }

        val fragment = supportFragmentManager.fragments.last()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager.beginTransaction()
                .replace(fragmentHolderIdentifier, fragment)
                .commit()
    }

    override fun transactionFromFragment(viewFragmentModelCompany: CompanyDetailFragmentModel):
            Pair<Fragment, FragmentTransaction> {
        val toFragment =
                supportFragmentManager.findFragmentByTag(CompanyDetailFragmentModel::class.java.name) ?:
                        CompanyDetailFragment.withViewModel(viewFragmentModelCompany)
        val transaction = supportFragmentManager.beginTransaction()
                .replace(fragmentHolderIdentifier, toFragment)
                .addToBackStack(toFragment.javaClass.name)

        return Pair(toFragment, transaction)
    }

    override fun showCompanyList() {
        val fragment = supportFragmentManager.findFragmentByTag(CompanyListFragment::class.java.name) ?:
                CompanyListFragment.initNew()

        supportFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out,android.R.anim.fade_in,android.R.anim.fade_out)
                .replace(fragmentHolderIdentifier, fragment)
                .addToBackStack(fragment.javaClass.name)
                .commit()
    }

    override fun openPage(url: String) {
        try {
            val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(myIntent);
        } catch (e: ActivityNotFoundException) {
            LogE(e.toString())
        }
    }
}
