package org.android.go.sopt.presentation.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.android.go.sopt.domain.entity.music.SoptPostMusicBody
import org.android.go.sopt.domain.repository.MusicRepository
import retrofit2.http.Multipart
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val musicRepository: MusicRepository
) : ViewModel() {

    private val _homeStateFlow = MutableStateFlow<HomeState>(HomeState.UnInitialized)
    val homeStateFlow: StateFlow<HomeState> get() = _homeStateFlow

    private val id = "admin5"

    fun getMusicList() {
        viewModelScope.launch {
            musicRepository.getMusicList(id)
                .onSuccess {
                    _homeStateFlow.emit(HomeState.SuccessGetMusicList(it))
                }.onFailure {
                    Log.e("HomeViewModel", "getMusicList() error: $it")
                    _homeStateFlow.emit(HomeState.Error)
                }
        }
    }

    fun postMusic(soptPostMusicBody: SoptPostMusicBody) {
        viewModelScope.launch {
            val image = soptPostMusicBody.image
            val title = soptPostMusicBody.title.toRequestBody("text/plain".toMediaType())
            val singer = soptPostMusicBody.singer.toRequestBody("text/plain".toMediaType())

            musicRepository.postMusic(id, image, title, singer)
                .onSuccess {
                    _homeStateFlow.emit(HomeState.SuccessPostMusic)
                }.onFailure {
                    Log.e("HomeViewModel", "postMusic() error: $it")
                    _homeStateFlow.emit(HomeState.Error)
                }
        }
    }
}