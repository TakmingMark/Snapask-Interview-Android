package com.snapask.sdk

import com.snapask.sdk.api.ApiManager


class Sdk {
    val apiManager by lazy {
        ApiManager()
    }
}