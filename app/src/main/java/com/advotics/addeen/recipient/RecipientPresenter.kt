package com.advotics.addeen.recipient

import com.advotics.addeen.data.Recipient
import com.advotics.addeen.data.source.RecipientDataSource
import com.advotics.addeen.data.source.remote.recipient.RecipientDataRemoteSource
import com.advotics.addeen.data.source.remote.recipient.RecipientListResponse
import com.advotics.addeen.utils.ErrorCode
import com.advotics.addeen.utils.PaginationListener
import java.util.concurrent.atomic.AtomicInteger

class RecipientPresenter (val mView: RecipientContract.View?, var isDataMissing: Boolean): RecipientContract.Presenter {
    override var year: Int? = null

    private val mPageNumber: AtomicInteger = AtomicInteger(0)

    override var isLastPage: Boolean = false

    init {
        mView?.mPresenter = this
    }

    override fun fetch() {
        if (isLastPage) return
        mView?.addLoadingItem()

        RecipientDataRemoteSource.getInstance().getRecipientList(mPageNumber.get(), PaginationListener.PAGE_SIZE, null, year, object: RecipientDataSource.RecipientListCallback {
            override fun onLoadCallback(response: RecipientListResponse) {
                val data = response.data
                isLastPage = response?.totalPages == mPageNumber?.incrementAndGet()

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
        if (isDataMissing) fetch()
    }
}