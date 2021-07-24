package com.mark.snapask_interview_android.di


import com.mark.snapask_interview_android.ui.user.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        UserViewModel(get())
    }
}