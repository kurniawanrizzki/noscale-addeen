package com.advotics.addeen.user

import com.advotics.addeen.data.Admin
import com.advotics.addeen.data.source.AdminDataSource
import com.advotics.addeen.data.source.remote.admin.AdminDataRemoteSource
import com.advotics.addeen.utils.ErrorCode

class UserPresenter (val mView: UserContract.View?): UserContract.Presenter {

    init {
        mView?.mPresenter = this
    }

    override fun fetch(pageNumber: Int, pageSize: Int) {
        AdminDataRemoteSource.getInstance().getAdminList(pageNumber, pageSize, object: AdminDataSource.AdminListCallback {
            override fun onLoadCallback(data: List<Admin>) {
                mView?.append(data)
            }

            override fun onErrorCallback(e: ErrorCode) {
                mView?.throwError(e.message)
            }

        })
    }

    override fun start() {
        fetch(0, 10)
    }
}