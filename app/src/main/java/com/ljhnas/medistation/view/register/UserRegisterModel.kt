package com.ljhnas.medistation.view.register

data class UserRegisterModel(val id: String, val password: String, val name: String, val birth: String,
                             val allergy: ArrayList<String>, val medicine: ArrayList<String>, val gender: String, val pregnant: Boolean)