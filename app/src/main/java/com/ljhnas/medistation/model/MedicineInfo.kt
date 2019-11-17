package com.ljhnas.medistation.model

data class MedicineInfo(val id: String, val name: String, val shape: String,
                        val company: String, val store: String, val symptom: ArrayList<MedicineSymptomName>,
                        val takeInfo: ArrayList<MedicineTakeInfo>, val how: ArrayList<MedicineHow>,
                        val caution: ArrayList<MedicineCaution>)