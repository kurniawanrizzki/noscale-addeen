package com.advotics.addeen.data.source.remote.creation

import com.advotics.addeen.data.Admin
import com.advotics.addeen.data.Recipient
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CreationApi {
    @POST("admin")
    fun onAdminRegis (@Body request: CreationAdminRequest): Call<Admin>

    @POST("recipient")
    fun onRecipientRegis (@Body request: CreationRecipientRequest): Call<Recipient>
}