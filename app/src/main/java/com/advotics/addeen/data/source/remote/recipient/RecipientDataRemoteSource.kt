package com.advotics.addeen.data.source.remote.recipient

import com.advotics.addeen.data.Recipient
import com.advotics.addeen.data.RecipientPackage
import com.advotics.addeen.data.source.RecipientDataSource
import com.advotics.addeen.data.source.remote.APIService
import com.advotics.addeen.utils.ErrorCode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipientDataRemoteSource: RecipientDataSource {
    override fun getRecipientList(
        pageNumber: Int?,
        pageSize: Int?,
        sort: String?,
        year: Int?,
        callback: RecipientDataSource.RecipientListCallback
    ) {
        val fixSort = sort ?: "recipientYear"
        val response = APIService.getInstance()?.mRecipientApi?.getRecipients(pageNumber, pageSize, fixSort, year)
        response?.enqueue(object: Callback<RecipientListResponse> {
            override fun onResponse(
                call: Call<RecipientListResponse>,
                response: Response<RecipientListResponse>
            ) {
                val recipientList = response.body()?.data

                recipientList?.let {
                    callback.onLoadCallback(it)
                    return
                }

                callback.onErrorCallback(ErrorCode.INTERNAL_SERVER_ERROR)
            }

            override fun onFailure(call: Call<RecipientListResponse>, t: Throwable) {
                callback.onErrorCallback(ErrorCode.STATUS_NO_CONNECTION)
            }

        })
    }

    override fun redeem(
        email: String,
        phone: String,
        year: String,
        callback: RecipientDataSource.RedeemCallback
    ) {
        val request = RecipientRedemptionRequest(email, phone, year)
        val response = APIService.getInstance()?.mRecipientApi?.redeem(request)
        response?.enqueue(object: Callback<RecipientPackage> {
            override fun onResponse(call: Call<RecipientPackage>, response: Response<RecipientPackage>) {
                val result = response.body()

                result?.let {
                    callback.onRedeemSuccess(it)
                    return
                }

                callback.onErrorCallback(ErrorCode.NO_RESULTS)
            }

            override fun onFailure(call: Call<RecipientPackage>, t: Throwable) {
                callback.onErrorCallback(ErrorCode.INTERNAL_SERVER_ERROR)
            }

        })
    }

    companion object {
        fun getInstance(): RecipientDataRemoteSource = RecipientDataRemoteSource()
    }
}