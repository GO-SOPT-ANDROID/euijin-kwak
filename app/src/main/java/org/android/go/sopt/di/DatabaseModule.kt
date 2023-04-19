package org.android.go.sopt.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.android.go.sopt.data.dao.MusicDao
import org.android.go.sopt.data.database.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "music_database.db"
        ).createFromAsset("music_database.db").build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): MusicDao {
        return database.musicDao()
    }
}