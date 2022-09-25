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

class SendEmailViewModel(private val repository: UserRepository, private val email: String): ViewModel() {
    companion object {
        private const val TAG = "SendEmailViewModel"
    }

    private val _sendEmailServiceLiveData: MutableLiveData<BaseUserResponse?> = MutableLiveData()
    val sendEmailServiceLiveData: MutableLiveData<BaseUserResponse?>
    get() = _sendEmailServiceLiveData

    init {
        viewModelScope.launch {
            Log.d(TAG, "init: ${repository.sendEmail(email)}")
            _sendEmailServiceLiveData.value = repository.sendEmail(email)
        }
    }

    class Factory(private val application: Application, private val email: String): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SendEmailViewModel(UserRepository.getInstance(application)!!, email) as T
        }
    }
}