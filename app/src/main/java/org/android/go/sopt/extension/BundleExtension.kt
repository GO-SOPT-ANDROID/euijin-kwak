package org.android.go.sopt.extension

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.TIRAMISU
import android.os.Bundle
import android.os.Parcelable

/**
 * 사용시 NPE 발생 위험도 있음
 * */
fun <T : Parcelable?> Bundle.getExtParcelable(key: String, clazz: Class<T>): T? = when {
    SDK_INT >= TIRAMISU -> getParcelable(key, clazz)
    else -> getParcelable(key)
}

