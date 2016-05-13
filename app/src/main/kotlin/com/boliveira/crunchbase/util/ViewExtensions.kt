package com.boliveira.crunchbase.util

import android.os.Build
import android.view.View

/**
 * Created by bruno on 13/05/16.
 */

fun View.setSafeTransitionName(name: String) {
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
        this.transitionName = name
    }
}
