package com.mark.snapask_interview_android.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.snapask.sdk.Sdk
import com.snapask.sdk.data.User
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class UserViewModel(val sdk: Sdk):ViewModel() {
    private val _callUsersDataFailure = MutableLiveData<String>()
    val callUsersDataFailure: LiveData<String> = _callUsersDataFailure

    private val _updateUsersData = MutableLiveData<List<User>>()
    val updateUsersData: LiveData<List<User>> = _updateUsersData

    fun callUsersData() {
        sdk
            .apiManager
            .getUsers(0)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({ response ->
                if (response.isSuccessful) {
                    Timber.d("get users success")
                    val users = response.body()
                    Timber.d("users:$users")
                    if (users != null)
                        _updateUsersData.postValue(users!!)
                } else {
                    _callUsersDataFailure.postValue("Response data is failure.")
                }
            }, { throwable ->
                _callUsersDataFailure.postValue(throwable.message)
            })
    }
}