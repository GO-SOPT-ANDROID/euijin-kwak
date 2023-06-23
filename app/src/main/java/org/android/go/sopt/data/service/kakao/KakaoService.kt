package org.android.go.sopt.data.service.kakao

import org.android.go.sopt.BuildConfig
import org.android.go.sopt.data.model.kakao.KakaoSearchWebResponse
import org.android.go.sopt.util.Constant
import org.android.go.sopt.util.UrlInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface KakaoService {

    @GET("/v2/search/web")
    suspend fun getSearchWeb(
        @Header("Authorization") key: String = Constant.KAKAO_AUTH_HEADER,
        @Query(value = "query") keyword: String
    ): Response<KakaoSearchWebResponse>

}