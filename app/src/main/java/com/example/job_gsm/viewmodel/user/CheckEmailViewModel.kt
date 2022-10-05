package com.example.job_gsm.viewmodel.user

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.job_gsm.data.request.CheckEmailRequest
import com.example.job_gsm.data.response.user.BaseUserResponse
import com.example.job_gsm.repository.UserRepository
import kotlinx.coroutines.launch

class CheckEmailViewModel(private val repository: UserRepository, private val email: String, private val key: String): ViewModel() {

    private val _checkEmailServiceLiveData: MutableLiveData<BaseUserResponse?> = MutableLiveData()
    val checkEmailServiceLiveData: MutableLiveData<BaseUserResponse?>
    get() = _checkEmailServiceLiveData

    init {
        viewModelScope.launch {
            _checkEmailServiceLiveData.value = repository.checkEmail(CheckEmailRequest(email, key))
        }
    }

    class Factory(private val application: Application, private val email: String, private val key: String): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CheckEmailViewModel(UserRepository(application), email, key) as T
        }
    }
}