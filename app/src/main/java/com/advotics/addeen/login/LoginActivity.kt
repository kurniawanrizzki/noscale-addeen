package com.advotics.addeen.login

import android.os.Bundle
import com.noscale.cerberus.base.BaseActivity

class LoginActivity: BaseActivity<LoginContract.View, LoginContract.Presenter>() {
    override var mIllustrationDescription: Int? = null

    override var mIllustrationSrc: Int? = null

    override var mIllustrationTitle: Int? = null

    override var mView: LoginContract.View? = LoginFragment.newInstance()

    override var mPresenter: LoginContract.Presenter? = LoginPresenter(mView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showIllustration(false)
    }
}