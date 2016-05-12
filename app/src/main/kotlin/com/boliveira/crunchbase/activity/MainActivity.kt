package com.boliveira.crunchbase.activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.Toolbar
import android.transition.TransitionInflater
import android.view.MenuItem
import android.widget.ImageView

import boliveira.com.rxkotlinmvvm.R
import com.boliveira.crunchbase.fragment.CompanyDetailFragment
import com.boliveira.crunchbase.fragment.CompanyListFragment
import com.boliveira.crunchbase.model.CompanyDetailFragmentModel
import com.boliveira.crunchbase.presenter.CompanyDetailPresenter
import com.boliveira.crunchbase.presenter.CompanyListPresenter

import com.boliveira.crunchbase.presenter.WebUrlPresenter
import com.boliveira.crunchbase.util.LogE
import com.trello.rxlifecycle.components.support.RxAppCompatActivity


class MainActivity : RxAppCompatActivity(), CompanyListPresenter, CompanyDetailPresenter, WebUrlPresenter, ToolbarManager {
    val fragmentManager = supportFragmentManager
    val fragmentHolderIdentifier = R.id.fragment_holder

    override lateinit var toolbar: Toolbar

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

    // Show all companies
    override fun showCompanyList() {
        val fragment = fragmentManager.findFragmentByTag(CompanyListFragment::class.java.name) ?:
                CompanyListFragment.initNew()

        fragmentManager
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out,android.R.anim.fade_in,android.R.anim.fade_out)
                .replace(fragmentHolderIdentifier, fragment)
                .addToBackStack(fragment.javaClass.name)
                .commit()
    }

    /*
        Show a companydetail fragment
        If the originFragment and a shared imageview is given it adds an animation between fragments
    */
    override fun showCompany(viewFragmentModelCompany: CompanyDetailFragmentModel,
                             fromFragment: Fragment?, detailImage: ImageView?) {

        val targetFragment = fragmentManager.findFragmentByTag(CompanyDetailFragmentModel::class.java.name) ?:
                CompanyDetailFragment.withViewModel(viewFragmentModelCompany)

        val transaction = fragmentManager.beginTransaction()
                .replace(fragmentHolderIdentifier, targetFragment)
                .addToBackStack(targetFragment.javaClass.name)

        addAnimationToTransaction(
                transaction = transaction,
                fromFragment = fromFragment, toFragment = targetFragment,
                sharedElement = detailImage, sharedElementIdentifier = viewFragmentModelCompany.identifier)
                .commit()
    }

    private fun addAnimationToTransaction(
            transaction: FragmentTransaction,
            fromFragment: Fragment?, toFragment: Fragment,
            sharedElement: ImageView?, sharedElementIdentifier: String?
    ): FragmentTransaction {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP ||
                fromFragment == null || sharedElement == null || sharedElementIdentifier == null) {
            transaction.setCustomAnimations(
                    android.R.anim.fade_in,android.R.anim.fade_out)
            return transaction
        }

        fromFragment.sharedElementReturnTransition = TransitionInflater.from(this)
                .inflateTransition(R.transition.image_view_transition_set)
        fromFragment.exitTransition = TransitionInflater.from(this)
                .inflateTransition(android.R.transition.fade)

        toFragment.sharedElementEnterTransition = TransitionInflater.from(this)
                .inflateTransition(R.transition.image_view_transition_set)
        toFragment.enterTransition = TransitionInflater.from(this)
                .inflateTransition(android.R.transition.fade)

        transaction.addSharedElement(sharedElement, sharedElementIdentifier)

        return transaction
    }

    // Open browser at url
    override fun showPage(url: String) {
        try {
            val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url));
            this.startActivity(myIntent);
        } catch (e: ActivityNotFoundException) {
            LogE(e.toString())
        }
    }
}
