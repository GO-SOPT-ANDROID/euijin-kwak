package org.android.go.sopt

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData(
        val id: String,
        val password: String,
        val name: String,
        val specialty: String
) : Parcelable
