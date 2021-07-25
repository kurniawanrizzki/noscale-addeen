package com.advotics.addeen.login

class LoginPresenter(private val mView: LoginContract.View?): LoginContract.Presenter {

    init {
        mView?.mPresenter = this
    }

    override fun start() {
    }
}