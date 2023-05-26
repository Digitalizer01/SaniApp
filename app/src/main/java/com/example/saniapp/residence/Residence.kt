package com.example.saniapp.residence

import com.example.saniapp.resident.Resident
import com.example.saniapp.user.Staff
import java.io.Serializable

data class Residence(
    var city: String?,
    var country: String?,
    var email: String?,
    var name: String?,
    var phone: String?,
    var province: String?,
    var street: String?,
    var timetable: String?,
    var zc: String?,
    var id: String?
): Serializable
