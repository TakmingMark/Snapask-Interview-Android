package com.snapask.sdk.api

import com.snapask.sdk.data.User
import com.snapask.sdk.retrofit.SdkRetrofit
import io.reactivex.rxjava3.core.Single
import retrofit2.Response

class ApiManager {
    private val sdkRetrofit by lazy {
        SdkRetrofit()
    }

    fun getUsers(sinceId:Int):Single<Response<List<User>>>{
        return sdkRetrofit
            .service
            .getUsers(sinceId)
    }
}