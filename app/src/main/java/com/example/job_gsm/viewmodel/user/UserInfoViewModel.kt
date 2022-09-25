package com.example.job_gsm.viewmodel.user

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.job_gsm.data.response.user.BaseUserResponse
import com.example.job_gsm.repository.UserRepository
import kotlinx.coroutines.launch

class UserInfoViewModel(private val repository: UserRepository, private val email: String,
                        private val username: String, private val github: String, private val discord: String): ViewModel()
{
    private val _userInfoLiveData: MutableLiveData<BaseUserResponse?> = MutableLiveData()
    val userInfoLiveData: MutableLiveData<BaseUserResponse?>
    get() = _userInfoLiveData

    init {
        viewModelScope.launch {
            _userInfoLiveData.value = repository.getUserInfo(email, username, github, discord)
        }
    }

    class Factory(private val application: Application, private val email: String, private val username: String,
                  private val github: String, private val discord: String): ViewModelProvider.Factory
    {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UserInfoViewModel(UserRepository.getInstance(application)!!, email, username, github, discord) as T
        }
    }
}