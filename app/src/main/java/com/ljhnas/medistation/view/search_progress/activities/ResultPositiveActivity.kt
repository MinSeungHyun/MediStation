package com.ljhnas.medistation.view.search_progress.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ljhnas.medistation.R
import com.ljhnas.medistation.model.MedicineInfo
import com.ljhnas.medistation.util.BASE_URL
import com.ljhnas.medistation.util.RetrofitService
import kotlinx.android.synthetic.main.activity_result_negative.goBackButton
import kotlinx.android.synthetic.main.activity_result_negative.homeButton
import kotlinx.android.synthetic.main.activity_result_positive.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ResultPositiveActivity : AppCompatActivity() {
    private val retrofitService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_positive)

        goBackButton.setOnClickListener {
            startActivity(Intent(this, SearchProgressActivity::class.java))
            finish()
        }
        homeButton.setOnClickListener {
            finish()
        }

        showMedicineInfoButton.setOnClickListener {
            showMedicineInfoButton()
        }

        val medicineID = intent.getStringExtra("medicineID") ?: return
        Log.d("testing", medicineID)
        retrofitService.getMedicineInfo(medicineID)
            .enqueue(object : Callback<MedicineInfo> {
                override fun onFailure(call: Call<MedicineInfo>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<MedicineInfo>, response: Response<MedicineInfo>) {
                    val info = response.body() ?: return
                    medicineName.text = info.name
                    company.text = info.company
                    protectText.text = info.store

                    val symptomString = StringBuilder()
                    info.symptom.forEachIndexed { index, medicineSymptomName ->
                        symptomString.append(medicineSymptomName.name)
                        if (index != info.symptom.size - 1) symptomString.append(", ")
                    }
                    symptomText.text = symptomString

                    takeInfoText.text = "성인\n하루 %s번, 한번에 %s알씩".format(info.how[0].timeNumber, info.how[0].eatNumber)

                    val howTakeString = StringBuilder()
                    info.takeInfo.forEachIndexed { index, takeInfo ->
                        howTakeString.append("%d. %s\n".format(index + 1, takeInfo.info))
                    }
                    cautionText.text = howTakeString
                }
            })
    }

    private fun showMedicineInfoButton() {
        safeLayout.visibility = View.GONE
        infoCardView.visibility = View.VISIBLE
    }
}