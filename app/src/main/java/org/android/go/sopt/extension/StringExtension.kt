package org.android.go.sopt.extension

import android.text.Html
import android.text.Spanned


fun String.fromHtmlLegacy(): Spanned = Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
