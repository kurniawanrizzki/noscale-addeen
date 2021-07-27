package com.advotics.addeen.login

import com.advotics.addeen.data.Admin
import com.advotics.addeen.data.Recipient
import com.advotics.addeen.data.source.LoginDataSource
import com.advotics.addeen.data.source.remote.login.LoginRemoteDataSource
import com.advotics.addeen.utils.AppHelper
import com.advotics.addeen.utils.ErrorCode

class LoginPresenter(private val mView: LoginContract.View?): LoginContract.Presenter {

    init {
        mView?.mPresenter = this
    }

    override var loginAsAdmin: Boolean = false

    override fun login(email: String, password: String) {

        if (loginAsAdmin) {
            LoginRemoteDataSource.getInstance().adminLogin(email, password, object: LoginDataSource.LoginCallback<Admin> {
                override fun onLoginSuccess(r: Admin) {
                    val result = AppHelper.toJson(r)
                    mView?.goToDashboard(result)
                }

                override fun onLoginFailure(code: ErrorCode) {
                    mView?.throwError(code.message)
                }
            })

            return
        }

        LoginRemoteDataSource.getInstance().recipientLogin(email, password, object: LoginDataSource.LoginCallback<Recipient> {
            override fun onLoginSuccess(r: Recipient) {
                val result = AppHelper.toJson(r)
                mView?.goToDashboard(result)
            }

            override fun onLoginFailure(code: ErrorCode) {
                mView?.throwError(code.message)
            }
        })
    }

    override fun start() {
    }
}