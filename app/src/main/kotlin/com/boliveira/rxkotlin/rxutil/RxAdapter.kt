package com.boliveira.rxkotlin.rxutil

import android.support.v7.widget.RecyclerView
import com.boliveira.rxkotlin.util.LogE
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers

abstract class RxRecyclerViewAdapter<SourceObject, ViewHolder: RecyclerView.ViewHolder> : RecyclerView.Adapter<ViewHolder>() {
    private var valuesObservable: Observable<Array<SourceObject>?>? = null
    private var valuesSubscription: rx.Subscription? = null
    private var values: Array<SourceObject> = createEmptyArray()

    private val valuesSubscriber = object : Subscriber<Array<SourceObject>>() {
        override fun onCompleted() {
            this@RxRecyclerViewAdapter.onComplete()
        }

        override fun onError(e: Throwable) {
            this@RxRecyclerViewAdapter.onError(e)
        }

        override fun onNext(t: Array<SourceObject>) {
            this@RxRecyclerViewAdapter.onNextArray(t)
            this@RxRecyclerViewAdapter.notifyDataSetChanged()
        }
    }

    protected fun onComplete() {
        LogE("onComplete")
    }

    protected fun onError(e: Throwable) {
        LogE("onError $e")
    }

    protected fun onNextArray(t: Array<SourceObject>?) {
        if (t == null) {
            values = createEmptyArray()
            return
        }
        t.let {  values = it }
        LogE("Items count: ${values.size}")
    }

    fun subscribe(observable: Observable<Array<SourceObject>?>): rx.Subscription? {
        if (valuesSubscription != null && !valuesSubscription!!.isUnsubscribed) {
            valuesSubscription!!.unsubscribe()
        }

        this.valuesObservable = bind(observable)

        valuesSubscription = bind(observable).subscribe(valuesSubscriber)

        if (!values.isEmpty()) {
            values = createEmptyArray()
            this.notifyDataSetChanged()
        }

        return valuesSubscription
    }

    private fun <T> bind(observable: Observable<Array<T>?>): Observable<Array<T>?> {
        return observable.observeOn(AndroidSchedulers.mainThread())
    }

    fun getItemAtIndex(position: Int): SourceObject {
        return values[position]
    }

    //OVERRIDES
    override fun getItemCount(): Int {
        return values.size
    }
}

private fun <V> createEmptyArray(): Array<V> {
    return arrayOfNulls<Any>(0) as Array<V>
}