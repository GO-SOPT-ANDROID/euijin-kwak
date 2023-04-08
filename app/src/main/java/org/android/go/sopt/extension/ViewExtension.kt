package org.android.go.sopt

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar


fun View.showSnack(@StringRes stringId: Int) {
    Snackbar.make(this, context.getString(stringId), Snackbar.LENGTH_SHORT).show()
}
