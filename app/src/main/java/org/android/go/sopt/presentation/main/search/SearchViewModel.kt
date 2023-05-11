package org.android.go.sopt.presentation.main.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.android.go.sopt.domain.entity.kakao.KakaoSearchWebEntity
import org.android.go.sopt.domain.repository.KakaoRepository
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val kakaoRepository: KakaoRepository) : ViewModel() {

    private val _searchLiveData = MutableLiveData<Pair<String,KakaoSearchWebEntity>>()
    val searchLiveData: LiveData<Pair<String,KakaoSearchWebEntity>> get() = _searchLiveData

    fun search(query: String) {
        viewModelScope.launch {
            kakaoRepository.getSearchWeb(query)?.let {
                _searchLiveData.value = Pair(query, it)
            }?: kotlin.run {
             Log.d("SearchViewModel", "search: null")
            }
        }
    }
}