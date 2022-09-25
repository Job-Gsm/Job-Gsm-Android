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

class SignUpViewModel(private val repository: UserRepository, private val username: String,
                      private val email: String, private val pw: String): ViewModel()
{
    companion object {
        private const val TAG = "SignUpViewModel"
    }

    private val _signUpServiceLiveData: MutableLiveData<UserResponse?> = MutableLiveData()
    val signUpServiceLiveData: MutableLiveData<UserResponse?>
    get() = _signUpServiceLiveData

    init {
        viewModelScope.launch {
            Log.d(TAG, "init: ${repository.signUp(username, email, pw)}")
            _signUpServiceLiveData.value = repository.signUp(username, email, pw)
        }
    }

    class Factory(private val application: Application, private val username: String,
                  private val email: String, private val pw: String): ViewModelProvider.Factory
    {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SignUpViewModel(UserRepository.getInstance(application)!!, username, email, pw) as T
        }
    }
}