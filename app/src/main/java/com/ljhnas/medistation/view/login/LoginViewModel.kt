package com.ljhnas.medistation.view.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.databinding.ObservableField
import com.ljhnas.medistation.R
import com.ljhnas.medistation.model.RequestSuccess
import com.ljhnas.medistation.util.BASE_URL
import com.ljhnas.medistation.util.RetrofitService
import com.ljhnas.medistation.view.main.MainActivity
import com.ljhnas.medistation.view.register.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginViewModel(private val context: Activity) {
    val email by lazy { ObservableField<String>().apply { set("") } }
    val password by lazy { ObservableField<String>().apply { set("") } }

    init {
        val preference = context.getPreferences(Context.MODE_PRIVATE)
        email.set(preference.getString("id", ""))
        password.set(preference.getString("pw", ""))
    }

    private val retrofitService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)
    }

    fun onClickRegister() {
        context.startActivity(Intent(context, RegisterActivity::class.java))
    }

    fun onClickLogin() {
        retrofitService.login(hashMapOf("id" to email.get()!!, "password" to password.get()!!))
            .enqueue(object : Callback<RequestSuccess> {
                override fun onFailure(call: Call<RequestSuccess>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(context, R.string.request_failed, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<RequestSuccess>, response: Response<RequestSuccess>) {
                    if (response.body()?.success == true) {
                        saveIdPw()
                        context.startActivity(Intent(context, MainActivity::class.java))
                        context.finish()
                    } else {
                        Toast.makeText(context, R.string.request_failed, Toast.LENGTH_LONG).show()
                    }
                }
            })
    }

    private fun saveIdPw() =
        with(context.getPreferences(Context.MODE_PRIVATE).edit()) {
            putString("id", email.get())
            putString("pw", password.get())
            apply()
        }
}
