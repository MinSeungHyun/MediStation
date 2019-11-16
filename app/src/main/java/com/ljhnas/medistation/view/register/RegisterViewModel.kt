package com.ljhnas.medistation.view.register

import android.app.Activity
import android.widget.Toast
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.ljhnas.medistation.R
import com.ljhnas.medistation.model.RequestSuccess
import com.ljhnas.medistation.util.BASE_URL
import com.ljhnas.medistation.util.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterViewModel(private val context: Activity) {
    val email: ObservableField<String> by lazy { ObservableField<String>() }
    val password: ObservableField<String> by lazy { ObservableField<String>() }
    val name: ObservableField<String> by lazy { ObservableField<String>() }
    val birthString: ObservableField<String> by lazy { ObservableField<String>() }
    val isFemale: ObservableBoolean by lazy { ObservableBoolean().apply { set(false) } }
    val isPregnant: ObservableArrayList<Boolean> by lazy {
        ObservableArrayList<Boolean>().apply {
            add(true) //no
            add(false) //pregnant
            add(false) //lactating
        }
    }
    val diseases: ObservableArrayList<Boolean> by lazy {
        ObservableArrayList<Boolean>().apply {
            add(true) //no
            add(false) //고혈압
            add(false) //당뇨
        }
    }
    val takingMedicines: ObservableArrayList<Boolean> by lazy {
        ObservableArrayList<Boolean>().apply {
            add(true) //no
            add(false) //당뇨약
            add(false) //심작약
        }
    }

    val isInvalid: ObservableBoolean by lazy { ObservableBoolean().apply { set(false) } }
    val currentStage: ObservableInt by lazy { ObservableInt().apply { set(0) } }

    private val retrofitService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)
    }

    fun onNextButtonClick() {
        val email = email.get()
        val password = password.get()
        val name = name.get()
        val birth = birthString.get()
        if (email.isNullOrBlank() || password.isNullOrBlank() || name.isNullOrBlank() || birth.isNullOrBlank()) {
            isInvalid.set(true)
            return
        }
        isInvalid.set(false)

        if (currentStage.get() == 4) {
            val gender = if (isFemale.get()) "1" else "0"
            val isPregnant = !isPregnant[0]
            val allergy = ArrayList<String>()
            if (diseases[1]) allergy.add("고혈압")
            if (diseases[2]) allergy.add("당뇨")
            val medicine = ArrayList<String>()
            if (takingMedicines[1]) medicine.add("당뇨약")
            if (takingMedicines[2]) medicine.add("심장약")

            val requestModel = UserRegisterModel(email, password, name, birth, allergy, medicine, gender, isPregnant)
            retrofitService.requestRegister(requestModel)
                .enqueue(object : Callback<RequestSuccess> {
                    override fun onFailure(call: Call<RequestSuccess>, t: Throwable) {
                        t.printStackTrace()
                        Toast.makeText(context, R.string.request_failed, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<RequestSuccess>, response: Response<RequestSuccess>) {
                        if (response.isSuccessful) {
                            Toast.makeText(context, R.string.registered, Toast.LENGTH_LONG).show()
                            context.finish()
                        } else {
                            Toast.makeText(context, R.string.request_failed, Toast.LENGTH_LONG).show()
                        }
                    }
                })
        } else {
            currentStage.set(currentStage.get() + 1)
        }
    }
}