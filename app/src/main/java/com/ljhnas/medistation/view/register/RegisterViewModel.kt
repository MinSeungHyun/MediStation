package com.ljhnas.medistation.view.register

import android.app.Activity
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt

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
            val isFemale = if (isFemale.get()) "1" else "0"
            val isPregnant = !isPregnant[0]
            val allergy = ArrayList<String>()
            if (diseases[1]) allergy.add("고혈압")
            if (diseases[2]) allergy.add("당뇨")
            val medicine = ArrayList<String>()
            if (takingMedicines[1]) medicine.add("당뇨약")
            if (takingMedicines[2]) medicine.add("심장약")
        } else {
            currentStage.set(currentStage.get() + 1)
        }
    }
}