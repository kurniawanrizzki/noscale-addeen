package com.advotics.addeen.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Recipient(
    @SerializedName("recipientId")
    @Expose
    val id: Int,

    @SerializedName("recipientName")
    @Expose
    val name: String?,

    @SerializedName("recipientEmail")
    @Expose
    val email: String?,

    @SerializedName("recipientPassword")
    @Expose
    val password: String?,

    @SerializedName("recipientPhoto")
    @Expose
    val photo: String?,

    @SerializedName("recipientPhone")
    @Expose
    val phone: String?,

    @SerializedName("recipientGender")
    @Expose
    val gender: String?,

    @SerializedName("recipientYear")
    @Expose
    val year: String?,

    @SerializedName("recipientPackage")
    @Expose
    val recipientPackage: RecipientPackage?,

    @SerializedName("createdAt")
    @Expose
    val createdAt: String?,

    @SerializedName("updatedAt")
    @Expose
    val updatedAt: String?
): Parcelable {
    constructor() : this (0, "", "", "", "", "", "", "", null,"", "")
}
