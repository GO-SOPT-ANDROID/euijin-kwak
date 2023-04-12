package org.android.go.sopt.presentation.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import org.android.go.sopt.util.livedata.Event
import java.net.SocketException
import java.net.UnknownHostException

abstract class BaseViewModel: ViewModel() {

    companion object {
        const val EVENT_COROUTINE_EXCEPTION = 400
        const val EVENT_SOCKET_EXCEPTION = 401
        const val EVENT_HTTP_EXCEPTION = 402
        const val EVENT_UNKNOWN_HOST_EXCEPTION = 403
        const val EVENT_SHOW_LOADING_VIEW = 1000
        const val EVENT_HIDE_LOADING_VIEW = 2000
    }

    private val _viewEvent = MutableLiveData<Event<Any>>()
    val viewEvent: LiveData<Event<Any>>
        get() = _viewEvent

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(throwable.message, throwable.toString())
        when (throwable) {
            is SocketException -> viewEvent(EVENT_SOCKET_EXCEPTION) // Bad Internet
            is UnknownHostException -> viewEvent(EVENT_UNKNOWN_HOST_EXCEPTION) // Wrong connection
            else -> viewEvent(EVENT_COROUTINE_EXCEPTION)
        }
    }

    fun ViewModel.launch(dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate, block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(dispatcher + exceptionHandler) {
            block()
        }
    }

    fun viewEvent(content: Any) {
        _viewEvent.value = Event(content)
    }

    fun postViewEvent(content: Any) {
        _viewEvent.postValue(Event(content))
    }

}