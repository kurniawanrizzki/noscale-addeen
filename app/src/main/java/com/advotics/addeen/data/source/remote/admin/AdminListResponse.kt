package com.advotics.addeen.data.source.remote.admin

import com.advotics.addeen.data.Admin
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AdminListResponse(
    @SerializedName("content")
    @Expose
    val data: List<Admin>?,

    @SerializedName("totalPages")
    @Expose
    val totalPages: Int,

    @SerializedName("totalElements")
    @Expose
    val totalElements: Int
)
