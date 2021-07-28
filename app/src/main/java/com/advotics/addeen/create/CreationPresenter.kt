package com.advotics.addeen.create

import com.advotics.addeen.data.Admin
import com.advotics.addeen.data.Recipient
import com.advotics.addeen.data.source.CreationDataSource
import com.advotics.addeen.data.source.remote.creation.CreationRemoteDataSource
import com.advotics.addeen.utils.ErrorCode

class CreationPresenter(val mView: CreationContract.View?): CreationContract.Presenter {
    init {
        mView?.mPresenter = this
    }

    override fun submitAdminRegis(name: String?, email: String?, password: String?) {
        CreationRemoteDataSource.getInstance().onAdminRegis(name, email, password, object: CreationDataSource.LoadCreationCallback<Admin> {
            override fun onSuccessCallback(r: Admin) {
                mView?.onCreationSuccess()
            }

            override fun onErrorCallback(e: ErrorCode) {
                mView?.throwError(e.message)
            }
        })
    }

    override fun submitRecipientRegis(
        adminId: Int,
        photo: String?,
        name: String?,
        email: String?,
        phone: String?,
        gender: String?
    ) {
        CreationRemoteDataSource.getInstance().onRecipientRegis(adminId, photo, name, email, phone, gender, object: CreationDataSource.LoadCreationCallback<Recipient> {
            override fun onSuccessCallback(r: Recipient) {
                mView?.onCreationSuccess()
            }

            override fun onErrorCallback(e: ErrorCode) {
                mView?.throwError(e.message)
            }
        })
    }

    override fun start() {
    }
}