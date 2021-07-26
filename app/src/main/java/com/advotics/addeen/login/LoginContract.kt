package com.advotics.addeen.login

import com.google.android.material.textfield.TextInputLayout
import com.noscale.cerberus.base.BasePresenter
import com.noscale.cerberus.base.BaseView

interface LoginContract {
    interface View: BaseView<Presenter> {
        fun isInputValidated (v: TextInputLayout): Boolean
        fun assignTextWatcherToInput (v: TextInputLayout)
        fun isEmailValidated (v: TextInputLayout): Boolean
    }

    interface Presenter: BasePresenter {

    }
}