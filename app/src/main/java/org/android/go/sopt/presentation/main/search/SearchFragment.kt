package org.android.go.sopt.presentation.main.search

import android.app.SearchManager
import android.content.Intent
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.os.Bundle
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.android.go.sopt.databinding.FragmentSearchBinding
import org.android.go.sopt.domain.entity.kakao.KakaoSearchWebEntity
import org.android.go.sopt.extension.fromHtmlLegacy
import org.android.go.sopt.data.model.BaseResult

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    private var kakaoSearchResultAdapter: KakaoSearchResultAdapter? = null

    private var searchJob: Job? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
    }

    override fun onDestroyView() {
        _binding = null
        kakaoSearchResultAdapter = null
        super.onDestroyView()
    }

    private fun initObserve() {
        viewModel.searchViewState.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED).onEach { searchViewState ->
            when (searchViewState) {
                SearchViewState.UnInitialized -> {
                    initSearchView()
                    initRecyclerView()
                }

                is SearchViewState.Loading -> {
                    //TODO Loading 힘들어요...
                }

                is SearchViewState.SuccessSearchKeyword -> {
                    handleSuccessSearchKeywordState(searchViewState.data)
                }

                is SearchViewState.SuccessSearchWeb -> {
                    handleSuccessSearchWeb(searchViewState.data)
                }

                else -> {
                    //TODO ERROR Handling
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initSearchView() {
        val searchKeyword = MutableStateFlow("")
        binding.searchView.run {
            suggestionsAdapter = SearchCursorAdapter(requireContext(), null, false, searchKeyword)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.search(searchKeyword.value, SearchViewModel.SearchType.SEARCH_WEB)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    searchJob?.cancel()
                    if (!newText.isNullOrEmpty()) {
                        searchKeyword.value = newText
                        searchJob = lifecycleScope.launch {
                            delay(500)
                            viewModel.search(newText, SearchViewModel.SearchType.SEARCH_KEYWORD)
                        }
                    }
                    return true
                }
            })

            setOnSuggestionListener(object : SearchView.OnSuggestionListener {
                override fun onSuggestionSelect(position: Int): Boolean {
                    return true
                }

                override fun onSuggestionClick(position: Int): Boolean {
                    val cursor = suggestionsAdapter.getItem(position) as? Cursor
                    val selection = cursor?.getString(1)
                    setQuery(selection, true)
                    viewModel.search(searchKeyword.value, SearchViewModel.SearchType.SEARCH_WEB)
                    return true
                }
            })
        }
    }

    private fun initRecyclerView() {
        kakaoSearchResultAdapter = KakaoSearchResultAdapter { url ->
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
        binding.rvKakaoSearchResult.adapter = kakaoSearchResultAdapter
    }

    private fun changeSearchTitleListener(searchResult: Pair<List<String>, String>) {
        binding.searchView.run {
            val suggestions = replaceSuggestionList(searchResult.first).filter { it.contains(searchResult.second) }
            val cursor = MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
            suggestions.forEachIndexed { index, suggestion ->
                cursor.addRow(arrayOf(index, suggestion))
            }
            suggestionsAdapter.changeCursor(cursor)
        }
    }

    private fun replaceSuggestionList(searchResultTitle: List<String>) =
        searchResultTitle.map { title ->
            title.fromHtmlLegacy()
        }

    private fun handleSuccessSearchKeywordState(data: Pair<BaseResult<KakaoSearchWebEntity>, String>) {
        when (data.first) {
            is BaseResult.Success -> {
                val result = data.first as BaseResult.Success
                changeSearchTitleListener(
                    Pair(
                        result.data.documents.map { it.title },
                        data.second
                    )
                )
            }

            is BaseResult.Error -> {
                // TODO: ERROR Handling
            }
        }
    }


    private fun handleSuccessSearchWeb(data: BaseResult<KakaoSearchWebEntity>) {
        when (data) {
            is BaseResult.Success -> {
                kakaoSearchResultAdapter?.submitList(data.data.documents)
            }

            is BaseResult.Error -> {
                //TODO ERROR Handling
            }
        }
    }
}