package org.android.go.sopt

import android.view.View
import com.google.android.material.snackbar.Snackbar


fun View.showSnack(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}
