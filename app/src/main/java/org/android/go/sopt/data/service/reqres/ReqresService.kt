package org.android.go.sopt.data.service.reqres

import org.android.go.sopt.data.model.reqres.ReqresResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ReqresService {

    @GET("/api/users")
    suspend fun getUsers(
        @Query("page") page:Int = 1
    ): Response<ReqresResponse>

}