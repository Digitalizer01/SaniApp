package com.example.saniapp.resident

import java.io.Serializable

data class Resident(
    var birthdate: String?,
    var gender: String?,
    var name: String?,
    var surnames: String?,
    var id: String?,
    var alert: Boolean?
): Serializable
