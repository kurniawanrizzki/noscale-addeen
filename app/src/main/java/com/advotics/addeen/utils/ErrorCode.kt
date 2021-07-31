package com.advotics.addeen.utils

enum class ErrorCode (val code: Int, val message: String) {
    STATUS_NO_CONNECTION (500, "The connection has timed out"),
    INTERNAL_SERVER_ERROR (404, "Whoops.. Looks like something went wrong, we're working on it"),
    NO_RESULTS (300, "No Results"),
    OK_RESULTS (200, "Downloading...")
}