package com.ljhnas.medistation.view.register

import android.content.Context
import androidx.databinding.ObservableField

class RegisterViewModel(private val context: Context) {
    val birthString: ObservableField<String> by lazy { ObservableField<String>() }
}