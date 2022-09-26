package com.example.job_gsm.viewmodel.user

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.job_gsm.data.response.user.BaseUserResponse
import com.example.job_gsm.repository.UserRepository
import kotlinx.coroutines.launch

class ChangePwViewModel(private val repository: UserRepository, private val email: String, private val newPw: String): ViewModel() {
    companion object {
        private const val TAG = "ChangePwViewModel"
    }

    private val _changePwLiveData: MutableLiveData<BaseUserResponse?> = MutableLiveData()
    val changePwLiveData: MutableLiveData<BaseUserResponse?>
    get() = _changePwLiveData

    init {
        viewModelScope.launch {
            Log.d(TAG, "init: ${repository.changePw(email, newPw)}")
            _changePwLiveData.value = repository.changePw(email, newPw)
        }
    }

    class Factory(private val application: Application, private val email: String, private val newPw: String): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ChangePwViewModel(UserRepository(application), email, newPw) as T
        }
    }
}