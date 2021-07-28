package com.advotics.addeen.data.source.remote.recipient

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface RecipientApi {
    @GET("recipients")
    fun getRecipients (@Query("page") pageNumber: Int, @Query("size") pageSize: Int, @Query("sort") sort: String, @Query("year") year: Int?): Call<RecipientListResponse>
}