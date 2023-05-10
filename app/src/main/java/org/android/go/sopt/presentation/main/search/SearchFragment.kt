package org.android.go.sopt.presentation.main.search

import android.app.SearchManager
import android.content.Context
import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import org.android.go.sopt.databinding.FragmentSearchBinding


@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchableInfo = searchManager.getSearchableInfo(requireActivity().componentName)

        binding.searchView.run {
            suggestionsAdapter = SearchResultAdapter(
                requireContext(),
                null,
                false
            )


            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.isNullOrEmpty()) {
                        suggestionsAdapter.changeCursor(null)
                    } else {
                        val suggestions = listOf(
                            "검색어1",
                            "검색어2",
                            "검색어3"
                        ).filter { it.contains(newText) }

                        // mock 데이터를 Cursor에 추가
                        val cursor = MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
                        suggestions.forEachIndexed { index, suggestion ->
                            cursor.addRow(arrayOf(index, suggestion))
                        }
                        suggestionsAdapter.changeCursor(cursor)
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
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