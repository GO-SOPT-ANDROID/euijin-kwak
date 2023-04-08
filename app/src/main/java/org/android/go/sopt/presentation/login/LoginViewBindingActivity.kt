package org.android.go.sopt.presentation.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivityLoginBinding
import org.android.go.sopt.extension.getParcelable
import org.android.go.sopt.extension.showToast
import org.android.go.sopt.model.UserData
import org.android.go.sopt.presentation.base.BaseViewBindingActivity
import org.android.go.sopt.presentation.main.MainViewBindingActivity
import org.android.go.sopt.presentation.signup.SignUpViewBindingActivity
import org.android.go.sopt.showSnack
import org.android.go.sopt.util.IntentKey

class LoginViewBindingActivity : BaseViewBindingActivity<ActivityLoginBinding>() {

    private var userData: UserData? = null

    private val signUpLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                userData = result.data?.getParcelable(IntentKey.USER_DATA, UserData::class.java)
                binding.root.showSnack(R.string.sign_up_complete)
            }
        }

    override fun setBinding(layoutInflater: LayoutInflater): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        btLogin.setOnClickListener {
            userData?.let { userData ->
                if ((etId.text.toString() == userData.id) && (etPassword.text.toString() == userData.password)) {
                    showToast(R.string.login_complete)
                    Intent(this@LoginViewBindingActivity, MainViewBindingActivity::class.java).apply {
                        putExtra(IntentKey.USER_DATA, userData)
                    }.let(::startActivity)
                }
            } ?: root.showSnack(R.string.sign_up_not_complete_message)
        }

        btSignUp.setOnClickListener {
            Intent(this@LoginViewBindingActivity, SignUpViewBindingActivity::class.java).let {
                signUpLauncher.launch(it)
            }
        }
    }
}