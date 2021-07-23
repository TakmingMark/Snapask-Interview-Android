package com.snapask.sdk.api

import com.snapask.sdk.data.Users
import com.snapask.sdk.retrofit.SdkRetrofit
import io.reactivex.rxjava3.core.Single
import retrofit2.Response

class ApiManager {
    private val sdkRetrofit by lazy {
        SdkRetrofit()
    }

    fun getUsers(sinceId:Int):Single<Response<Users>>{
        return sdkRetrofit
            .service
            .getUsers(sinceId)
    }
}