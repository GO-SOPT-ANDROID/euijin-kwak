package org.android.go.sopt.presentation.main.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.android.go.sopt.domain.repository.KakaoRepository
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val kakaoRepository: KakaoRepository) : ViewModel() {

    private val _searchViewState = MutableStateFlow<SearchViewState>(SearchViewState.UnInitialized)
    val searchViewState: StateFlow<SearchViewState> get() = _searchViewState

    fun search(keyword: String, searchType: SearchType) {
        viewModelScope.launch {
            kakaoRepository.getSearchWeb(keyword).onStart {
                _searchViewState.value = SearchViewState.Loading
            }.collect {
                when (searchType) {
                    SearchType.SEARCH_KEYWORD -> {
                        _searchViewState.value =
                            SearchViewState.SuccessSearchKeyword(Pair(it, keyword))
                    }

                    SearchType.SEARCH_WEB -> {
                        _searchViewState.value = SearchViewState.SuccessSearchWeb(it)
                    }
                }
            }
        }
    }

    enum class SearchType {
        SEARCH_KEYWORD,
        SEARCH_WEB
    }
}