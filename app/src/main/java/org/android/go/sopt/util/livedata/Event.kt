package org.android.go.sopt.util.livedata

open class Event<out T>(private val content: T) {
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            // 이벤트가 이미 처리 되었다면 null 을 반환
            null
        } else {
            // 이벤트가 처리되었다고 표시한 후에 값을 반환
            hasBeenHandled = true
            content
        }
    }

    /**
     * 이벤트의 처리 여부에 상관 없이 값을 반환합니다.
     */
    fun peekContent(): T = content
}