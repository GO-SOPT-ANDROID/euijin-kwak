package org.android.go.sopt.data.service.kakao

import org.android.go.sopt.data.model.kakao.KakaoSearchWebResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoService {

    @GET("/search/web")
    suspend fun getSearchWeb(
        @Query("query") keyword: String
    ): Response<KakaoSearchWebResponse>

}