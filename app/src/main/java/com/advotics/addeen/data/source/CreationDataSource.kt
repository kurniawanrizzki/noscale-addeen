package com.advotics.addeen.data.source

import com.advotics.addeen.data.Admin
import com.advotics.addeen.data.Recipient
import com.advotics.addeen.utils.ErrorCode


interface CreationDataSource {
    interface LoadCreationCallback<T> {
        fun onSuccessCallback (r: T)
        fun onErrorCallback (e: ErrorCode)
    }

    fun onAdminRegis (name: String?, email: String?, password: String?, callback: LoadCreationCallback<Admin>)

    fun onRecipientRegis (adminId: Int, photo: String?, name: String?, email: String?, phone: String?, gender: String?, callback: LoadCreationCallback<Recipient>)
}