package org.android.go.sopt.data.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

const val USER_PREFERENCE = "user_preference"

object PreferencesDataStore {
    val USER_ID = stringPreferencesKey("user_id")
    val USER_PASSWORD = stringPreferencesKey("user_password")
    val USER_NAME = stringPreferencesKey("user_name")
    val USER_SPECIALTY = stringPreferencesKey("user_specialty")
}