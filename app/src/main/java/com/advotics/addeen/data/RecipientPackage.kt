package com.advotics.addeen.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RecipientPackage(
    @SerializedName("packageId")
    @Expose
    val id: Int,

    @SerializedName("qrName")
    @Expose
    val qrName: String?,

    @SerializedName("qrSentStatus")
    @Expose
    val qrSentStatus: Boolean,

    @SerializedName("packageReceivedStatus")
    @Expose
    val packageReceivedStatus: Boolean,

    @SerializedName("createdAt")
    @Expose
    val createdAt: String,

    @SerializedName("updatedAt")
    @Expose
    val updatedAt: String
)
