package com.advotics.addeen.data.source.remote.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginRecipientRequest (
    @SerializedName("recipientEmail")
    @Expose
    val email: String,

    @SerializedName("recipientPassword")
    @Expose
    val password: String
)