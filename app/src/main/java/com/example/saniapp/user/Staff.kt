package com.example.saniapp.user

import java.io.Serializable

data class Staff(
    var birthdate: String?,
    var email: String?,
    var gender: String?,
    var name: String?,
    var phone: String?,
    var surnames: String?,
    var id: String?
): Serializable
