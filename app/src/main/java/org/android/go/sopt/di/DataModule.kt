package org.android.go.sopt.di

import android.content.Context
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.android.go.sopt.UserPreferences
import org.android.go.sopt.data.datastore.UserPreferencesSerializer
import org.android.go.sopt.extension.showSnack
import org.android.go.sopt.extension.userPreferencesStore
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    @Singleton
    fun provideDataSource(@ApplicationContext context: Context): DataStore<UserPreferences> {
        return context.userPreferencesStore
    }
}