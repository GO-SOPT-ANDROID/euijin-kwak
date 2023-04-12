package org.android.go.sopt.presentation.base

import android.os.Bundle
import androidx.viewbinding.ViewBinding

abstract class BaseViewModelViewBindingActivity<B : ViewBinding, VM : BaseViewModel> : BaseViewBindingActivity<B>(), ViewModel<VM> {

    abstract fun initObserve()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewEvent(this)
        initObserve()
    }


}