package com.ljhnas.medistation.util

import com.ljhnas.medistation.model.RequestSuccess
import com.ljhnas.medistation.view.register.UserRegisterModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

const val BASE_URL = "http://medistation.ljhnas.com"

interface RetrofitService {
    @POST("/user/register")
    fun requestRegister(@Body param: UserRegisterModel): Call<RequestSuccess>

    @POST("/user/login")
    fun login(@Body param: HashMap<String, String>): Call<RequestSuccess>
}