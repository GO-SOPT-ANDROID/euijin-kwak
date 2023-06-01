package org.android.go.sopt.presentation.sign

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.android.go.sopt.data.api.ApiFactory
import org.android.go.sopt.data.model.sopt.SoptSignUpRequest
import org.android.go.sopt.data.model.sopt.SoptSignUpResponse

class SignUpViewModel : ViewModel() {
    private val _idInput = MutableStateFlow("")
    val idInput: StateFlow<String> = _idInput

    private val _passwordInput = MutableStateFlow("")
    val passwordInput: StateFlow<String> = _passwordInput

    private val _idError = MutableLiveData<String?>()
    val idError: LiveData<String?> = _idError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?> = _passwordError

    private val _isSignUpEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isSignUpEnabled: StateFlow<Boolean> = _isSignUpEnabled

    fun onIdChanged(id: String) {
        _idInput.value = id
        getSignUpEnabled()
        if (!idPattern.matches(id)) {
            _idError.value = ID_ERROR
        } else {
            _idError.value = null
        }
    }

    fun onPasswordChanged(password: String) {
        _passwordInput.value = password
        getSignUpEnabled()
        if (!passwordPattern.matches(password)) {
            _passwordError.value = PASSWORD_ERROR
        } else {
            _passwordError.value = null
        }
    }

    fun getSignUpEnabled() {
        _isSignUpEnabled.value = idError.value == null && passwordError.value == null
    }

    private val _signUpLiveData = MutableLiveData<SoptSignUpResponse?>()
    val signUpLiveData: LiveData<SoptSignUpResponse?> get() = _signUpLiveData

    fun signUp(id: String, password: String, name: String, skill: String) {
        viewModelScope.launch {
            val response = ApiFactory.signUpService.postSignUp(SoptSignUpRequest(id, password, name, skill))
            _signUpLiveData.value = if (response.isSuccessful && response.body()?.status == 200) {
                response.body()
            } else {
                null
            }
        }
    }

    private val _isDuplicatedId = MutableLiveData<Boolean>()
    val isDuplicatedId: LiveData<Boolean?> get() = _isDuplicatedId

    fun startDuplicateIdCheck() {
        viewModelScope.launch {
            val response = ApiFactory.signUpService.getUserInfo(idInput.value)
            _isDuplicatedId.value = response.isSuccessful && response.body()?.status == 200
        }
    }

    companion object {
        private const val ID_ERROR = "ID는 영문, 숫자가 포함되어야 하며 6~10글자 이내로 작성해야합니다."
        private const val PASSWORD_ERROR = "Password는 영문, 숫자, 특수문자가 포함되어야 하며 6~12글자 이내로 작성해야합니다."

        private val idPattern = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,10}$")
        private val passwordPattern = Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{6,12}$")
    }
}