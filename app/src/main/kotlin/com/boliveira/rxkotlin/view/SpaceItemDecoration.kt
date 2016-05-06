package com.boliveira.rxkotlin.view

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View

class SpaceItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    private var mShowFirstDivider = false
    private var mShowLastDivider = false
    internal var mOrientation = -1

    constructor(ctx: Context, resId: Int) : this(ctx.resources.getDimensionPixelSize(resId)) {}

    private constructor(context: Context, attrs: AttributeSet) : this(0) { }

    private constructor(context: Context, attrs: AttributeSet, showFirstDivider: Boolean,
                        showLastDivider: Boolean) : this(context, attrs) {
        mShowFirstDivider = showFirstDivider
        mShowLastDivider = showLastDivider
    }

    constructor(spaceInPx: Int, showFirstDivider: Boolean,
                showLastDivider: Boolean) : this(spaceInPx) {
        mShowFirstDivider = showFirstDivider
        mShowLastDivider = showLastDivider
    }

    private constructor(ctx: Context, resId: Int, showFirstDivider: Boolean,
                        showLastDivider: Boolean) : this(ctx, resId) {
        mShowFirstDivider = showFirstDivider
        mShowLastDivider = showLastDivider
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State?) {
        if (space == 0) {
            return
        }

        if (mOrientation == -1)
            getOrientation(parent)

        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION || position == 0 && !mShowFirstDivider) {
            return
        }

        if (mOrientation == LinearLayoutManager.VERTICAL) {
            outRect.top = space
            if (mShowLastDivider && position == state!!.itemCount - 1) {
                outRect.bottom = outRect.top
            }
        } else {
            outRect.left = space
            if (mShowLastDivider && position == state!!.itemCount - 1) {
                outRect.right = outRect.left
            }
        }
    }

    private fun getOrientation(parent: RecyclerView): Int {
        if (mOrientation == -1) {
            if (parent.layoutManager is LinearLayoutManager) {
                val layoutManager = parent.layoutManager as LinearLayoutManager
                mOrientation = layoutManager.orientation
            } else {
                throw IllegalStateException(
                        "DividerItemDecoration can only be used with a LinearLayoutManager.")
            }
        }
        return mOrientation
    }
}
