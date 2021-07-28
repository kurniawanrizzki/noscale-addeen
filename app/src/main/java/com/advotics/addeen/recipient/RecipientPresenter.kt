package com.advotics.addeen.recipient

import com.advotics.addeen.data.Recipient
import com.advotics.addeen.data.source.RecipientDataSource
import com.advotics.addeen.data.source.remote.recipient.RecipientDataRemoteSource
import com.advotics.addeen.utils.ErrorCode

class RecipientPresenter (val mView: RecipientContract.View?): RecipientContract.Presenter {
    override var year: Int? = null

    init {
        mView?.mPresenter = this
    }

    override fun fetch(pageNumber: Int, pageSize: Int, sort: String?) {
        RecipientDataRemoteSource.getInstance().getRecipientList(pageNumber, pageSize, sort, year, object: RecipientDataSource.RecipientListCallback {
            override fun onLoadCallback(data: List<Recipient>) {
                mView?.append(data)
            }

            override fun onErrorCallback(e: ErrorCode) {
                mView?.throwError(e.message)
            }
        })
    }

    override fun start() {
        fetch(0, 10, null)
    }
}