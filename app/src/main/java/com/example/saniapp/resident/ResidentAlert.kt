package com.example.saniapp.resident

import java.io.Serializable

data class ResidentAlert(
    var resident: Resident?,
    var monday: Boolean?,
    var tuesday: Boolean?,
    var wednesday: Boolean?,
    var thursday: Boolean?,
    var friday: Boolean?,
    var saturday: Boolean?,
    var sunday: Boolean?
): Serializable
