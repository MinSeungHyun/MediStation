package com.ljhnas.medistation.view.register

import android.app.Activity
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField

class RegisterViewModel(private val context: Activity) {
    val email: ObservableField<String> by lazy { ObservableField<String>() }
    val password: ObservableField<String> by lazy { ObservableField<String>() }
    val name: ObservableField<String> by lazy { ObservableField<String>() }
    val birthString: ObservableField<String> by lazy { ObservableField<String>() }
    val isInvalid: ObservableBoolean by lazy { ObservableBoolean().apply { set(false) } }

    fun onRegisterButtonClick() {
        val email = email.get()
        val password = password.get()
        val name = name.get()
        val birth = birthString.get()
        if (email.isNullOrBlank() || password.isNullOrBlank() || name.isNullOrBlank() || birth.isNullOrBlank()) {
            isInvalid.set(true)
            return
        }
        isInvalid.set(false)
    }
}