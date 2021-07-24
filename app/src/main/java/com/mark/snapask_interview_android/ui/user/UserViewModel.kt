package com.mark.snapask_interview_android.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.mark.snapask_interview_android.ui.user.recyclerview.UserPagingSource

class UserViewModel :ViewModel() {
    val flow = Pager(
        PagingConfig(pageSize = 20)
    ) {
        UserPagingSource()
    }.flow
        .cachedIn(viewModelScope)
}