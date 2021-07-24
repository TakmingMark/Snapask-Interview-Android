package com.mark.snapask_interview_android.ui.user.recyclerview

import androidx.paging.PagingSource.LoadResult.Page.Companion.COUNT_UNDEFINED
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.snapask.sdk.Sdk
import com.snapask.sdk.data.User
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class UserPagingSource : RxPagingSource<Int, User>(), KoinComponent {
    private val sdk: Sdk by inject()
    private var sinceId = 0

    override fun loadSingle(
        params: LoadParams<Int>
    ): Single<LoadResult<Int, User>> {
        return sdk
            .apiManager
            .getUsers(sinceId)
            .subscribeOn(Schedulers.io())
            .map { response->toLoadResult(response) }
            .onErrorReturn {error-> LoadResult.Error(error) }
    }

    private fun toLoadResult(response: retrofit2.Response<List<User>>): LoadResult<Int, User> {
        val users = response.body()
        sinceId += users?.size ?: 0
        return LoadResult.Page(
            users!!,
            null,  // Only paging forward.
            sinceId,
            COUNT_UNDEFINED,
            COUNT_UNDEFINED
        )
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        val anchorPosition = state.anchorPosition ?: return null

        val anchorPage: LoadResult.Page<Int, User> = state.closestPageToPosition(anchorPosition)
            ?: return null

        if (anchorPage.prevKey != null) {
            return anchorPage.prevKey!! + anchorPage.data.size
        }
        if (anchorPage.nextKey != null) {
            return anchorPage.nextKey!! - anchorPage.data.size
        }
        return null
    }
}