package com.advotics.addeen.user

import com.advotics.addeen.data.Admin
import com.noscale.cerberus.base.BasePresenter
import com.noscale.cerberus.base.BaseView

interface UserContract {
    interface View: BaseView<Presenter> {
        fun append (data: MutableList<Admin>?)
        fun throwError (message: String)
        fun throwError (message: String, deletion: Boolean)
        fun addLoadingItem ()
        fun removeLoadingItem ()
        fun removeItem (i: Admin)
    }

    interface Presenter: BasePresenter {
        var isLastPage: Boolean

        fun fetch ()
        fun reset ()
        fun delete (admin: Admin)
    }
}