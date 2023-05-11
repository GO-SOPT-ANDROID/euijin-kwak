package org.android.go.sopt.presentation.main.search

import android.app.SearchManager
import android.content.Context
import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.android.go.sopt.databinding.FragmentSearchBinding

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()

    private var searchJob: Job? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
        initSearchView()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initSearchView() {
        binding.searchView.run {
            suggestionsAdapter = SearchResultAdapter(requireContext(), null, false)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    searchJob?.cancel() // 이전 검색어 작업을 취소합니다.
                    if (!newText.isNullOrEmpty()) {
                        searchJob = lifecycleScope.launch {
                            delay(500)
                            Log.d("SearchFragment", "onQueryTextChange: $newText")
                            viewModel.search(newText)
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
                    return true
                }
            })

        }
    }

    private fun initObserve() {
        binding.searchView.run {
            viewModel.searchLiveData.observe(viewLifecycleOwner) { searchResult ->
                val defaultSuggestions = searchResult.second.documents.map { document ->
                    document.title.replace("<b>", "").replace("</b>", "")
                }
                val suggestions = defaultSuggestions.filter { it.contains(searchResult.first) }
                val cursor = MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
                suggestions.forEachIndexed { index, suggestion ->
                    cursor.addRow(arrayOf(index, suggestion))
                }
                suggestionsAdapter.changeCursor(cursor)
            }
        }
    }

    class SearchResultAdapter(context: Context, cursor: Cursor?, autoRequery: Boolean) :
        CursorAdapter(context, cursor, autoRequery) {

        override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
            return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
        }

        override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
            view?.findViewById<TextView>(android.R.id.text1)?.text = cursor?.getString(1)
        }
    }
}