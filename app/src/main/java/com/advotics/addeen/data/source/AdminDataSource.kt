package com.advotics.addeen.data.source

import com.advotics.addeen.data.Admin
import com.advotics.addeen.data.source.remote.admin.AdminListResponse
import com.advotics.addeen.utils.ErrorCode

interface AdminDataSource {
    interface AdminListCallback {
        fun onLoadCallback (response: AdminListResponse)
        fun onErrorCallback (e: ErrorCode)
    }

    fun getAdminList (pageNumber: Int, pageSize: Int, callback: AdminListCallback)
}