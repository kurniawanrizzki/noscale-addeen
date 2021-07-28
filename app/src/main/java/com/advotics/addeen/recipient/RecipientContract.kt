package com.advotics.addeen.recipient

import com.advotics.addeen.data.Recipient
import com.noscale.cerberus.base.BasePresenter
import com.noscale.cerberus.base.BaseView

interface RecipientContract {
    interface View: BaseView<Presenter> {
        fun append (data: List<Recipient>)
        fun throwError (message: String)
    }

    interface Presenter: BasePresenter {
        var year: Int?
        fun fetch (pageNumber: Int, pageSize: Int, sort: String?)
    }
}