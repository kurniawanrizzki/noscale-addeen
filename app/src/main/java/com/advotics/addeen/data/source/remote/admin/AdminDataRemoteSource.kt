package com.advotics.addeen.data.source.remote.admin

import com.advotics.addeen.data.source.AdminDataSource
import com.advotics.addeen.data.source.remote.APIService
import com.advotics.addeen.utils.ErrorCode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminDataRemoteSource: AdminDataSource {

    override fun getAdminList(
        pageNumber: Int,
        pageSize: Int,
        callback: AdminDataSource.AdminListCallback
    ) {
        val sort = "adminId,desc"
        val response = APIService.getInstance()?.mAdminApi?.getAdmins(pageNumber, pageSize, sort)
        response?.enqueue(object: Callback<AdminListResponse> {
            override fun onResponse(
                call: Call<AdminListResponse>,
                response: Response<AdminListResponse>
            ) {
                val adminList = response.body()?.data

                adminList?.let {
                    callback.onLoadCallback(it)
                    return
                }

                callback.onErrorCallback(ErrorCode.INTERNAL_SERVER_ERROR)
            }

            override fun onFailure(call: Call<AdminListResponse>, t: Throwable) {
                callback.onErrorCallback(ErrorCode.STATUS_NO_CONNECTION)
            }

        })
    }

    companion object {
        fun getInstance(): AdminDataRemoteSource = AdminDataRemoteSource()
    }
}