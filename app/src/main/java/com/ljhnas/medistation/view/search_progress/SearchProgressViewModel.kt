package com.ljhnas.medistation.view.search_progress

import android.content.Context
import androidx.databinding.ObservableInt

class SearchProgressViewModel(private val context: Context) {
    val progress by lazy { ObservableInt().apply { set(0) } }

    fun onClickNextButton() {
        if (progress.get() == 0) {
            progress.set(1)
            return
        }
    }
}