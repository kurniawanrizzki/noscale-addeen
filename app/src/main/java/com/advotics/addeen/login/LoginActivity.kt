package com.advotics.addeen.login

import android.os.Bundle
import com.advotics.addeen.R
import com.noscale.cerberus.base.BaseActivity

class LoginActivity: BaseActivity<LoginContract.View, LoginContract.Presenter>() {
    override var mIllustrationDescription: Int? = R.string.progress_description

    override var mIllustrationSrc: Int? = R.raw.ic_progress

    override var mIllustrationTitle: Int? = R.string.progress_title

    override var mView: LoginContract.View? = LoginFragment.newInstance()

    override var mPresenter: LoginContract.Presenter? = LoginPresenter(mView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showIllustration = false
    }
}