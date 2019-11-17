package com.ljhnas.medistation.view.search_progress.models

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableInt
import com.ljhnas.medistation.R
import com.ljhnas.medistation.util.BASE_URL
import com.ljhnas.medistation.util.RetrofitService
import com.ljhnas.medistation.view.search_progress.activities.ResultNegativeActivity
import com.ljhnas.medistation.view.search_progress.activities.ResultPositiveActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchProgressViewModel(private val context: Activity) {
    val progress by lazy { ObservableInt().apply { set(0) } }
    private val retrofitService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)
    }

    fun onClickNextButton() {
        if (progress.get() == 0) {
            progress.set(1)
            return
        }
    }

    fun requestNfcUid(uid: String) {
        val id = context.getPreferences(Context.MODE_PRIVATE).getString("id", "")!!
        retrofitService.requestNfc(hashMapOf("id" to id, "uid" to uid))
            .enqueue(object : Callback<SearchResult> {
                override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(context, R.string.request_failed, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                    val body = response.body()!!
                    if (body.success) {
                        Log.d("testing", "${body.eatable}, ${body.name}")
                        val intent =
                            if (body.eatable) Intent(context, ResultPositiveActivity::class.java).apply {
                                putExtra("medicineName", body.name)
                            }
                            else Intent(context, ResultNegativeActivity::class.java)
                        context.startActivity(intent)
                        context.finish()
                    } else {
                        Toast.makeText(context, R.string.request_failed, Toast.LENGTH_LONG).show()
                    }
                }
            })
    }
}