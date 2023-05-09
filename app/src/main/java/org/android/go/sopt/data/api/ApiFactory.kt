package org.android.go.sopt.data.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.android.go.sopt.data.service.sopt.SoptService
import org.android.go.sopt.util.UrlInfo
import retrofit2.Retrofit

object ApiFactory {
    /**
     * 심화, 도전 과제에서 retrofit 생성 로직 및 비즈니스 로직들을 모두 마이그레이션 할 겁니다.
     * */
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(UrlInfo.SOPT_BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    inline fun <reified T> create(): T = retrofit.create<T>(T::class.java)
}

object ServicePool {
    val signUpService = ApiFactory.create<SoptService>()
}