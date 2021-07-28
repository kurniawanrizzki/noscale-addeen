package com.advotics.addeen.data.source.remote

import com.advotics.addeen.data.source.remote.admin.AdminApi
import com.advotics.addeen.data.source.remote.login.LoginApi
import com.advotics.addeen.data.source.remote.recipient.RecipientApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIService {

    private val BASE_URL = "http://34.101.167.168/qris-qurban-service/"

    val mLoginApi: LoginApi

    val mAdminApi: AdminApi

    val mRecipientApi: RecipientApi

    init {
        val client = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mLoginApi = client.create(LoginApi::class.java)
        mAdminApi = client.create(AdminApi::class.java)
        mRecipientApi = client.create(RecipientApi::class.java)
    }

    companion object {
        private var INSTANCE: APIService? = null

        fun getInstance (): APIService? {
            synchronized(lock = Any()) {
                INSTANCE = INSTANCE ?: APIService()
            }

            return INSTANCE
        }
    }
}