package com.advotics.addeen.scan

import com.advotics.addeen.data.Recipient
import com.advotics.addeen.data.RecipientPackage
import com.advotics.addeen.data.source.RecipientDataSource
import com.advotics.addeen.data.source.remote.recipient.RecipientDataRemoteSource
import com.advotics.addeen.utils.ErrorCode

class ScanPresenter(val mView: ScanContract.View?): ScanContract.Presenter {

    init {
        mView?.mPresenter = this
    }

    override fun redeem(email: String, phone: String, year: String) {
        RecipientDataRemoteSource.getInstance().redeem(email, phone, year, object: RecipientDataSource.RedeemCallback {
            override fun onRedeemSuccess(r: RecipientPackage) {
                mView?.onSuccess(r.qrSentStatus, r.packageReceivedStatus)
            }

            override fun onErrorCallback(e: ErrorCode) {
                mView?.throwError(e.message)
            }
        })
    }

    override fun start() {
    }

}