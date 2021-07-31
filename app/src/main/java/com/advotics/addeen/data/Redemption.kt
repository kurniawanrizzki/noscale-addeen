package com.advotics.addeen.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Redemption(
    @SerializedName("phone")
    @Expose
    val phone: String?,

    @SerializedName("email")
    @Expose
    val email: String?,

    @SerializedName("year")
    @Expose
    val year: String?
)
