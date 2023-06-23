package org.android.go.sopt.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.android.go.sopt.data.service.kakao.KakaoService
import org.android.go.sopt.data.service.reqres.ReqresService
import org.android.go.sopt.data.service.sopt.SoptMusicService
import org.android.go.sopt.data.service.sopt.SoptService
import org.android.go.sopt.util.UrlInfo
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideSignUpService(jsonConverter:Converter.Factory, client: OkHttpClient): SoptService {
        return Retrofit.Builder()
            .baseUrl(UrlInfo.SOPT_BASE_URL)
            .addConverterFactory(jsonConverter)
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideReqresService(jsonConverter:Converter.Factory, client: OkHttpClient): ReqresService {
        return Retrofit.Builder()
            .baseUrl(UrlInfo.REQRES_BASE_URL)
            .addConverterFactory(jsonConverter)
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideKakaoService(jsonConverter:Converter.Factory, client: OkHttpClient): KakaoService {
        return Retrofit.Builder()
            .baseUrl(UrlInfo.KAKAO_BASE_URL)
            .addConverterFactory(jsonConverter)
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideMusicService(jsonConverter:Converter.Factory, client: OkHttpClient): SoptMusicService {
        return Retrofit.Builder()
            .baseUrl(UrlInfo.SOPT_BASE_URL)
            .addConverterFactory(jsonConverter)
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideJsonConverterFactory(): Converter.Factory {
        return Json.asConverterFactory("application/json".toMediaType())
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .build()
    }
}