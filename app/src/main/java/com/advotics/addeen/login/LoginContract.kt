package com.advotics.addeen.login

import com.noscale.cerberus.base.BasePresenter
import com.noscale.cerberus.base.BaseView

interface LoginContract {
    interface View: BaseView<Presenter> {
        fun goToDashboard (src: String)
        fun throwError (message: String)
    }

    interface Presenter: BasePresenter {
        var loginAsAdmin: Boolean
        fun login (email: String, password: String)
    }
}