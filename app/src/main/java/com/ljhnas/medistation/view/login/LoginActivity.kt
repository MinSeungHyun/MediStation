package com.ljhnas.medistation.view.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ljhnas.medistation.R
import com.ljhnas.medistation.databinding.ActivityLoginBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = LoginViewModel(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.vm = viewModel
    }
}
