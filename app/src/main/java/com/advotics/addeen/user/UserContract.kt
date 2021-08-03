package com.advotics.addeen.user

import com.advotics.addeen.data.Admin
import com.noscale.cerberus.base.BasePresenter
import com.noscale.cerberus.base.BaseView

interface UserContract {
    interface View: BaseView<Presenter> {
        fun append (data: List<Admin>)
        fun throwError (message: String)
        fun addLoadingItem ()
        fun removeLoadingItem ()
    }

    interface Presenter: BasePresenter {
        var isLastPage: Boolean

        fun fetch ()
        fun reset ()
    }
}