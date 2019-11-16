package com.ljhnas.medistation.view.search_progress

import android.os.Bundle
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ljhnas.medistation.R
import com.ljhnas.medistation.databinding.ActivitySearchProgressBinding
import kotlinx.android.synthetic.main.activity_search_progress.*

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

        val span = mainText.text as Spannable
        span.setSpan(ForegroundColorSpan(getColor(R.color.colorPrimary)), 15, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        mainText.text = span
        val span2 = mainText2.text as Spannable
        span2.setSpan(ForegroundColorSpan(getColor(R.color.colorPrimary)), 13, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        mainText2.text = span2
    }
}