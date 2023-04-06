package org.android.go.sopt.presentation.base

import androidx.lifecycle.LifecycleOwner
import org.android.go.sopt.util.livedata.EventObserver

interface ViewModel<VM : BaseViewModel> {

    val viewModel: VM
    val errorHandler: (() -> Unit)?

    fun observeViewEvent(lifecycleOwner: LifecycleOwner) {
        viewModel.viewEvent.observe(lifecycleOwner, EventObserver(this::onViewEvent))
    }

    fun onViewEvent(event: Any) {
        when (event) {
            BaseViewModel.EVENT_COROUTINE_EXCEPTION,
            BaseViewModel.EVENT_SOCKET_EXCEPTION,
            BaseViewModel.EVENT_UNKNOWN_HOST_EXCEPTION -> {
                errorHandler?.invoke()
            }
        }
    }
}