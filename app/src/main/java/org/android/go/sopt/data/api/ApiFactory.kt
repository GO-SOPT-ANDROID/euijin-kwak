package org.android.go.sopt.data.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.android.go.sopt.BuildConfig
import org.android.go.sopt.data.service.multi_part.MultiService
import org.android.go.sopt.data.service.reqres.ReqresService
import org.android.go.sopt.data.service.sopt.SoptService
import org.android.go.sopt.util.UrlInfo
import retrofit2.Retrofit
import retrofit2.create

object ApiFactory {
    /**
     * 심화, 도전 과제에서 retrofit 생성 로직 및 비즈니스 로직들을 모두 마이그레이션 할 겁니다.
     * */
    val signUpService: SoptService by lazy {
        Retrofit.Builder()
            .baseUrl(UrlInfo.SOPT_BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()
            .create()
    }

    private val okHttpClient:OkHttpClient = Interceptor { chain ->
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder()
            .header("Authorization", "token")
            .build()
        chain.proceed(request)
    }.let { interceptor ->
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }


    val reqresService: ReqresService by lazy {
        Retrofit.Builder()
            .baseUrl(UrlInfo.REQRES_BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create()
    }

    val multiService:MultiService by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.Multy_Part_BASE_URL)
            .addConverterFactory(Json.asConverterFactory("multipart/form-data".toMediaType()))
            .client(okHttpClient)
            .build()
            .create()
    }
}