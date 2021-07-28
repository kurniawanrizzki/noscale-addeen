package com.advotics.addeen.data.source.remote.creation

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class CreationRecipientRequest(
    @SerializedName("adminId")
    @Expose
    val adminId: Int,

    @SerializedName("recipientPhoto")
    @Expose
    val photo: String?,

    @SerializedName("recipientName")
    @Expose
    val name: String?,

    @SerializedName("recipientEmail")
    @Expose
    val email: String?,

    @SerializedName("recipientPhone")
    @Expose
    val phone: String?,

    @SerializedName("recipientGender")
    @Expose
    val gender: String?,

    @SerializedName("recipientYear")
    @Expose
    val year: String = SimpleDateFormat("YYYY").format(Date())
)
