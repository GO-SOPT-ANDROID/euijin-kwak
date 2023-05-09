package org.android.go.sopt.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.android.go.sopt.data.repository.MusicRepositoryImpl
import org.android.go.sopt.domain.repository.MusicRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    @Singleton
    fun bindRepositoryImpl(repositoryImpl: MusicRepositoryImpl): MusicRepository

}