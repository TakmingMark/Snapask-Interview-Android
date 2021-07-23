package com.mark.snapask_interview_android

import android.app.Application
import com.mark.snapask_interview_android.di.applicationModule
import com.mark.snapask_interview_android.di.viewModelModule
import com.snapask.sdk.di.sdkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import timber.log.Timber

class MainApplication : Application() {
    lateinit var koinApplication: KoinApplication

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // start Koin context
        koinApplication = startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(
                applicationModule,
                sdkModule,
                viewModelModule
            )
        }
    }
}