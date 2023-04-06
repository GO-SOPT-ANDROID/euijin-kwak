package org.android.go.sopt.di

import android.content.Context
import androidx.datastore.core.DataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.android.go.sopt.AutoLogin
import org.android.go.sopt.UserPreferences
import org.android.go.sopt.extension.autoLoginPreferencesStore
import org.android.go.sopt.extension.userPreferencesStore
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    @Singleton
    fun provideUserPreferencesDataStore(@ApplicationContext context: Context): DataStore<UserPreferences> {
        return context.userPreferencesStore
    }

    @Provides
    @Singleton
    fun provideAutoLoginPreferencesDataStore(@ApplicationContext context: Context): DataStore<AutoLogin> {
        return context.autoLoginPreferencesStore
    }
}