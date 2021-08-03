package com.advotics.addeen.user

import com.advotics.addeen.data.Admin
import com.advotics.addeen.data.source.AdminDataSource
import com.advotics.addeen.data.source.remote.admin.AdminDataRemoteSource
import com.advotics.addeen.data.source.remote.admin.AdminListResponse
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
            override fun onLoadCallback(response: AdminListResponse) {
                val data = response.data
                isLastPage = response?.totalPages == mPageNumber?.incrementAndGet()

                data?.let {
                    mView?.append(it)
                }

                isDataMissing = false
            }

            override fun onErrorCallback(e: ErrorCode) {
                mView?.throwError(e.message, false)
            }

        })
    }

    override fun reset() {
        isLastPage = false
        mPageNumber.set(0)

        fetch()
    }

    override fun delete(admin: Admin) {
        AdminDataRemoteSource.getInstance().delete(admin.id, object: AdminDataSource.AdminDeletionCallback {
            override fun onSuccessDeletion() {
                mView?.removeItem(admin)
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