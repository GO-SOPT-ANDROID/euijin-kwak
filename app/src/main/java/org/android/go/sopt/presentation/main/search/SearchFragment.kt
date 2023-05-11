package org.android.go.sopt.presentation.main.search

import android.app.SearchManager
import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.android.go.sopt.databinding.FragmentSearchBinding
import org.android.go.sopt.extension.fromHtmlLegacy

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    private var kakaoSearchResultAdapter = KakaoSearchResultAdapter()

    private var searchJob: Job? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
    }

    private fun initRecyclerView() {
        binding.rvKakaoSearchResult.adapter = kakaoSearchResultAdapter
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
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

    private fun initObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.searchViewState.collectLatest { searchViewState ->
                    when (searchViewState) {
                        SearchViewState.UnInitialized -> {
                            initSearchView()
                            initRecyclerView()
                        }

                        is SearchViewState.Loading -> {
                            //TODO Loading
                        }

                        is SearchViewState.SuccessSearchKeyword -> {
                            changeSearchTitleListener(searchViewState.data)
                        }

                        is SearchViewState.SuccessSearchWeb -> {
                            kakaoSearchResultAdapter.submitList(searchViewState.data.documents)
                        }

                        else -> {
                            //TODO ERROR Handling
                        }
                    }
                }
            }
        }
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
}