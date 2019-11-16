package com.ljhnas.medistation.view.register

import android.app.Activity
import android.util.Log
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

private val diseasesList = listOf(
    "없음", //0
    "고혈압",
    "당뇨",
    "고지혈증",
    "신장질환",
    "간질환",
    "암",
    "비뇨기/산부인과",
    "정신질환",
    "기타" //9
)

private val medicineList = listOf(
    "없음", //0
    "진통제",
    "감기약",
    "항생제",
    "위장약",
    "혈압약",
    "당뇨약",
    "골다공중약",
    "수면제",
    "빈혈제",
    "기타" //10
)

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
            add(true)
            repeat(9) {
                add(false)
            }
        }
    }
    val takingMedicines: ObservableArrayList<Boolean> by lazy {
        ObservableArrayList<Boolean>().apply {
            add(true)
            repeat(10) {
                add(false)
            }
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
            diseases.forEachIndexed { index, b ->
                if (!(index == 0 || index == 9) && b) {
                    allergy.add(diseasesList[index])
                }
            }
            val medicine = ArrayList<String>()
            takingMedicines.forEachIndexed { index, b ->
                if (!(index == 0 || index == 10) && b) {
                    medicine.add(medicineList[index])
                }
            }

            Log.d("testing", allergy.toString())
            Log.d("testing", medicine.toString())

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