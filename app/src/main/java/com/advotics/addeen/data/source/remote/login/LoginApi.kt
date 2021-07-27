package com.advotics.addeen.data.source.remote.login

import com.advotics.addeen.data.Admin
import com.advotics.addeen.data.Recipient
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface LoginApi {
    @POST ("recipient-login")
    fun recipientLogin (@Body request: LoginRecipientRequest): Call<Recipient>

    @POST ("admin-login")
    fun adminLogin (@Body request: LoginAdminRequest): Call<Admin>
}