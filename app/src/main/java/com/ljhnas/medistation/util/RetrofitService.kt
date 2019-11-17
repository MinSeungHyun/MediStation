package com.ljhnas.medistation.util

import com.ljhnas.medistation.model.RequestSuccess
import com.ljhnas.medistation.view.register.UserRegisterModel
import com.ljhnas.medistation.view.search_progress.models.SearchResult
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

const val BASE_URL = "http://medistation.ljhnas.com"

interface RetrofitService {
    @POST("/user/register")
    fun requestRegister(@Body param: UserRegisterModel): Call<RequestSuccess>

    @POST("/user/login")
    fun login(@Body param: HashMap<String, String>): Call<RequestSuccess>

    @POST("/berry/send")
    fun requestNfc(@Body param: HashMap<String, String>): Call<SearchResult>
}