package com.advotics.addeen.create

import com.noscale.cerberus.base.BasePresenter
import com.noscale.cerberus.base.BaseView

interface CreationContract {
    interface View: BaseView<Presenter> {
        fun onCreationSuccess ()
        fun throwError (message: String)
    }

    interface Presenter: BasePresenter {
        fun submitAdminRegis (name: String?, email: String?, password: String?)
        fun submitRecipientRegis (adminId: Int, photo: String?, name: String?, email: String?, phone: String?, gender: String?)
    }
}