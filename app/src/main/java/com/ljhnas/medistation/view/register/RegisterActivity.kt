package com.ljhnas.medistation.view.register

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ljhnas.medistation.R
import com.ljhnas.medistation.databinding.ActivityRegisterBinding
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = RegisterViewModel(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.vm = viewModel

        birthEditText.setOnFocusChangeListener { _, hasFocus ->
            birthEditText.clearFocus()
            if (!hasFocus) return@setOnFocusChangeListener
            DatePickerDialog(this@RegisterActivity).apply {
                setOnDateSetListener { _, year, month, dayOfMonth ->
                    viewModel.birthString.set(getString(R.string.birth_text_format).format(year, month, dayOfMonth))
                }
                show()
            }
        }
    }
}
