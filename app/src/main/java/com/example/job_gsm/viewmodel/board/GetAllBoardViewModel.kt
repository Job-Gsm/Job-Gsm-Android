package com.example.job_gsm.viewmodel.board

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.job_gsm.data.response.board.GetAllBoardResponse
import com.example.job_gsm.repository.GetAllBoardRepository
import kotlinx.coroutines.launch

class GetAllBoardViewModel(private val repository: GetAllBoardRepository, private val page: Int): ViewModel() {
    companion object {
        private const val TAG = "GetAllBoardViewModel"
    }

    private val _getAllBoardLiveData: MutableLiveData<GetAllBoardResponse?> = MutableLiveData()
    val getAllBoardLiveData: MutableLiveData<GetAllBoardResponse?> = MutableLiveData()

    init {
        viewModelScope.launch {
            Log.d(TAG, "init: ${repository.getAllBoard(page)}")
            _getAllBoardLiveData.value = repository.getAllBoard(page)
        }
    }

    class Factory(private val application: Application, private val page: Int): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return GetAllBoardViewModel(GetAllBoardRepository(application), page) as T
        }
    }
}