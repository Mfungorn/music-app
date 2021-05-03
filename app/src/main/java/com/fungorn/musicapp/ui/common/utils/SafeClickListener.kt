package com.fungorn.musicapp.ui.common.utils

import android.os.SystemClock
import android.view.View

class SafeClickListener(
    private var defaultInterval: Int = 1000,
    private val onSafeCLick: (View) -> Unit
) : View.OnClickListener {
    private var lastTimeClicked: Long = 0

    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) return

        lastTimeClicked = SystemClock.elapsedRealtime()

        onSafeCLick(v)
    }
}

fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    setOnClickListener(SafeClickListener {
        onSafeClick(it)
    })
}

fun View.setSafeOnClickListener(interval: Int, onSafeClick: (View) -> Unit) {
    setOnClickListener(SafeClickListener(interval) {
        onSafeClick(it)
    })
}
