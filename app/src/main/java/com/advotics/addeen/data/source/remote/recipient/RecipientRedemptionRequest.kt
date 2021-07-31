package com.advotics.addeen.data.source.remote.recipient

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RecipientRedemptionRequest(
    @SerializedName("recipientEmail")
    @Expose
    val email: String,

    @SerializedName("recipientPhone")
    @Expose
    val phone: String,

    @SerializedName("recipientYear")
    @Expose
    val year: String
)