package org.android.go.sopt.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import org.android.go.sopt.domain.repository.UserRepository
import org.android.go.sopt.presentation.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: UserRepository): BaseViewModel() {

    private val _mainState = MutableLiveData<MainState>(MainState.UnInitialized)
    val mainState: LiveData<MainState> get() = _mainState

    fun readUser() {
        launch {
            withContext(Dispatchers.IO){
                repository.readUser().collectLatest {
                    _mainState.postValue(MainState.SuccessUserData(it))
                }
            }
        }
    }
}