package org.android.go.sopt.data.repository

import org.android.go.sopt.data.model.sopt.SoptLoginRequest
import org.android.go.sopt.data.model.sopt.SoptSignUpResponse
import org.android.go.sopt.data.model.sopt.toSoptLoginResponseEntity
import org.android.go.sopt.data.model.sopt.toSoptSignUpRequest
import org.android.go.sopt.data.model.sopt.toSoptSignUpResponseEntity
import org.android.go.sopt.data.service.sopt.SoptService
import org.android.go.sopt.domain.entity.sopt.SoptLoginRequestEntity
import org.android.go.sopt.domain.entity.sopt.SoptLoginResponseEntity
import org.android.go.sopt.domain.entity.sopt.SoptSignUpRequestEntity
import org.android.go.sopt.domain.entity.sopt.SoptSignUpResponseEntity
import org.android.go.sopt.domain.repository.SoptRepository
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Inject

class SoptRepositoryImpl @Inject constructor(private val soptService: SoptService): SoptRepository {
    override suspend fun getUserInfo(userId: String): SoptLoginResponseEntity? {
        val response = soptService.getUserInfo(userId)
        return if (response.isSuccessful) {
             response.body()?.toSoptLoginResponseEntity()
        } else {
           null
        }
    }

    override suspend fun postSignUp(body: SoptSignUpRequestEntity): SoptSignUpResponseEntity? {
        val response = soptService.postSignUp(body.toSoptSignUpRequest())
        return if (response.isSuccessful) {
            response.body()?.toSoptSignUpResponseEntity()
        } else {
            null
        }
    }

    override suspend fun postLogin(body: SoptLoginRequest): SoptLoginResponseEntity? {
        val response = soptService.postLogin(body)
        return if (response.isSuccessful) {
            response.body()?.toSoptLoginResponseEntity()
        } else {
            null
        }
    }
}