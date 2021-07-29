package com.advotics.addeen.recipient

import com.advotics.addeen.data.Recipient
import com.advotics.addeen.data.source.RecipientDataSource
import com.advotics.addeen.data.source.remote.recipient.RecipientDataRemoteSource
import com.advotics.addeen.utils.ErrorCode
import java.util.concurrent.atomic.AtomicInteger

class RecipientPresenter (val mView: RecipientContract.View?, var isDataMissing: Boolean): RecipientContract.Presenter {
    override var year: Int? = null

    private val mPageNumber: AtomicInteger = AtomicInteger(0)

    private var alreadyLastPage = false

    init {
        mView?.mPresenter = this
    }

    override fun fetch(pageNumber: Int, pageSize: Int, sort: String?) {
        if (alreadyLastPage) return

        RecipientDataRemoteSource.getInstance().getRecipientList(pageNumber, pageSize, sort, year, object: RecipientDataSource.RecipientListCallback {
            override fun onLoadCallback(data: List<Recipient>) {
                if (data.isEmpty()) {
                    alreadyLastPage = true
                    return
                }

                mView?.append(data)
                mPageNumber.getAndIncrement()

                isDataMissing = false
            }

            override fun onErrorCallback(e: ErrorCode) {
                mView?.throwError(e.message)
            }
        })
    }

    override fun start() {
        if (isDataMissing) fetch(mPageNumber.get(), 10, null)
    }
}