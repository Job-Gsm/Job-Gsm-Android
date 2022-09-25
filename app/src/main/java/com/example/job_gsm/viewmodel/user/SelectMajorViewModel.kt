package com.example.job_gsm.viewmodel.user

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.job_gsm.data.response.user.BaseUserResponse
import com.example.job_gsm.repository.UserRepository
import kotlinx.coroutines.launch

class SelectMajorViewModel(private val repository: UserRepository, private val email: String, private val major: String,
                           private val career: Int): ViewModel()
{
    private val _selectMajorLiveData: MutableLiveData<BaseUserResponse?> = MutableLiveData()
    val selectMajorLiveData: MutableLiveData<BaseUserResponse?>
    get() = _selectMajorLiveData

    init {
        viewModelScope.launch {
            _selectMajorLiveData.value = repository.selectMajor(email, major, career)
        }
    }

    class Factory(private val application: Application,private val email: String, private val major: String,
                  private val career: Int): ViewModelProvider.Factory
    {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SelectMajorViewModel(UserRepository(application), email, major, career) as T
        }
    }
}