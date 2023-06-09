package org.android.go.sopt.data.service.sopt

import org.android.go.sopt.data.model.sopt.SoptLoginRequest
import org.android.go.sopt.data.model.sopt.SoptLoginResponse
import org.android.go.sopt.data.model.sopt.SoptSignUpRequest
import org.android.go.sopt.data.model.sopt.SoptSignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SoptService {

    @GET("/info/{userId}")
    suspend fun getUserInfo(
        @Path("userId") userId: String
    ): SoptLoginResponse

    @POST("/sign-up")
    suspend fun postSignUp(
        @Body body: SoptSignUpRequest
    ): SoptSignUpResponse

    @POST("/sign-in")
    suspend fun postLogin(
        @Body body: SoptLoginRequest
    ): SoptLoginResponse

}