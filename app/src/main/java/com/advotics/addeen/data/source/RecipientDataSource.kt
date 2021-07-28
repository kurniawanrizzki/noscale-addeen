package com.advotics.addeen.data.source

import com.advotics.addeen.data.Recipient
import com.advotics.addeen.utils.ErrorCode

interface RecipientDataSource {
    interface RecipientListCallback {
        fun onLoadCallback (data: List<Recipient>)
        fun onErrorCallback (e: ErrorCode)
    }

    fun getRecipientList (pageNumber: Int, pageSize: Int, sort: String?, callback: RecipientListCallback)
}