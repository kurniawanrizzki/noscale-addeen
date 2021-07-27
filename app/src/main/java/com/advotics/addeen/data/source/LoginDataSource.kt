package com.advotics.addeen.data.source

import com.advotics.addeen.data.Admin
import com.advotics.addeen.data.Recipient
import com.advotics.addeen.utils.ErrorCode

interface LoginDataSource {

    interface LoginCallback<T> {
        fun onLoginSuccess (r: T)
        fun onLoginFailure (code: ErrorCode)
    }

    fun recipientLogin (email: String, password: String, callback: LoginCallback<Recipient>)

    fun adminLogin (email: String, password: String, callback: LoginCallback<Admin>)
}