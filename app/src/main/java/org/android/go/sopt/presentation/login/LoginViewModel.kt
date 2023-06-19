package org.android.go.sopt.presentation.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.android.go.sopt.data.api.ApiFactory
import org.android.go.sopt.data.model.sopt.SoptLoginRequest
import org.android.go.sopt.data.model.sopt.SoptLoginResponse

class LoginViewModel : ViewModel() {

    private val _loginLiveData = MutableLiveData<SoptLoginResponse>()
    val loginLiveData:LiveData<SoptLoginResponse> get() = _loginLiveData

    fun login(id: String, password: String) {
        viewModelScope.launch {
            val response = ApiFactory.signUpService.postLogin(SoptLoginRequest(id, password))
            if (response.isSuccessful && response.body()?.status == 200) {
                _loginLiveData.value = response.body()
            } else {
                Log.e("LoginViewModel", "login: ${response.body()?.message}")
            }
        }
    }
}