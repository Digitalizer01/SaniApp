package com.example.saniapp.resident

import java.io.Serializable

data class ResidentMedication(
    var day: String?,

    var morning_hour: String?,
    var morning_medication: String?,
    var morning_taken: String?,

    var afternoon_hour: String?,
    var afternoon_medication: String?,
    var afternoon_taken: String?,

    var evening_hour: String?,
    var evening_medication: String?,
    var evening_taken: String?,

    var night_hour: String?,
    var night_medication: String?,
    var night_taken: String?
): Serializable
