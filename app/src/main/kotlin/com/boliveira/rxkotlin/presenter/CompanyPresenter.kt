package com.boliveira.rxkotlin.presenter

import android.os.Build
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.transition.TransitionInflater
import android.widget.ImageView
import boliveira.com.rxkotlinmvvm.R
import com.boliveira.rxkotlin.model.CompanyDetailFragmentModel

/*
    This presenters are just to simple interfaces without logic
    just to give access to Fragments to show children views
 */
interface CompanyListPresenter {
    fun showCompanyList()
}

interface WebUrlPresenter {
    fun openPage(url: String)
}

interface CompanyPresenter {
    val presentingContext: AppCompatActivity

    fun transactionFromFragment(viewFragmentModelCompany: CompanyDetailFragmentModel): Pair<Fragment, FragmentTransaction>

    fun showCompany(fromFragment: Fragment,
                    viewFragmentModelCompany: CompanyDetailFragmentModel,
                    detailImage: ImageView? = null) {
        val transaction = transactionFromFragment(viewFragmentModelCompany)
        addAnimationToTransaction(
                transaction = transaction.second,
                fromFragment = fromFragment, toFragment = transaction.first,
                sharedElement = detailImage, sharedElementIdentifier = viewFragmentModelCompany.identifier)
                .commit()
    }


    private fun addAnimationToTransaction(transaction: FragmentTransaction,
            fromFragment: Fragment, toFragment: Fragment,
            sharedElement: ImageView?, sharedElementIdentifier: String?
            ): FragmentTransaction {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fromFragment.sharedElementReturnTransition = TransitionInflater.from(presentingContext)
                    .inflateTransition(R.transition.image_view_transition_set)
            fromFragment.exitTransition = TransitionInflater.from(presentingContext)
                    .inflateTransition(android.R.transition.fade)

            toFragment.sharedElementEnterTransition = TransitionInflater.from(presentingContext)
                    .inflateTransition(R.transition.image_view_transition_set)
            toFragment.enterTransition = TransitionInflater.from(presentingContext)
                    .inflateTransition(android.R.transition.fade)

            sharedElement?.let {
                transaction.addSharedElement(it, sharedElementIdentifier)
            }
        } else {
            transaction.setCustomAnimations(
                    android.R.anim.fade_in,android.R.anim.fade_out)
        }

        return transaction
    }


}
