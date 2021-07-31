package com.advotics.addeen.scan

import com.noscale.cerberus.base.BasePresenter
import com.noscale.cerberus.base.BaseView

interface ScanContract {
    interface View: BaseView<Presenter> {
        fun onSuccess (qrSentStatus: Boolean, packageReceivedStatus: Boolean)
        fun throwError (message: String)
    }

    interface Presenter: BasePresenter {
        fun redeem (email: String, phone: String, year: String)
    }
}