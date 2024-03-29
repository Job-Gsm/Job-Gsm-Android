package com.example.job_gsm.viewmodel.user

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.job_gsm.data.response.user.NewTokenResponse
import com.example.job_gsm.repository.UserRepository
import kotlinx.coroutines.launch

class NewTokenViewModel(private val repository: UserRepository, private val email: String): ViewModel() {
    companion object {
        private const val TAG = "NewTokenViewModel"
    }

    private val _newTokenLiveData: MutableLiveData<NewTokenResponse?> = MutableLiveData()
    val newTokenLiveData: MutableLiveData<NewTokenResponse?>
    get() = _newTokenLiveData

    init {
        viewModelScope.launch {
            Log.d(TAG, "init: ${repository.newToken(email)}")
            _newTokenLiveData.value = repository.newToken(email)
        }
    }

    class Factory(private val application: Application, private val email: String): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewTokenViewModel(UserRepository.getInstance(application)!!, email) as T
        }
    }
}