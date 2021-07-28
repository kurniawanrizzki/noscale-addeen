package com.advotics.addeen.data.source

import com.advotics.addeen.data.Admin
import com.advotics.addeen.utils.ErrorCode

interface AdminDataSource {
    interface AdminListCallback {
        fun onLoadCallback (data: List<Admin>)
        fun onErrorCallback (e: ErrorCode)
    }

    fun getAdminList (pageNumber: Int, pageSize: Int, callback: AdminListCallback)
}