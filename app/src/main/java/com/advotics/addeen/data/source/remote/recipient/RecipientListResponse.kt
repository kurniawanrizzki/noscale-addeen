package com.advotics.addeen.data.source.remote.recipient

import com.advotics.addeen.data.Recipient
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RecipientListResponse (
    @SerializedName("content")
    @Expose
    val data: List<Recipient>,

    @SerializedName("totalPages")
    @Expose
    val totalPages: Int,

    @SerializedName("totalElements")
    @Expose
    val totalElements: Int
)