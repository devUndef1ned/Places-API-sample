package com.devundefined.placesapisample

import android.view.View

fun View.switchVisibility(shouldBeVisible: Boolean) {
    if (visibility != visibility(shouldBeVisible)) {
        visibility = visibility(shouldBeVisible)
    }
}

private fun View.visibility(shouldBeVisible: Boolean): Int {
    return if (shouldBeVisible) View.VISIBLE else View.GONE
}