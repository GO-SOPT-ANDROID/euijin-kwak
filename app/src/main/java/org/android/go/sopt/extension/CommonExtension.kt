package org.android.go.sopt.extension

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class CommonExtension {
    private fun Int.toRequestBody() = toString().toRequestBody("text/plain".toMediaTypeOrNull())
}