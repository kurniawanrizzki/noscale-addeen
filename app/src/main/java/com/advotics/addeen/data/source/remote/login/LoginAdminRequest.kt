package com.advotics.addeen.data.source.remote.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginAdminRequest (
    @SerializedName("adminEmail")
    @Expose
    val email: String,

    @SerializedName("adminPassword")
    @Expose
    val password: String
)