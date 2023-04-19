package org.android.go.sopt.presentation.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.android.go.sopt.domain.MusicRepository
import org.android.go.sopt.domain.entity.MusicData
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val musicRepository: MusicRepository) : ViewModel() {

    private val _homeState = MutableStateFlow<HomeState>(HomeState.UnInitialized)
    val homeState: StateFlow<HomeState> get() = _homeState

    fun getMusicList() {
        viewModelScope.launch(Dispatchers.IO) {
            musicRepository.getAll().collect {
                _homeState.value = HomeState.Loading
                _homeState.value = HomeState.SuccessMusicList(it)
            }
        }
    }

    fun getLatestMusic() {
        viewModelScope.launch(Dispatchers.IO) {
            _homeState.value = HomeState.Loading
            _homeState.value = HomeState.SuccessLatestMusic(musicRepository.getLatestMusic())
        }
    }

    fun insertMusic(musicData: MusicData) {
        viewModelScope.launch(Dispatchers.IO) {
            musicRepository.insert(musicData)
        }
    }

    fun deleteMusic(musicData: MusicData) {
        viewModelScope.launch(Dispatchers.IO) {
            musicRepository.delete(musicData)
        }
    }

    fun updateMusic(musicData: MusicData) {
        viewModelScope.launch(Dispatchers.IO) {
            musicRepository.update(musicData)
        }
    }
}