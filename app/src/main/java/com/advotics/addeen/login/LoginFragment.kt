package com.advotics.addeen.login

import com.noscale.cerberus.base.BaseFragment

class LoginFragment: BaseFragment(), LoginContract.View {
    override var mPresenter: LoginContract.Presenter? = null

    companion object {
        fun newInstance(): LoginFragment = LoginFragment()
    }
}