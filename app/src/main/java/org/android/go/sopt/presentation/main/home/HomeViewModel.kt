package org.android.go.sopt.presentation.main.home

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.android.go.sopt.domain.MusicRepository
import org.android.go.sopt.domain.entity.MusicData
import org.android.go.sopt.presentation.model.MusicItem
import org.android.go.sopt.presentation.model.toMusicData
import org.android.go.sopt.presentation.model.toMusicItem
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val musicRepository: MusicRepository) : ViewModel() {

    private val _homeState = MutableStateFlow<HomeState>(HomeState.UnInitialized)
    val homeState: StateFlow<HomeState> get() = _homeState

    fun getMusicList() {
        viewModelScope.launch(Dispatchers.IO) {
            musicRepository.getAll().collect {
                val musicList = it.map {musicData-> musicData.toMusicItem() }
                _homeState.value = HomeState.Loading
                _homeState.value = HomeState.SuccessMusicList(musicList)
            }
        }
    }

    fun getLatestMusic() {
        viewModelScope.launch(Dispatchers.IO) {
            _homeState.value = HomeState.Loading
            _homeState.value = HomeState.SuccessLatestMusic(musicRepository.getLatestMusic().toMusicItem())
        }
    }

    fun insertMusic(musicData: MusicItem) {
        viewModelScope.launch(Dispatchers.IO) {
            musicRepository.insert(musicData.toMusicData())
        }
    }

    fun deleteMusic(musicData: MusicItem) {
        viewModelScope.launch(Dispatchers.IO) {
            musicRepository.delete(musicData.toMusicData())
        }
    }

    fun updateMusic(musicData: MusicItem) {
        viewModelScope.launch(Dispatchers.IO) {
            musicRepository.update(musicData.toMusicData())
        }
    }
}