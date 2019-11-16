package com.ljhnas.medistation.view.main

import android.content.Context
import android.content.Intent
import com.ljhnas.medistation.view.search_progress.activities.SearchProgressActivity

class MainActivityViewModel(private val context: Context) {
    fun onSearchButtonClick() {
        context.startActivity(Intent(context, SearchProgressActivity::class.java))
    }
}