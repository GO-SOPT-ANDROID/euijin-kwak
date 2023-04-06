package org.android.go.sopt.presentation.main

import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.android.go.sopt.R
import org.android.go.sopt.data.model.UserData
import org.android.go.sopt.data.model.toUserData
import org.android.go.sopt.databinding.ActivityMainBinding
import org.android.go.sopt.extension.showErrorSnack
import org.android.go.sopt.extension.showSnack
import org.android.go.sopt.presentation.base.BaseViewModelActivity

@AndroidEntryPoint
class MainActivity : BaseViewModelActivity<ActivityMainBinding, MainViewModel>() {
    override fun setBinding(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override val viewModel: MainViewModel by viewModels()
    override val errorHandler: (() -> Unit)
        get() = { binding.root.showErrorSnack() }

    override fun initObserve() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mainState.collectLatest {state ->
                    when (state) {
                        is MainState.UnInitialized -> {
                            viewModel.readUser()
                        }
                        is MainState.SuccessUserData -> {
                            bindViews(state.user.toUserData())
                        }
                        is MainState.Error -> {
                            binding.root.showSnack(getString(R.string.error_message))
                        }
                    }
                }
            }
        }
    }

    private fun bindViews(userData: UserData?) = with(binding) {
        userData?.run {
            tvUserName.text = if (name?.isNotEmpty() == true) {
                getString(R.string.user_name_format, name)
            } else {
                getString(R.string.user_name_null)
            }

            tvUserSpecialty.text = if (specialty?.isNotEmpty() == true) {
                getString(R.string.user_specialty_format, specialty)
            } else {
                getString(R.string.user_specialty_null)
            }
        }
    }
}