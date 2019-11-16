package com.ljhnas.medistation.view.search_progress

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ljhnas.medistation.R
import com.ljhnas.medistation.databinding.ActivitySearchProgressBinding

class SearchProgressActivity : AppCompatActivity() {
    private val viewModel by lazy { SearchProgressViewModel(this) }
    private lateinit var binding: ActivitySearchProgressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_progress)
        binding.apply {
            vm = viewModel
            activity = this@SearchProgressActivity
        }
    }
}