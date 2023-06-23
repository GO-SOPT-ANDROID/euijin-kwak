package org.android.go.sopt.presentation.main.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.android.go.sopt.domain.repository.MusicRepository
import org.android.go.sopt.presentation.model.MusicItem
import org.android.go.sopt.presentation.model.toMusicData
import org.android.go.sopt.presentation.model.toMusicItem
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(private val musicRepository: MusicRepository) : ViewModel() {

    private val _musicState = MutableStateFlow<MusicState>(MusicState.UnInitialized)
    val musicState: StateFlow<MusicState> get() = _musicState

    fun getMusicList() {
        viewModelScope.launch(Dispatchers.IO) {
            _musicState.value = MusicState.Loading
            runCatching {
                musicRepository.getAll().collect { musicDataList ->
                    musicDataList.map { musicData ->
                        musicData.toMusicItem()
                    }.let { musicList ->
                        _musicState.value = MusicState.SuccessMusicList(musicList)
                    }
                }
            }.onFailure { exception ->
                _musicState.value = MusicState.Error(exception.message ?: "Error occurred")
            }
        }
    }

    fun getLatestMusic() {
        _musicState.value = MusicState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            _musicState.value = MusicState.SuccessLatestMusic(musicRepository.getLatestMusic().toMusicItem())
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