package com.advotics.addeen.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class RecipientWrapper(
    @SerializedName("recipient")
    @Expose
    val recipient: Recipient,

    @SerializedName("totalRecipients")
    @Expose
    val totalRecipient: Int?,

    @SerializedName("totalRedeemed")
    @Expose
    val totalRedemption: Int?,

    @SerializedName("totalSent")
    @Expose
    val totalSent: Int?
): Parcelable