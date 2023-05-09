package org.android.go.sopt.data.service.sopt

import org.android.go.sopt.data.model.sopt.SoptLoginRequest
import org.android.go.sopt.data.model.sopt.SoptLoginResponse
import org.android.go.sopt.data.model.sopt.SoptSignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SoptService {

    @GET("/info/{userId}")
    fun getUserInfo(
        @Path("userId") userId: String
    ): Response<SoptLoginResponse>

    @POST("/sign-up")
    fun postSignUp(
        @Body body: SoptSignUpRequest
    ): Response<SoptLoginResponse>

    @POST("/sign-in")
    fun postLogin(
        @Body body: SoptLoginRequest
    ): Response<SoptLoginResponse>

}