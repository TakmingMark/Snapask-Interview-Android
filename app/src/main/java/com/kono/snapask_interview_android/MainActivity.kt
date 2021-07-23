package com.kono.snapask_interview_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.snapask.sdk.Sdk
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sdk= Sdk()

        sdk
            .apiManager
            .getUsers(0)
            .subscribeOn(Schedulers.io())
            .subscribe ({ users->
                Log.d("Mark",users.toString())
            },{throwable->
                Log.d("Mark",throwable.toString())
            })
    }
}