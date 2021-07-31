package com.advotics.addeen.data.source.remote.recipient

import com.advotics.addeen.data.Recipient
import com.advotics.addeen.data.RecipientPackage
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query


interface RecipientApi {
    @GET("recipients")
    fun getRecipients (@Query("page") pageNumber: Int?, @Query("size") pageSize: Int?, @Query("sort") sort: String, @Query("year") year: Int?): Call<RecipientListResponse>

    @PUT("receive-package")
    fun redeem (@Body request: RecipientRedemptionRequest): Call<RecipientPackage>
}