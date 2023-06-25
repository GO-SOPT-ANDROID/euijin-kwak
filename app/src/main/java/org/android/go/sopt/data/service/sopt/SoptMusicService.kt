package org.android.go.sopt.data.service.sopt

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.android.go.sopt.data.model.music.SoptGetMusicListResponse
import org.android.go.sopt.data.model.music.SoptPostMusicResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface SoptMusicService {

    @Multipart
    @POST("/music")
    suspend fun postMusic(
        @Header("id") id: String,
        @Part image: MultipartBody.Part,
        @Part("title") title: RequestBody,
        @Part("singer") singer: RequestBody,
    ): SoptPostMusicResponse

    @GET("/{id}/music")
    suspend fun getMusic(
        @Path("id") id: String
    ): SoptGetMusicListResponse
}