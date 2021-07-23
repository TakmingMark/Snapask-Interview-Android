package com.snapask.sdk.di

import com.snapask.sdk.Sdk
import org.koin.dsl.module


val sdkModule = module {
    single {
        Sdk()
    }
}