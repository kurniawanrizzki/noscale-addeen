package com.advotics.addeen.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Admin(
    @SerializedName("adminId")
    @Expose
    val id: Int,

    @SerializedName("adminName")
    @Expose
    val name: String?,

    @SerializedName("adminEmail")
    @Expose
    val email: String?,

    @SerializedName("adminPassword")
    @Expose
    val password: String?,

    @SerializedName("createdAt")
    @Expose
    val createdAt: String?,

    @SerializedName("updatedAt")
    @Expose
    val updatedAt: String?
)
