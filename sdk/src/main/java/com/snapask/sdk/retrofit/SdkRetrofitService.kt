package com.snapask.sdk.retrofit

import com.snapask.sdk.data.User
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SdkRetrofitService {
    companion object {
        private const val PATH_USERS="users"
        private const val DEFAULT_PER_PAGE=30
        private const val QUERY_SINCE_ID="since"
        private const val QUERY_PER_PAGE="per_page"
    }

    @GET("$PATH_USERS")
    fun getUsers(
        @Query(QUERY_SINCE_ID)
        sinceId:Int,
        @Query(QUERY_PER_PAGE)
        perPage:Int=DEFAULT_PER_PAGE):Single<Response<List<User>>>
}