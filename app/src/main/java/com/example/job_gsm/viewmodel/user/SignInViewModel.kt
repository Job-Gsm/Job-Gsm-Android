package com.example.job_gsm.viewmodel.user

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.job_gsm.data.response.user.UserResponse
import com.example.job_gsm.repository.UserRepository
import kotlinx.coroutines.launch

class SignInViewModel(private val repository: UserRepository, private val email: String, private val pw: String): ViewModel() {
    companion object {
        const val TAG = "SignInViewModel"
    }

    private val _signInServiceLiveData: MutableLiveData<UserResponse?> = MutableLiveData()
    val signInServiceLiveData: MutableLiveData<UserResponse?>
        get() = _signInServiceLiveData

    init {
        viewModelScope.launch {
            Log.d(TAG, "init: ${repository.signIn(email, pw)}")
            _signInServiceLiveData.value = repository.signIn(email, pw)
        }
    }

    class Factory(private val application: Application, private val email: String, private val pw: String): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SignInViewModel(UserRepository.getInstance(application)!!, email, pw) as T
        }
    }
}