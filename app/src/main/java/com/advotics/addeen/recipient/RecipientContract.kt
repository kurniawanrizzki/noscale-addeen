package com.advotics.addeen.recipient

import com.advotics.addeen.data.Recipient
import com.noscale.cerberus.base.BasePresenter
import com.noscale.cerberus.base.BaseView

interface RecipientContract {
    interface View: BaseView<Presenter> {
        fun append (data: List<Recipient>)
        fun throwError (message: String)
        fun addLoadingItem ()
        fun removeLoadingItem ()
    }

    interface Presenter: BasePresenter {
        var isLastPage: Boolean
        var year: Int?

        fun fetch ()
    }
}