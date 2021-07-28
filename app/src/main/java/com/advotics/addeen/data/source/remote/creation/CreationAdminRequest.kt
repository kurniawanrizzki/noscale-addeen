package com.advotics.addeen.data.source.remote.creation

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CreationAdminRequest(
    @SerializedName("adminName")
    @Expose
    val name: String?,

    @SerializedName("adminEmail")
    @Expose
    val email: String?,

    @SerializedName("adminPassword")
    @Expose
    val password: String?
)