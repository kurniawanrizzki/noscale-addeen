package com.advotics.addeen.user

import com.advotics.addeen.data.Admin
import com.advotics.addeen.data.source.AdminDataSource
import com.advotics.addeen.data.source.remote.admin.AdminDataRemoteSource
import com.advotics.addeen.utils.ErrorCode
import com.advotics.addeen.utils.PaginationListener
import java.util.concurrent.atomic.AtomicInteger

class UserPresenter (val mView: UserContract.View?, var isDataMissing: Boolean): UserContract.Presenter {

    private val mPageNumber: AtomicInteger = AtomicInteger(0)

    override var isLastPage = false

    init {
        mView?.mPresenter = this
    }

    override fun fetch() {
        if (isLastPage) return
        mView?.addLoadingItem()

        AdminDataRemoteSource.getInstance().getAdminList(mPageNumber?.get(), PaginationListener.PAGE_SIZE, object: AdminDataSource.AdminListCallback {
            override fun onLoadCallback(data: List<Admin>) {
                if (data.isEmpty()) {
                    isLastPage = true
                    mView?.removeLoadingItem()
                    return
                }

                mView?.append(data)
                mPageNumber?.getAndIncrement()

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