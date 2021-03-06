package com.mark.snapask_interview_android.di

import com.mark.snapask_interview_android.ui.user.recyclerview.UserAdapter
import org.koin.dsl.module

val applicationModule = module {
    factory {
        UserAdapter()
    }
}