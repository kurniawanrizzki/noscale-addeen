package com.advotics.addeen.data.source.remote.admin

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AdminApi {
    @GET("admins")
    fun getAdmins (@Query("page") pageNumber: Int, @Query("size") pageSize: Int, @Query("sort") sort: String): Call<AdminListResponse>
}