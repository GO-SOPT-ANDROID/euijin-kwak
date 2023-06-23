package org.android.go.sopt.domain.repository

import org.android.go.sopt.data.model.sopt.SoptLoginRequest
import org.android.go.sopt.domain.entity.sopt.SoptLoginResponseEntity
import org.android.go.sopt.domain.entity.sopt.SoptSignUpRequestEntity
import org.android.go.sopt.domain.entity.sopt.SoptSignUpResponseEntity

interface SoptRepository {

    suspend fun getUserInfo(userId: String): Result<SoptLoginResponseEntity>

    suspend fun postSignUp(body: SoptSignUpRequestEntity): Result<SoptSignUpResponseEntity>

    suspend fun postLogin(body: SoptLoginRequest): Result<SoptLoginResponseEntity>

}