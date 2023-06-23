package org.android.go.sopt.presentation.main.search

import android.content.Context
import android.database.Cursor
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView
import kotlinx.coroutines.flow.MutableStateFlow

class SearchCursorAdapter(
    context: Context,
    cursor: Cursor?,
    autoRequery: Boolean,
    private val searchKeyword: MutableStateFlow<String>
) : CursorAdapter(context, cursor, autoRequery) {

    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        try {
            view?.findViewById<TextView>(android.R.id.text1)?.text = cursor?.getString(1)?.highlightMatchedText(searchKeyword.value)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun String.highlightMatchedText(searchKeyword: String?): Spannable {
        val spannable = SpannableString(this)
        if (!searchKeyword.isNullOrEmpty()) {
            val start = this.indexOf(searchKeyword, ignoreCase = true)
            val end = start + searchKeyword.length
            if (start >= 0) {
                spannable.setSpan(
                    StyleSpan(Typeface.BOLD),
                    start,
                    end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        return spannable
    }
}