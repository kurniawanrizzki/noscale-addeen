package com.advotics.addeen.data.source.remote.creation

import com.advotics.addeen.data.Admin
import com.advotics.addeen.data.Recipient
import com.advotics.addeen.data.source.CreationDataSource
import com.advotics.addeen.data.source.remote.APIService
import com.advotics.addeen.utils.ErrorCode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreationRemoteDataSource: CreationDataSource {
    override fun onAdminRegis(
        name: String?,
        email: String?,
        password: String?,
        callback: CreationDataSource.LoadCreationCallback<Admin>
    ) {
        val request = CreationAdminRequest(name, email, password)
        val response = APIService.getInstance()?.mCreationApi?.onAdminRegis(request)
        response?.enqueue(object: Callback<Admin> {
            override fun onResponse(call: Call<Admin>, response: Response<Admin>) {
                val admin = response.body()

                admin?.let {
                    callback.onSuccessCallback(admin)
                    return
                }

                callback.onErrorCallback(ErrorCode.INTERNAL_SERVER_ERROR)
            }

            override fun onFailure(call: Call<Admin>, t: Throwable) {
                callback.onErrorCallback(ErrorCode.STATUS_NO_CONNECTION)
            }

        })
    }

    override fun onRecipientRegis(
        adminId: Int,
        photo: String?,
        name: String?,
        email: String?,
        phone: String?,
        gender: String?,
        callback: CreationDataSource.LoadCreationCallback<Recipient>
    ) {
        val request = CreationRecipientRequest(adminId, photo, name, email, phone, gender)
        val response = APIService.getInstance()?.mCreationApi?.onRecipientRegis(request)
        response?.enqueue(object: Callback<Recipient> {
            override fun onResponse(call: Call<Recipient>, response: Response<Recipient>) {
                val recipient = response.body()

                recipient?.let {
                    callback.onSuccessCallback(recipient)
                    return
                }

                callback.onErrorCallback(ErrorCode.INTERNAL_SERVER_ERROR)
            }

            override fun onFailure(call: Call<Recipient>, t: Throwable) {
                callback.onErrorCallback(ErrorCode.STATUS_NO_CONNECTION)
            }

        })
    }

    companion object {
        fun getInstance(): CreationRemoteDataSource = CreationRemoteDataSource()
    }
}