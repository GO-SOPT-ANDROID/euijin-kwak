package org.android.go.sopt.extension

import android.content.Context
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import org.android.go.sopt.UserPreferences
import org.android.go.sopt.data.datastore.UserPreferencesSerializer

val Context.userPreferencesStore: DataStore<UserPreferences> by dataStore(
    fileName = "user_preferences.pb",
    serializer = UserPreferencesSerializer
)
fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}