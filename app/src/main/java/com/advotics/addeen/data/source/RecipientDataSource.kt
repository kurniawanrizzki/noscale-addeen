package com.advotics.addeen.data.source

import com.advotics.addeen.data.Recipient
import com.advotics.addeen.data.RecipientPackage
import com.advotics.addeen.utils.ErrorCode

interface RecipientDataSource {
    interface RecipientListCallback {
        fun onLoadCallback (data: List<Recipient>)
        fun onErrorCallback (e: ErrorCode)
    }

    interface RedeemCallback {
        fun onRedeemSuccess (r: RecipientPackage)
        fun onErrorCallback (e: ErrorCode)
    }

    fun getRecipientList (pageNumber: Int?, pageSize: Int?, sort: String?, year: Int?, callback: RecipientListCallback)

    fun redeem (email: String, phone: String, year: String, callback: RedeemCallback)
}