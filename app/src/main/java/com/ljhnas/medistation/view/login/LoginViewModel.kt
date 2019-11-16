package com.ljhnas.medistation.view.login

import android.content.Context
import android.content.Intent
import com.ljhnas.medistation.view.register.RegisterActivity

class LoginViewModel(private val context: Context) {
    fun onClickRegister() {
        context.startActivity(Intent(context, RegisterActivity::class.java))
    }
}