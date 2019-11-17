package com.ljhnas.medistation.view.search_progress.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ljhnas.medistation.R
import kotlinx.android.synthetic.main.activity_result_negative.*

class ResultNegativeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_negative)

        goBackButton.setOnClickListener {
            startActivity(Intent(this, SearchProgressActivity::class.java))
            finish()
        }
        homeButton.setOnClickListener {
            finish()
        }
    }
}