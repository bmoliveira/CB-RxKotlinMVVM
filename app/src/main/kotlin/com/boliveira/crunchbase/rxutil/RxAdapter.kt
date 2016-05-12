package com.boliveira.crunchbase.rxutil

import android.support.v7.widget.RecyclerView
import com.boliveira.crunchbase.util.LogE
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers

//RxAdapter provides a Recyclerview adapter that is possible to bind to an observable array of items
abstract class RxRecyclerViewAdapter<SourceObject, ViewHolder: RecyclerView.ViewHolder> : RecyclerView.Adapter<ViewHolder>() {
    private var valuesObservable: Observable<Array<SourceObject>?>? = null
    private var valuesSubscription: rx.Subscription? = null
    private var values: Array<SourceObject> = createEmptyArray()

    lateinit private var valuesSubscriber: Subscriber<Array<SourceObject>>

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

        valuesSubscriber = generateValueSubscriber()
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

    private fun generateValueSubscriber() = object : Subscriber<Array<SourceObject>>() {
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
}

//Helper to create empty array of T items
private fun <V> createEmptyArray(): Array<V> {
    return arrayOfNulls<Any>(0) as Array<V>
}