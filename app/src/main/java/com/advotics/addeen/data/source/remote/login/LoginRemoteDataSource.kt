package com.advotics.addeen.data.source.remote.login

import com.advotics.addeen.data.Admin
import com.advotics.addeen.data.RecipientWrapper
import com.advotics.addeen.data.source.LoginDataSource
import com.advotics.addeen.data.source.remote.APIService
import com.advotics.addeen.utils.ErrorCode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRemoteDataSource: LoginDataSource {
    override fun recipientLogin(
        email: String,
        password: String,
        callback: LoginDataSource.LoginCallback<RecipientWrapper>
    ) {
        val request = LoginRecipientRequest(email, password)
        val response = APIService.getInstance()?.mLoginApi?.recipientLogin(request)
        response?.enqueue(object: Callback<RecipientWrapper> {
            override fun onResponse(call: Call<RecipientWrapper>, response: Response<RecipientWrapper>) {
                val recipient = response.body()
                recipient?.let {
                    callback.onLoginSuccess(it)
                    return
                }

                callback.onLoginFailure(ErrorCode.INTERNAL_SERVER_ERROR)
            }

            override fun onFailure(call: Call<RecipientWrapper>, t: Throwable) {
                callback.onLoginFailure(ErrorCode.STATUS_NO_CONNECTION)
            }

        })
    }

    override fun adminLogin(
        email: String,
        password: String,
        callback: LoginDataSource.LoginCallback<Admin>
    ) {
        val request = LoginAdminRequest(email, password)
        val response = APIService.getInstance()?.mLoginApi?.adminLogin(request)
        response?.enqueue(object: Callback<Admin> {
            override fun onResponse(call: Call<Admin>, response: Response<Admin>) {
                val admin = response.body()
                admin?.let {
                    callback.onLoginSuccess(it)
                    return
                }

                callback.onLoginFailure(ErrorCode.INTERNAL_SERVER_ERROR)
            }

            override fun onFailure(call: Call<Admin>, t: Throwable) {
                callback.onLoginFailure(ErrorCode.STATUS_NO_CONNECTION)
            }

        })
    }

    companion object {
        fun getInstance (): LoginRemoteDataSource = LoginRemoteDataSource()
    }
}